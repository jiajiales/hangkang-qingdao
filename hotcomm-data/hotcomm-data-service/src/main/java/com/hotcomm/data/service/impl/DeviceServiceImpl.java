package com.hotcomm.data.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.service.Device;
import com.hotcomm.data.bean.entity.service.DeviceType;
import com.hotcomm.data.bean.enums.DeviceEnum;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceDeleteStatus;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceIotTech;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceProtocol;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceStatus;
import com.hotcomm.data.bean.params.service.device.DeviceBatchParams;
import com.hotcomm.data.bean.params.service.device.LoraABPDevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraABPDeviceParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADeviceParam;
import com.hotcomm.data.bean.vo.device.LoraABPDeviceVO;
import com.hotcomm.data.bean.vo.device.LoraOTAADeviceVO;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.comm.RedisCacheHelper;
import com.hotcomm.data.db.DataMapper;
import com.hotcomm.data.db.DeviceGroupMapper;
import com.hotcomm.data.db.DeviceMapper;
import com.hotcomm.data.db.DeviceTypeMapper;
import com.hotcomm.data.service.DataPushReadyService;
import com.hotcomm.data.service.DeviceService;
import com.hotcomm.data.utils.ExcelData;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.utils.RegexUtils;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DeviceServiceImpl implements DeviceService {
	
	@Autowired
	DeviceMapper deviceMapper;

	@Autowired
	DeviceGroupMapper deviceGroupMapper;

	@Autowired
	DeviceTypeMapper deviceTypeMapper;

	@Autowired
	DataMapper dataMapper;

	@Autowired
	@Qualifier("exceptionManager")
    ExceptionManager manager;

	@Autowired
	RedisHelper redisHelper;
	
	@Autowired
	DataPushReadyService dataPushReadyService;
	
	@Override
	public LoraABPDeviceVO getLoraABP(Integer deviceId) {
		return this.deviceMapper.queryLoraABPDev(deviceId);
	}

	@Override
	public LoraOTAADeviceVO getLoraOTAA(Integer deviceId) {
		return this.deviceMapper.queryLoraOTAADev(deviceId);
	}

	@Override
	public Device existsDevice(String deviceCode) throws HKException {
		Device device = new Device();
		device.setCode(deviceCode);
		device.setIsDelete(DeviceEnum.DeviceDeleteStatus.NO.getValue());
		device.setStatus(DeviceEnum.DeviceStatus.ENABLE.getValue());
		device = deviceMapper.selectOne(device);
		return device;
	}

	@Override
	public void delDev(Integer deviceId) throws HKException {
		long devUnsentQueueNum = dataMapper.getDevUnsentQueueNum(deviceId);
		// 判断设备是否有尚有未发送的数据
		String deviceCode = null;
		if (devUnsentQueueNum == 0) { 
			Device device = new Device();
			device = deviceMapper.selectByPrimaryKey(deviceId);
			deviceCode = device.getCode();
			device.setCode(device.getCode() + "DEL@" + deviceId);
			device.setStatus(DeviceStatus.DISABLE.getValue());
			device.setIsDelete(DeviceDeleteStatus.YES.getValue());
			device.setUpdateTime(new Date());
			deviceMapper.updateByPrimaryKey(device);
			
		 String findCacheDevCode = RedisCacheHelper.CACHE_DEVICE_PREFIX + device.getCode();
		 redisHelper.del(findCacheDevCode);
		 String key = Constant.CODE_REDIS_TEMP_SUPIX+device.getCode();
		 redisHelper.del(key);
		 
		 dataPushReadyService.deviceDel(deviceCode);
		} else {
			throw manager.create("DEVICE00002");
		}
	}

	@Override
	public void batchAllotDeviceGroup(DeviceBatchParams param) throws HKException {
		Integer newDeviceGroupId = param.getDeviceGroupId();
		String deviceIds = param.getDeviceIds();
		String[] deviceIdsArr = deviceIds.split(",");
		Integer deviceNum = deviceIdsArr.length;
		
		Integer remainNums = deviceGroupMapper.getRemainDevNums(newDeviceGroupId);

		if (remainNums < deviceNum)
			throw manager.create("DEVICE00008");

		deviceMapper.batchAllotDeviceGroup(newDeviceGroupId, deviceIdsArr);
		
		dataPushReadyService.batchDeviceGroupUpdate(newDeviceGroupId, deviceIdsArr);
	}

	@Override
	public Integer addLoraABPDev(LoraABPDeviceParam param) throws HKException {
		Device device =null;
		device = existsDevice(param.getCode());
		if(device!=null) 
			throw manager.create("DEVICE00001");
		try {
			device =  new Device();
			ObjectMapper mapper = new ObjectMapper();
			BeanUtils.copyProperties(param, device);
			
			Integer remainNums = deviceGroupMapper.getRemainDevNums(device.getGroupId());
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
			
			Map<String, Object> lora = new HashMap<>();
			lora.put("AppSKey", param.getAppSKey() == null ? "" : param.getAppSKey());
			lora.put("NwkSKey", param.getNwkSKey() == null ? "" : param.getNwkSKey());
			lora.put("network_type", "ABP");
			String loraStr = mapper.writeValueAsString(lora);
			device.setCreateTime(new Date());
			device.setUpdateTime(new Date());
			device.setLora(loraStr);
			device.setStatus(DeviceStatus.ENABLE.getValue());
			device.setIsDelete(DeviceDeleteStatus.NO.getValue());
			device.setIotTech(DeviceIotTech.LOAR.getValue());
			deviceMapper.insertSelective(device);
			
		} catch (Exception e) {
			log.debug(param.toString());
			log.debug("添加设备失败", e);
		} 
		return device.getDeviceId();
	}

	@Override
	public Integer addLoraOTAADev(LoraOTAADeviceParam param) throws HKException {
		Device device =null;
		device = existsDevice(param.getCode());
		if(device!=null) 
			throw manager.create("DEVICE00001");
		try {
			device =  new Device();
			ObjectMapper mapper = new ObjectMapper();
			BeanUtils.copyProperties(param, device);
			
			Integer remainNums = deviceGroupMapper.getRemainDevNums(device.getGroupId());
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
			
			Map<String, Object> lora = new HashMap<>();
			lora.put("AppSKey", param.getAppSKey() == null ? "" : param.getAppSKey());
			lora.put("AppEUI", param.getAppEUI() == null ? "" : param.getAppEUI());
			lora.put("mac", param.getMac());
			lora.put("network_type", "OTAA");
			String loraStr = mapper.writeValueAsString(lora);
			device.setCreateTime(new Date());
			device.setUpdateTime(new Date());
			device.setLora(loraStr);
			device.setStatus(DeviceStatus.ENABLE.getValue());
			device.setIsDelete(DeviceDeleteStatus.NO.getValue());
			device.setIotTech(DeviceIotTech.LOAR.getValue());
			deviceMapper.insertSelective(device);
		} catch (Exception e) {
			log.debug(param.toString());
			log.debug("添加设备失败", e);
		} 
		return device.getDeviceId();
	}

	@Override
	public void updateLoraABPDev(LoraABPDeviceParam param) throws HKException {
		String code = param.getCode();
		Integer deviceId = param.getDeviceId();
		Device device = new Device();
		device.setDeviceId(deviceId);
		device = this.deviceMapper.selectOne(device);
		Device oldDbDevice = new Device();
		BeanUtils.copyProperties(device, oldDbDevice);
		
		if(device==null) 
			return;
		String sourceCode = device.getCode();
		
		if(!sourceCode.equals(code)) {
			Device check = new Device(deviceId,code);
			if(this.deviceMapper.existsCode(check)>0)
				throw manager.create("DEVICE00001");
			check = null;
			 String key = Constant.CODE_REDIS_TEMP_SUPIX+sourceCode;
			 redisHelper.del(key);
		}
		
		if(!(param.getGroupId()==device.getGroupId())) {
			Integer remainNums = deviceGroupMapper.getRemainDevNums(device.getGroupId());
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
		}
		
		BeanUtils.copyProperties(param, device);
		device.setUpdateTime(new Date());
		this.deviceMapper.updateByPrimaryKeySelective(device);
		
		String AppSKey = param.getAppSKey();
		String NwkSKey = param.getNwkSKey();
		Map<String, Object> jsonParam = new HashMap<>(); 
		jsonParam.put("AppSKey", AppSKey == null ? "" : AppSKey);
		jsonParam.put("NwkSKey", NwkSKey == null ? "" : NwkSKey);
		jsonParam.put("deviceId", deviceId);
		this.deviceMapper.updateLoraJson(jsonParam);
		
		if(oldDbDevice.getStatus()!=param.getStatus()) 
			 dataPushReadyService.deviceDisable(device.getCode(),DeviceStatus.getByValue(param.getStatus()));
		if(oldDbDevice.getType()!=param.getType()) 
			dataPushReadyService.deviceTypeUpate(device.getCode(), param.getType());
		if(oldDbDevice.getGroupId()!=param.getGroupId())
			dataPushReadyService.deviceGroupUpdate(device.getCode(), param.getGroupId());
		
		
	}
	
	@Override
	public void updateLoraOTAADev(LoraOTAADeviceParam param) throws HKException {
		String code = param.getCode();
		Integer deviceId = param.getDeviceId();
		Device device = new Device();
		device.setDeviceId(deviceId);
		device = this.deviceMapper.selectOne(device);
		if(device==null) 
			return;
		String sourceCode = device.getCode();
		
		if(!sourceCode.equals(code)) {
			Device check = new Device(deviceId,code);
			if(this.deviceMapper.existsCode(check)>0)
				throw manager.create("DEVICE00001");
			check = null;
			 String key = Constant.CODE_REDIS_TEMP_SUPIX+sourceCode;
			 redisHelper.del(key);
		}
		
		if(!(param.getGroupId()==device.getGroupId())) {
			Integer remainNums = deviceGroupMapper.getRemainDevNums(device.getGroupId());
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
		}
		
		BeanUtils.copyProperties(param, device);
		device.setUpdateTime(new Date());
		this.deviceMapper.updateByPrimaryKeySelective(device);
		
		String AppSKey = param.getAppSKey();
		String AppEUI = param.getAppEUI();
		String mac = param.getMac();
		Map<String, Object> jsonParam = new HashMap<>(); 
		jsonParam.put("AppSKey", AppSKey == null ? "" : AppSKey);
		jsonParam.put("AppEUI", AppEUI == null ? "" : AppEUI);
		jsonParam.put("mac", mac);
		jsonParam.put("deviceId", deviceId);
		this.deviceMapper.updateLoraJson(jsonParam);
		
		if(device.getStatus()!=param.getStatus()) 
			 dataPushReadyService.deviceDisable(device.getCode(),DeviceStatus.getByValue(param.getStatus()));
		if(device.getType()!=param.getType()) 
			dataPushReadyService.deviceTypeUpate(device.getCode(), param.getType());
		if(device.getGroupId()!=param.getGroupId())
			dataPushReadyService.deviceGroupUpdate(device.getCode(), param.getGroupId());
		
	}

	@Override
	public PageInfo<LoraABPDeviceVO> pageLoraABP(LoraABPDevicePageParam pageParam) {
		PageHelper.startPage(pageParam.getPage(),pageParam.getRows());
		Page<LoraABPDeviceVO> page = this.deviceMapper.queryPageLoraABPDev(pageParam);
		return new PageInfo<>(page.getTotal(),page.getResult());
	}

	@Override
	public PageInfo<LoraOTAADeviceVO> pageLoraOTAA(LoraOTAADevicePageParam pageParam) {
		PageHelper.startPage(pageParam.getPage(),pageParam.getRows());
		Page<LoraOTAADeviceVO> page = this.deviceMapper.queryPageLoraOTAADev(pageParam);
		return new PageInfo<>(page.getTotal(),page.getResult());
	}

	@Override
	public Object importLoraABPDev(InputStream fileInput, Integer groupid,String createUser)throws HKException {
		try(XSSFWorkbook workbook = new XSSFWorkbook (fileInput); ) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			if(rowNum<=0) 
				return new Object();
			List<DeviceType> types = this.deviceTypeMapper.queryList();
			Map<String, Integer> typeIds = new HashMap<>();
			for(DeviceType type:types) {
				typeIds.put(type.getTypeName(), type.getTypeId());
			}
			ObjectMapper jsonMapper = new ObjectMapper();
			Set<String> codes = new HashSet<>();
			Set<Device> devices = new HashSet<>();
			for (Row row : sheet) {
				int cellNum = row.getRowNum();
				if (cellNum == 0 || row.getCell(0)==null ||row.getCell(1)==null ) 
					continue;
				
				Cell cell0 = row.getCell(0);
				cell0.setCellType(CellType.STRING);
				String deviceCode = cell0.getStringCellValue();
				if(codes.contains(deviceCode)) 
					continue;
				Device device = new Device(deviceCode);
				codes.add(deviceCode);
				String deviceType = row.getCell(1).getStringCellValue();
				Map<String, Object> lora = new TreeMap<>();
				String AppSKey = row.getCell(2)!=null?row.getCell(2).getStringCellValue():"";
				String NwkSKey = row.getCell(3)!=null?row.getCell(3).getStringCellValue():"";
				lora.put("AppSKey", AppSKey);
				lora.put("NwkSKey", NwkSKey);
				lora.put("network_type", "ABP");
				String loraStr = jsonMapper.writeValueAsString(lora);
				device.setLora(loraStr);
				device.setGroupId(groupid);
				Integer typeId = typeIds.get(deviceType);
				typeId = typeId == null ? 0:typeId;
				device.setType(typeId);
				device.setCreateTime(new Date());
				device.setProtocol(DeviceProtocol.AMQP.getValue());
				device.setIotTech(DeviceIotTech.LOAR.getValue());
				device.setStatus(DeviceStatus.ENABLE.getValue());
				device.setIsDelete(DeviceDeleteStatus.NO.getValue());
				device.setCreateUser(createUser);
				device.setDesc("");
				device.setReceiveNum(0);
				devices.add(device);
			}
			log.info(devices.toString());
			
			Integer remainNums = deviceGroupMapper.getRemainDevNums(groupid);
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
			
			if(devices.size()>0) 
				this.deviceMapper.batchAddDevice(devices);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Object importLoraOTAADev(InputStream fileInput,Integer groupid,String createUser)throws HKException{
		try(XSSFWorkbook workbook = new XSSFWorkbook (fileInput); ) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			if(rowNum<=0) 
				return new Object();
			List<DeviceType> types = this.deviceTypeMapper.queryList();
			Map<String, Integer> typeIds = new HashMap<>();
			for(DeviceType type:types) {
				typeIds.put(type.getTypeName(), type.getTypeId());
			}
			ObjectMapper jsonMapper = new ObjectMapper();
			Set<String> codes = new HashSet<>();
			Set<Device> devices = new HashSet<>();
			for (Row row : sheet) {
				int cellNum = row.getRowNum();
				if (cellNum == 0 || row.getCell(0)==null ||row.getCell(1)==null ||row.getCell(2)==null) 
					continue;

				Cell cell0 = row.getCell(0);
				cell0.setCellType(CellType.STRING);
				String deviceCode = cell0.getStringCellValue();

				if(!RegexUtils.isEnglishNumber(deviceCode)) 
					continue;
				
				if(codes.contains(deviceCode)) 
					continue;
				Device device = new Device(deviceCode);
				codes.add(deviceCode);
				String mac = row.getCell(1).getStringCellValue();
				String deviceType = row.getCell(2).getStringCellValue();
				Map<String, Object> lora = new TreeMap<>();
				String AppSKey = row.getCell(3)!=null?row.getCell(3).getStringCellValue():"";
				String AppEUI = row.getCell(4)!=null?row.getCell(4).getStringCellValue():"";
				lora.put("AppSKey", AppSKey);
				lora.put("AppEUI", AppEUI);
				lora.put("mac", mac);
				lora.put("network_type", "OTAA");
				String loraStr = jsonMapper.writeValueAsString(lora);
				device.setLora(loraStr);
				device.setGroupId(groupid);
				Integer typeId = typeIds.get(deviceType);
				typeId = typeId == null ? 0:typeId;
				device.setType(typeId);
				device.setCreateTime(new Date());
				device.setProtocol(DeviceProtocol.AMQP.getValue());
				device.setIotTech(DeviceIotTech.LOAR.getValue());
				device.setStatus(DeviceStatus.ENABLE.getValue());
				device.setIsDelete(DeviceDeleteStatus.NO.getValue());
				device.setCreateUser(createUser);
				device.setDesc("");
				device.setReceiveNum(0);
				devices.add(device);
			}
			log.info(devices.toString());
			
			Integer remainNums = deviceGroupMapper.getRemainDevNums(groupid);
			if (remainNums < 1 )
				throw manager.create("DEVICE00008");
			
			if(devices.size()>0) 
				this.deviceMapper.batchAddDevice(devices);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ExcelData exportLoraABPDev(Integer devGroupId, Integer memberId) {
		LoraABPDevicePageParam pageParam = new LoraABPDevicePageParam();
		pageParam.setMemberId(memberId);
		pageParam.setGroupId(devGroupId);
		Page<LoraABPDeviceVO> page = this.deviceMapper.queryPageLoraABPDev(pageParam);
		List<LoraABPDeviceVO>  list = page.getResult();
		ExcelData data = new ExcelData();
		data.setName("OTAA设备列表");
		String [] headers = {"DevAddr","AppSKey","NwkSKey","GroupName","TypeName","Nums","Status","Desc","Time","Record"};
		data.setTitles(Arrays.asList(headers));
		List<List<Object>> rows = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		list.forEach(vo->{
			Integer status = vo.getStatus();
			String statusName = DeviceStatus.getByValue(status==null?1:status).getName();
			Object [] row = {vo.getCode(),vo.getAppSKey(),vo.getNwkSKey(),vo.getGroupName(),vo.getTypeName(),vo.getReceiveNum(),statusName,vo.getDesc(),df.format(vo.getCreateTime()),vo.getCreateUser()};
			rows.add(Arrays.asList(row));
		});
		data.setRows(rows);
		return data;
	}

	@Override
	public ExcelData exportLoarOTAADev(Integer devGroupId, Integer memberId) {
		LoraOTAADevicePageParam pageParam = new LoraOTAADevicePageParam();
		pageParam.setMemberId(memberId);
		pageParam.setGroupId(devGroupId);
		Page<LoraOTAADeviceVO> page = this.deviceMapper.queryPageLoraOTAADev(pageParam);
		List<LoraOTAADeviceVO>  list = page.getResult();
		ExcelData data = new ExcelData();
		data.setName("OTAA设备列表");
		String [] headers = {"DevAddr","MAC","AppSKey","AppEUI","GroupName","TypeName","Nums","Status","Desc","Time","Record"};
		data.setTitles(Arrays.asList(headers));
		List<List<Object>> rows = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		list.forEach(vo->{
			Integer status = vo.getStatus();
			String statusName = DeviceStatus.getByValue(status==null?1:status).getName();
			Object [] row = {vo.getCode(),vo.getMac(),vo.getAppSKey(),vo.getAppEUI(),vo.getGroupName(),vo.getTypeName(),vo.getReceiveNum(),statusName,vo.getDesc(),df.format(vo.getCreateTime()),vo.getCreateUser()};
			rows.add(Arrays.asList(row));
		});
		data.setRows(rows);
		return data;
	}

	@Override
	public void updateReceiveNums(Integer deviceId) {
		Map<String, Object> param = new HashMap<>();
		param.put("deviceId", deviceId);
		this.deviceMapper.receiveNumIncrement(param);
	}

	@Override
	public void updateReceiveNums(String deviceCode) throws HKException {
		Map<String, Object> param = new HashMap<>();
		param.put("deviceCode", deviceCode);
		this.deviceMapper.receiveNumIncrement(param);
	}

	@Override
	public Integer getReceiveNumByDeviceCode(String deviceCode) throws HKException {
		return deviceMapper.getReceiveNumByDeviceCode(deviceCode);
	}

}
