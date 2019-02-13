package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.service.DeviceGroupMember;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceGroupMemberMapper extends Mapper<DeviceGroupMember> {

	// 判断是否重复分配设备组
	Integer getMemberGroupNum(@Param("groupId") Integer groupId, @Param("memberId") Integer memberId);

	// 删除设备组时,同时删除设备组用户关联表的记录
	void delMemberGroupByGroupId(@Param("groupId") Integer groupId, @Param("loginMemberId") Integer loginMemberId, @Param("memberId") Integer memberId);

	// 根据客户ID获取组ID
	List<Integer> getGourpIdsByMemberId(@Param("memberId") Integer memberId);

}
