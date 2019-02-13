package com.hotcomm.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.service.DeviceType;
import com.hotcomm.data.bean.params.service.device.DeviceTypePageParams;
import com.hotcomm.data.bean.params.service.device.DeviceTypeParams;
import com.hotcomm.data.bean.vo.device.DeviceTypePageVO;
import com.hotcomm.data.bean.vo.device.DeviceTypeVO;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.DeviceTypeMapper;
import com.hotcomm.data.service.DeviceTypeService;
import com.hotcomm.framework.annotation.Slave;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class DeviceTypeServiceImpl implements DeviceTypeService {

	@Resource
	private DeviceTypeMapper deviceTypeMapper;

	@Autowired
	RedisHelper redisHelper;

	@Autowired
	HttpServletResponse response;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;

	/*
	 * 设备类型-增加
	 */
	@Transactional
	@Override
	public Integer addBean(DeviceTypeParams params) throws HKException {
		if (this.deviceTypeMapper.checkDeviceTypeNameExist(params.getTypeName()) != null)
			throw manager.create("DEVICETYPE00001");

		if (this.deviceTypeMapper.countByCode(params.getCode()) > 0)
			throw manager.create("DEVICETYPE00003");

		DeviceType deviceType = new DeviceType();
		BeanUtils.copyProperties(params, deviceType);

		deviceType.setCreateTime(new Date());
		deviceType.setCreateUser(params.getCreateUser());
		deviceType.setUpdateTime(new Date());
		this.deviceTypeMapper.insertSelective(deviceType);
		Integer typeId = deviceType.getTypeId();

		String key = Constant.DEV_TYPE_PRIFIX + typeId;
		redisHelper.set(key, JSON.toJSONString(deviceType).toString(), Constant.DEV_TYPE_KEEPTIMES);

		return typeId;
	}

	/*
	 * 设备类型-删除
	 */
	@Override
	@Transactional
	public void delBean(Integer typeId) throws HKException {
		if (this.deviceTypeMapper.checkDeviceTypeUsed(typeId) != null)
			throw manager.create("DEVICETYPE00002");

		this.deviceTypeMapper.deleteByPrimaryKey(typeId);
		String key = Constant.DEV_TYPE_PRIFIX + typeId;
		redisHelper.del(key);
	}

	/*
	 * 设备类型-更新
	 */
	@Override
	public void updateBean(DeviceTypeParams params) throws HKException {
		DeviceType query = new DeviceType();
		query.setTypeId(params.getTypeId());
		query = this.deviceTypeMapper.selectOne(query);

		if (query == null)
			return;

		if (!query.getTypeName().equals(params.getTypeName()) && this.deviceTypeMapper.checkDeviceTypeNameExist(params.getTypeName()) != null)
			throw manager.create("DEVICETYPE00001");

		if (!query.getCode().equals(params.getCode()) && this.deviceTypeMapper.countByCode(params.getCode()) > 0)
			throw manager.create("DEVICETYPE00003");

//		if (this.deviceTypeMapper.checkDeviceTypeUsed(params.getTypeId()) != null)
//			throw manager.create("DEVICETYPE00002");

		DeviceType deviceType = new DeviceType();
		BeanUtils.copyProperties(params, deviceType);
		deviceType.setUpdateTime(new Date());
		this.deviceTypeMapper.updateByPrimaryKeySelective(deviceType);

		String key = Constant.DEV_TYPE_PRIFIX + deviceType.getTypeId();
		redisHelper.set(key, JSON.toJSONString(deviceType).toString(), Constant.DEV_TYPE_KEEPTIMES);
	}

	/*
	 * 设备类型-获取
	 */
	@Override
	public DeviceTypeVO getBean(Integer typeId) throws HKException {
		DeviceType deviceType = this.deviceTypeMapper.selectByPrimaryKey(typeId);
		DeviceTypeVO vo = new DeviceTypeVO();

		if (deviceType != null)
			BeanUtils.copyProperties(deviceType, vo);

		return vo;
	}

	/*
	 * 设备类型-分页查询
	 */
	@Slave
	@Override
	public PageInfo<DeviceTypePageVO> queryPage(DeviceTypePageParams params) throws HKException {
		PageHelper.startPage(params.getPage(), params.getRows());
		Page<DeviceTypePageVO> pagelist = this.deviceTypeMapper.queryPage(params.getTypeName());
		List<DeviceTypePageVO> volist = new ArrayList<DeviceTypePageVO>();
		volist.addAll(pagelist);
		PageInfo<DeviceTypePageVO> info = new PageInfo<>(pagelist.getTotal(), volist);
		return info;
	}

	/*
	 * 设备类型-列表
	 */
	@Override
	public List<DeviceTypeVO> queryList() throws HKException {
		List<DeviceType> list = this.deviceTypeMapper.queryList();
		List<DeviceTypeVO> vos = new ArrayList<>(list.size());

		list.forEach(r -> {
			DeviceTypeVO vo = new DeviceTypeVO();
			BeanUtils.copyProperties(r, vo);
			vos.add(vo);
		});

		return vos;
	}

}
