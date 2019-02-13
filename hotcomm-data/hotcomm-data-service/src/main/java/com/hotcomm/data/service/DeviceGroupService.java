package com.hotcomm.data.service;

import java.util.List;

import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupPageParams;
import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupParams;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupListVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupPageVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupVO;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.data.service.common.PageService;
import com.hotcomm.framework.web.exception.HKException;

public interface DeviceGroupService extends CommFunService<DeviceGroupParams, DeviceGroupVO, Integer>, PageService<DeviceGroupPageParams, DeviceGroupPageVO> {

	List<DeviceGroupListVO> getGroupList(Integer memberId, Integer memberType, Integer selectMemberId) throws HKException;

	void groupAssignedToMember(Integer loginMemberId, Integer groupId, String groupMembers) throws HKException;

	void delDeviceGroupByMemberId(Integer customerId) throws HKException;

}
