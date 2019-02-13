package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.service.DeviceType;
import com.hotcomm.data.bean.vo.device.DeviceTypePageVO;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceTypeMapper extends Mapper<DeviceType> {

	Page<DeviceTypePageVO> queryPage(@Param("typeName") String typeName);

	List<DeviceType> queryList();

	Integer checkDeviceTypeUsed(@Param("typeId") Integer typeId);

	Integer checkDeviceTypeNameExist(@Param("typeName") String typeName);

	Integer countByCode(@Param("code") String code);

}
