package com.hotcomm.data.service;

import java.io.InputStream;

import com.hotcomm.data.bean.entity.service.Device;
import com.hotcomm.data.bean.params.service.device.DeviceBatchParams;
import com.hotcomm.data.bean.params.service.device.LoraABPDevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraABPDeviceParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADeviceParam;
import com.hotcomm.data.bean.vo.device.LoraABPDeviceVO;
import com.hotcomm.data.bean.vo.device.LoraOTAADeviceVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.utils.ExcelData;
import com.hotcomm.framework.web.exception.HKException;

public interface DeviceService  {

	/**
	 * 判断是否存在
	 * @param deviceCode
	 * @return
	 * @throws HKException
	 */
	Device existsDevice(String deviceCode) throws HKException;
	
	/**
	 * 删除
	 * @param deviceId
	 * @throws HKException
	 */
	void delDev(Integer deviceId)throws HKException;
	
	/**
	 * 批量分配设备组
	 * @param params
	 * @throws HKException
	 */
	void batchAllotDeviceGroup(DeviceBatchParams param) throws HKException;
	
	/**
	 * 添加Lora ABP 设备
	 * @param param
	 * @return
	 * @throws HKException
	 */
	Integer addLoraABPDev(LoraABPDeviceParam param)throws HKException;
	
	/**
	 * 添加 Lora OTAA 设备  
	 * @param param
	 * @return
	 * @throws HKException
	 */
	Integer addLoraOTAADev(LoraOTAADeviceParam param)throws HKException;
	
	/**
	 * 修改 Lora ABP 设备
	 * @param param
	 * @throws HKException
	 */
	void updateLoraABPDev(LoraABPDeviceParam param)throws HKException;
	
	/**
	 * 修改 Lora OTT 设备
	 * @param param
	 * @throws HKException
	 */
	void updateLoraOTAADev(LoraOTAADeviceParam param)throws HKException;
	
	/**
	 * 获取 Lora ABP 设备
	 * @param deviceId
	 * @return
	 */
	LoraABPDeviceVO getLoraABP(Integer deviceId);
	
	/***
	 * 获取 Lora OTAA设备
	 * @param deviceId
	 * @return
	 */
	LoraOTAADeviceVO getLoraOTAA(Integer deviceId);
	
	/**
	 * 分页  Lora ABP 设备
	 * @param pageParam
	 * @return
	 */
	PageInfo<LoraABPDeviceVO> pageLoraABP(LoraABPDevicePageParam pageParam);

	/**
	 * 分页  Lora OTAA 设备
	 * @param pageParam
	 * @return
	 */
	PageInfo<LoraOTAADeviceVO> pageLoraOTAA(LoraOTAADevicePageParam pageParam);

	/**
	 * 导入Lora ABP设备
	 * @param file
	 * @param groupid
	 * @param customerId
	 */
	Object importLoraABPDev(InputStream fileInput, Integer groupid,String createUser)throws HKException;
	
	/**
	 * 导入Lora OTAA设备
	 * @param file
	 * @param groupid
	 * @param customerId
	 */
	Object importLoraOTAADev(InputStream fileInput, Integer groupid,String createUser)throws HKException;
	
	/**
	 * 导出 Lora ABP设备
	 * @param devGroupId
	 * @param memberId
	 */
	ExcelData exportLoraABPDev(Integer devGroupId, Integer memberId);
	
	/**
	 * 导出 Lora OTAA设备
	 * @param devGroupId
	 * @param memberId
	 */
	ExcelData exportLoarOTAADev(Integer devGroupId, Integer memberId);
	
	
	/**
	 * 更新设备接收数据量(自增)
	 * @param deviceId
	 */
	void updateReceiveNums(Integer deviceId)throws HKException;
	
	void updateReceiveNums(String deviceCode)throws HKException;

	/**
	 * 获取设备接收数据量
	 * @param deviceCode
	 */
	Integer getReceiveNumByDeviceCode(String deviceCode) throws HKException;

}
