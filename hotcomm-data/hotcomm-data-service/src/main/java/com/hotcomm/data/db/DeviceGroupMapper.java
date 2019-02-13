package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.service.DeviceGroup;
import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupPageParams;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupPageVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupListVO;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceGroupMapper extends Mapper<DeviceGroup> {

	// 根据组ID获取组信息
	DeviceGroupVO getGroupById(@Param("groupId") Integer groupId);

	// 分页查询组信息
	Page<DeviceGroupPageVO> queryPage(@Param("params") DeviceGroupPageParams params);

	// 获取设备组列表
	List<DeviceGroupListVO> getGroupList(@Param("memberId") Integer memberId, @Param("memberType") Integer memberType,
			@Param("selectMemberId") Integer selectMemberId);

	/**
	 * 查询设备组剩余可添加数量
	 * @param deviceGroupId
	 * @return
	 */
	Integer getRemainDevNums(@Param("groupId")Integer deviceGroupId);

	/***
	 * 查询 设备组关联设备数量
	 * @param deviceGroupId
	 * @return
	 */
	Integer getDevNums(@Param("groupId")Integer deviceGroupId);

	// 查询设备组名称是否存在
	Integer checkDeviceGroupNameExist(@Param("GroupName") String GroupName);

}
