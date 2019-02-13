package com.hot.manage.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.system.TPowerGroupMapper;
import com.hot.manage.entity.system.TPowerGroup;
import com.hot.manage.entity.system.TUserPgrouRelationp;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Service
@Transactional
public class TPowerGroupServiceImpl implements TPowerGroupService {

	@Autowired
	private TPowerGroupMapper powerGroupMapper;
	@Autowired
	private TUserPgrouRelationpService userPgrouRelationpService;

	@Override
	public Integer insertObject(TPowerGroup params, Integer userid) throws MyException {
		params.setAddtime(ConverUtil.timeForString(new Date()));
		int in = powerGroupMapper.insertSelective(params);
		if (in<=0) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public Integer insertRole(TPowerGroup powerGroup) throws MyException {
		return null;
	}

	@Override
	public Integer updateObject(TPowerGroup params) throws MyException {
		return powerGroupMapper.updateByPrimaryKeySelective(params);
	}
	
	@Override
	public Integer delObject(Integer id) throws MyException {
		// 1、判断当前角色是否绑定用户
		List<TUserPgrouRelationp> list = userPgrouRelationpService.selectByRoleId(id);
		if (list.size() != 0) {
			throw new MyException("0", "次角色存在绑定用户！！");
		}
		// 2、删除角色
		TPowerGroup group = new TPowerGroup();
		group.setId(id);
		group.setIsdelete(true);
		group.setIsenable(false);
		int up = powerGroupMapper.updateByPrimaryKeySelective(group);
		if (up <= 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public TPowerGroup selectObject(Integer id) throws MyException {
		return powerGroupMapper.selectByPrimaryKey(id);
	}
	
	// 当期用户能查看的所有角色
	@Override
	public List<TPowerGroup> selectAllRoleByUserId(Integer userid) throws MyException {
		List<TPowerGroup> list = new ArrayList<>();
		TPowerGroup group = powerGroupMapper.selectByUserId(userid);
		list.add(group);
		List<TPowerGroup> roles = powerGroupMapper.selctByFatherId(group.getId());
		List<TPowerGroup> allRoles = recursion(roles, list);
		return allRoles;
	}

	@Override
	public TPowerGroup selectByUserId(Integer userid) throws MyException {
		// 查询当前用户的角色
		return powerGroupMapper.selectByUserId(userid);
	}

	// 递归
	public List<TPowerGroup> recursion(List<TPowerGroup> roles, List<TPowerGroup> list) throws MyException {
		if (roles.size() != 0) {
			for (TPowerGroup tPowerGroup : roles) {
				list.add(tPowerGroup);
				List<TPowerGroup> nlist = powerGroupMapper.selctByFatherId(tPowerGroup.getId());
				recursion(nlist, list);
			}
		}
		return list;
	}
}
