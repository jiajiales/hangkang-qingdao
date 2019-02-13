package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroup;
import com.hotcomm.prevention.bean.mysql.common.entity.TUserPgrouRelationp;
import com.hotcomm.prevention.db.mysql.common.TPowerGroupMapper;
import com.hotcomm.prevention.db.mysql.common.TUserPgrouRelationpMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class TPowerGroupServiceImpl implements TPowerGroupService {
	@Autowired
	TPowerGroupMapper powerGroupMapper;
	@Autowired
	TUserPgrouRelationpMapper userPgrouRelationpMapper;

	/**
	 * 系统设置->角色管理->新增
	 */
	@Override
	public void insert(TPowerGroup powerGroup) throws MyException {
		powerGroup.setAddtime(ConverUtil.dateForString(new Date()));
		powerGroupMapper.insertSelective(powerGroup);
	}

	/**
	 * 系统设置->角色管理->修改
	 */
	@Override
	public void update(TPowerGroup powerGroup) throws MyException {
		powerGroupMapper.updateByPrimaryKeySelective(powerGroup);
	}

	/**
	 * 系统设置->角色管理->删除
	 */
	@Transactional
	@Override
	public void delete(Integer id) throws MyException {
		Example example = new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("groupid", id).andEqualTo("isenable", true);
		List<TUserPgrouRelationp> list = userPgrouRelationpMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此角色存在绑定用户");
		}
		TPowerGroup record = new TPowerGroup();
		record.setIsenable(false);
		record.setIsdelete(true);
		record.setId(id);
		powerGroupMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 系统设置->角色管理->当前用户角色
	 */
	@Override
	public TPowerGroup selectByUserid(Integer userid) throws MyException {
		return powerGroupMapper.selectRoleByUserid(userid);
	}

	/**
	 * 系统设置->角色管理->当前用户及下级角色
	 */
	@Override
	public List<TPowerGroup> selectAllRoles(Integer userid) throws MyException {
		List<TPowerGroup> array = new ArrayList<>();
		TPowerGroup role = powerGroupMapper.selectRoleByUserid(userid);
		List<TPowerGroup> list = powerGroupMapper.selectRolesByFid(role.getId());
		List<TPowerGroup> recursion = recursion(list, array);
		recursion.add(role);
		return recursion;
	}

	/**
	 * 角色递归
	 * 
	 * @return
	 */
	public List<TPowerGroup> recursion(List<TPowerGroup> list, List<TPowerGroup> array) {
		array.addAll(list);
		if (list.size() != 0) {
			for (TPowerGroup tPowerGroup : list) {
				List<TPowerGroup> roles = powerGroupMapper.selectRolesByFid(tPowerGroup.getId());
				recursion(roles, array);
			}
		}
		return array;
	}
}
