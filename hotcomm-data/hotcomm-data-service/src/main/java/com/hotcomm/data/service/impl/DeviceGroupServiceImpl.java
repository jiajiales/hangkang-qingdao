package com.hotcomm.data.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.service.DeviceGroup;
import com.hotcomm.data.bean.entity.service.DeviceGroupMember;
import com.hotcomm.data.bean.enums.DeviceGroupEnum.DeviceGroupDeleteStatus;
import com.hotcomm.data.bean.enums.DeviceGroupEnum.DeviceGroupStatus;
import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupPageParams;
import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupParams;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupPageVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupListVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.DeviceGroupMapper;
import com.hotcomm.data.db.DeviceGroupMemberMapper;
import com.hotcomm.data.db.DeviceMapper;
import com.hotcomm.data.service.CustomerService;
import com.hotcomm.data.service.DataPushReadyService;
import com.hotcomm.data.service.DeviceGroupService;
import com.hotcomm.data.service.common.AbstractFunServiceImpl;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class DeviceGroupServiceImpl extends AbstractFunServiceImpl<DeviceGroupVO, DeviceGroup> implements DeviceGroupService {

	@Autowired
	DeviceMapper deviceMapper;

	@Autowired
	DeviceGroupMapper deviceGroupMapper;

	@Autowired
	DeviceGroupMemberMapper deviceGroupMemberMapper;

	@Autowired
	CustomerService customerService;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;
	
	@Autowired
	DataPushReadyService dataPushReadyService;
	
	/*
	 * 新增设备组
	 */
	@Override
	public Integer addBean(DeviceGroupParams params) throws HKException {
		if (deviceGroupMapper.checkDeviceGroupNameExist(params.getGroupName()) != null)
			throw manager.create("DEVICEGROUP00001");

		DeviceGroup deviceGroup = new DeviceGroup();
		BeanUtils.copyProperties(params, deviceGroup);
		deviceGroup.setIsDelete(DeviceGroupDeleteStatus.NO.getValue());
		deviceGroupMapper.insertSelective(deviceGroup);
		return 0;
	}

	/*
	 * 根据组ID删除设备组
	 */
	@Override
	public void delBean(Integer id) throws HKException {
		if (deviceGroupMapper.getDevNums(id) > 0)
			throw manager.create("DEVICEGROUP00002");

		DeviceGroup deviceGroup = new DeviceGroup();
		deviceGroup = deviceGroupMapper.selectByPrimaryKey(id);
		deviceGroup.setGroupStatus(DeviceGroupStatus.DISABLE.getValue());
		deviceGroup.setIsDelete(DeviceGroupDeleteStatus.YES.getValue());
		deviceGroup.setGroupName(deviceGroup.getGroupName() + "DEL@" + id);
		deviceGroupMapper.updateByPrimaryKeySelective(deviceGroup);
		deviceGroupMemberMapper.delMemberGroupByGroupId(id, null, null);
		
		dataPushReadyService.deviceGroupDel(deviceGroup.getGroupId());
	}

	/*
	 * 更新设备组
	 */
	@Override
	public void updateBean(DeviceGroupParams params) throws HKException {
		DeviceGroup deviceGroup = new DeviceGroup();

		if (params.getGroupName() != null) {
			deviceGroup = deviceGroupMapper.selectByPrimaryKey(params.getGroupId());
			if(!params.getGroupName().equals(deviceGroup.getGroupName())) {
				if (deviceGroupMapper.checkDeviceGroupNameExist(params.getGroupName()) != null)
					throw manager.create("DEVICEGROUP00001");
			}
		}

		// 判断要更新的设备数量阀值是否低于该设备组已关联的设备数量
		if (params.getMaxNums() < deviceGroupMapper.getDevNums(params.getGroupId()))
			throw manager.create("DEVICEGROUP00003");

		BeanUtils.copyProperties(params, deviceGroup);
		deviceGroupMapper.updateByPrimaryKeySelective(deviceGroup);
	}

	/*
	 * 根据组ID查询设备组信息
	 */
	@Override
	public DeviceGroupVO getBean(Integer id) throws HKException {
		return deviceGroupMapper.getGroupById(id);
	}

	/*
	 * 分页查询设备组信息
	 */
	@Override
	public PageInfo<DeviceGroupPageVO> queryPage(DeviceGroupPageParams params) throws HKException {
		PageHelper.startPage(params.getPage(), params.getRows());
		Page<DeviceGroupPageVO> pagelist = deviceGroupMapper.queryPage(params);
		PageInfo<DeviceGroupPageVO> info = new PageInfo<>(pagelist.getTotal(), pagelist);
		return info;
	}

	/*
	 * 获取设备组列表
	 */
	@Override
	public List<DeviceGroupListVO> getGroupList(Integer memberId, Integer memberType, Integer selectMemberId) throws HKException {
		return deviceGroupMapper.getGroupList(memberId, memberType, selectMemberId);
	}

	/*
	 * 设备组分配给用户
	 */
	@Override
	public void groupAssignedToMember(Integer loginMemberId, Integer groupId, String groupMembers) throws HKException {
		if (groupMembers != null) {
			List<String> result = Arrays.asList(groupMembers.split(","));
			deviceGroupMemberMapper.delMemberGroupByGroupId(groupId, loginMemberId, null);

			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).length() > 0) {
					Integer memberId = Integer.parseInt(result.get(i));
					DeviceGroupMember deviceGroupMember = new DeviceGroupMember();
					deviceGroupMember.setDeviceGroupId(groupId);
					deviceGroupMember.setMemberId(memberId);
					deviceGroupMemberMapper.insertSelective(deviceGroupMember);
				}
			}
		}
	}

	/*
	 * 根据客户ID删除关联的设备组、设备、设备组客户中间关联记录
	 */
	@Override
	public void delDeviceGroupByMemberId(Integer customerId) throws HKException {
		List<Integer> deviceGourpIdList = deviceGroupMemberMapper.getGourpIdsByMemberId(customerId);

		// 删除客户关联设备组(软删除) - 数据库
        for (int i = 0; i < deviceGourpIdList.size(); i++) {
        	int deviceGroupId = deviceGourpIdList.get(i);
    		DeviceGroup deviceGroup = new DeviceGroup();
    		deviceGroup = deviceGroupMapper.selectByPrimaryKey(deviceGroupId);
    		deviceGroup.setGroupStatus(DeviceGroupStatus.DISABLE.getValue());
    		deviceGroup.setIsDelete(DeviceGroupDeleteStatus.YES.getValue());
    		deviceGroup.setGroupName(deviceGroup.getGroupName() + "DEL@" + deviceGroupId);
    		deviceGroupMapper.updateByPrimaryKey(deviceGroup);

    		// 删除客户关联设备组下的关联设备(软删除) - 数据库
    		deviceMapper.delDeviceByDeviceGroupId(deviceGroupId);
        }

		// 删除hk_device_group_member中跟member_id关联的记录 - 数据库
		deviceGroupMemberMapper.delMemberGroupByGroupId(null, null, customerId);
	}

}
