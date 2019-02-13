package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.sys.Role;

import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {

	void addRoleResources(@Param("roleId") Integer roleId, @Param("resourceIds") String[] resourceIds);

	void delRoleResources(@Param("roleId") Integer roleId);

	List<Integer> getRoleResources(@Param("roleId") Integer roleId);

	Long chexisRole(@Param("roleId") Integer roleId);

}
