package com.hotcomm.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.sys.Role;
import com.hotcomm.data.bean.params.sys.RoleParams;
import com.hotcomm.data.bean.vo.sys.RoleResourceVO;
import com.hotcomm.data.bean.vo.sys.RoleVO;
import com.hotcomm.data.db.RoleMapper;
import com.hotcomm.data.service.RoleService;
import com.hotcomm.framework.annotation.Slave;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Resource
	RoleMapper roleMapper;

	@Override
	@Transactional
	public Integer addBean(RoleParams params) throws HKException {
		Integer id = null;
		Role role = new Role();
		BeanUtils.copyProperties(params, role);
		roleMapper.insertSelective(role);
		return id;
	}

	@Override
	@Transactional
	public void delBean(Integer id) throws HKException {
		roleMapper.deleteByPrimaryKey(id);
		roleMapper.delRoleResources(id);
	}

	@Override
	@Transactional
	public void updateBean(RoleParams params) throws HKException {
		Role role = new Role();
		BeanUtils.copyProperties(params, role);
		if (role.getStatus() == null) {
			role.setStatus(2);
		}
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public RoleVO getBean(Integer id) throws HKException {
		Role role = roleMapper.selectByPrimaryKey(id);
		RoleVO vo = new RoleVO();
		BeanUtils.copyProperties(role, vo);
		return vo;
	}

	@Slave
	public boolean chexisRole(Integer roleId) throws HKException {
		Long chexisRoleNum = roleMapper.chexisRole(roleId);
		if (chexisRoleNum > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<RoleVO> queryList() throws HKException {
		List<RoleVO> vos = new ArrayList<>(0);
		roleMapper.selectAll().forEach(r -> {
			RoleVO vo = new RoleVO();
			BeanUtils.copyProperties(r, vo);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	@Transactional
	public void saveRoleResources(RoleParams params) throws HKException {
		Integer roleId = params.getId();
		String resourceIds = params.getResourceIds();
		if (resourceIds == null || resourceIds.split(",").length == 0 || resourceIds.length() == 0) {
			// throw new HKException("E001", "资源编号不能为空");
			roleMapper.delRoleResources(roleId);
		} else {
			roleMapper.delRoleResources(roleId);
			roleMapper.addRoleResources(roleId, resourceIds.split(","));
		}
	}

	@Override
	public RoleResourceVO getRoleResources(Integer roleId) throws HKException {
		RoleResourceVO vo = new RoleResourceVO();
		StringBuffer resIds = new StringBuffer();
		roleMapper.getRoleResources(roleId).forEach(resId -> {
			resIds.append(resId).append(",");
		});
		vo.setRoleId(roleId);
		String rsIds = resIds.toString();
		if (rsIds.length() > 1) {
			rsIds = rsIds.substring(0, rsIds.length() - 1);
		}
		vo.setResourceIds(rsIds);
		;
		return vo;
	}

}
