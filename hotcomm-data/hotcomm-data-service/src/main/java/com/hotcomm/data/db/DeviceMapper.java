package com.hotcomm.data.db;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.service.Device;
import com.hotcomm.data.bean.params.service.device.LoraABPDevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADevicePageParam;
import com.hotcomm.data.bean.vo.device.LoraABPDeviceVO;
import com.hotcomm.data.bean.vo.device.LoraOTAADeviceVO;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceMapper extends Mapper<Device> {

	void receiveNumIncrement(Map<String, Object> param);

	void batchAddDevice(@Param("devices") Set<Device> device);

	void batchAllotDeviceGroup(@Param("deviceGroupId") Integer deviceGroupId, @Param("deviceIds") String[] deviceIds);

	LoraABPDeviceVO queryLoraABPDev(Integer deviceId);

	LoraOTAADeviceVO queryLoraOTAADev(Integer deviceId);

	void updateLoraJson(Map<String, Object> param);

	Integer existsCode(Device device);

	Page<LoraABPDeviceVO> queryPageLoraABPDev(LoraABPDevicePageParam pageParam);

	Page<LoraOTAADeviceVO> queryPageLoraOTAADev(LoraOTAADevicePageParam pageParam);

	void delDeviceByDeviceGroupId(@Param("deviceGroupId") Integer deviceGroupId);

	Integer getReceiveNumByDeviceCode(@Param("deviceCode") String deviceCode);

}
