package com.hotcomm.prevention.service.common;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotcomm.prevention.bean.mysql.common.ImportDeviceVO;
import com.hotcomm.prevention.bean.mysql.common.entity.TModule;
import com.hotcomm.prevention.db.mysql.common.ModuleMapper;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	ModuleMapper moduleMapper;

	/**
	 * 模块管理->所有模块
	 */
	@Override
	public List<TModule> selectModuleAll() throws MyException {
		Example example = new Example(TModule.class);
		example.createCriteria().andEqualTo("isenable", true);
		List<TModule> list = moduleMapper.selectByExample(example);
		return list;
	}

	// 查询当前用户所有的模块
	@Override
	public List<TModule> selectModuleByUserId(Integer userid) throws MyException {
		return moduleMapper.selectTModuleByUserId(userid);
	}

	@Override
	public String batchImport(String fileName, MultipartFile file, Integer userid) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("地磁", 1);
		map.put("烟感", 2);
		map.put("井盖", 3);
		map.put("PM2.5", 4);
		map.put("水表", 5);
		map.put("电表", 6);
		map.put("路灯", 7);
		map.put("红外", 8);
		map.put("垃圾桶", 9);
		map.put("水浸", 10);
		map.put("门磁", 11);
		map.put("可燃气体", 12);
		map.put("液位计", 13);
		map.put("三相电流", 14);
		map.put("三相电压", 15);
		map.put("水压", 16);
		map.put("剩余电流", 17);
		String notNull = "";
		List<ImportDevice> list = new ArrayList<ImportDevice>();
		ImportDeviceVO duplicateData = new ImportDeviceVO();
		List<String> listForMac = new ArrayList<>();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new MyException("0", "上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = "";
		}
		ImportDevice importDevice;
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			importDevice = new ImportDevice();
			String mac = null;
			String moduleid = null;
			Integer lessalarmvalue = null;
			Integer topalarmvalue = null;
			if (row.getCell(0) != null) {
				mac = row.getCell(0).getStringCellValue();
			}
			if (row.getCell(1) != null) {
				moduleid = row.getCell(1).getStringCellValue();
				moduleid = map.get(moduleid).toString();
			}
			if (row.getCell(2) != null) {
				lessalarmvalue = (int) row.getCell(2).getNumericCellValue();
			}
			if (row.getCell(3) != null) {
				topalarmvalue = (int) row.getCell(3).getNumericCellValue();
			}
			if (mac != null && moduleid != null) {
				Integer checkMac = moduleMapper.checkMac(mac, Integer.parseInt(moduleid));
				if (checkMac == 0) {
					importDevice.setMac(mac);
					importDevice.setModuleid(Integer.parseInt(moduleid));
					importDevice.setLessalarmvalue(lessalarmvalue);
					importDevice.setTopalarmvalue(topalarmvalue);
					importDevice.setAdduserid(userid);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
					importDevice.setAddTime(df.format(new Date()));
					list.add(importDevice);
				} else {
					listForMac.add(mac);
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			moduleMapper.batchInsertDevice(list.get(i));
		}
		duplicateData.setMac(listForMac);
		duplicateData.setNumberOfSuccessful(list.size());
		duplicateData.setNumberOfUnsuccessful(listForMac.size());
		return "导入设备成功数：" + duplicateData.getNumberOfSuccessful() + "个，" + "导入失败："
				+ duplicateData.getNumberOfUnsuccessful() + "个，" + "与数据库冲突的mac地址有：" + duplicateData.getMac();
	}

}
