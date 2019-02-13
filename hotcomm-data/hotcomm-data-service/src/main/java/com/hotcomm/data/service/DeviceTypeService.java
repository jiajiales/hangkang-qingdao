package com.hotcomm.data.service;

import java.util.List;

import com.hotcomm.data.bean.params.service.device.DeviceTypePageParams;
import com.hotcomm.data.bean.params.service.device.DeviceTypeParams;
import com.hotcomm.data.bean.vo.device.DeviceTypePageVO;
import com.hotcomm.data.bean.vo.device.DeviceTypeVO;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.data.service.common.PageService;
import com.hotcomm.framework.web.exception.HKException;

public interface DeviceTypeService extends CommFunService<DeviceTypeParams, DeviceTypeVO, Integer>,
		PageService<DeviceTypePageParams, DeviceTypePageVO> {

	List<DeviceTypeVO> queryList() throws HKException;

}
