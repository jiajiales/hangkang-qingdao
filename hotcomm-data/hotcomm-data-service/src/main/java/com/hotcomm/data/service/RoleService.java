package com.hotcomm.data.service;

import java.util.List;

import com.hotcomm.data.bean.params.sys.RoleParams;
import com.hotcomm.data.bean.vo.sys.RoleResourceVO;
import com.hotcomm.data.bean.vo.sys.RoleVO;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.framework.web.exception.HKException;

public interface RoleService extends CommFunService<RoleParams, RoleVO, Integer> {

	public List<RoleVO> queryList() throws HKException;

	public void saveRoleResources(RoleParams params) throws HKException;

	public RoleResourceVO getRoleResources(Integer roleId) throws HKException;

	public boolean chexisRole(Integer roleId) throws HKException;

}
