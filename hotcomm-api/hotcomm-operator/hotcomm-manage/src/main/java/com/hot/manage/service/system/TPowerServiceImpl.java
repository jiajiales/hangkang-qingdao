package com.hot.manage.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.config.RedisHelper;
import com.hot.manage.db.system.TPowerMapper;
import com.hot.manage.entity.MenuCategoryNode;
import com.hot.manage.entity.common.user.PowerModel;
import com.hot.manage.entity.system.TPower;
import com.hot.manage.entity.system.TPowerGroup;
import com.hot.manage.entity.system.TPowerGroupRelation;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Service
@Transactional
public class TPowerServiceImpl implements TPowerService {

	@Autowired
	private TPowerMapper powerMapper;
	@Autowired
	private TPowerGroupRelationService powerGroupRelationService;
	@Autowired
	private TPowerGroupService powerGroupService;
	@Autowired
	private RedisHelper redisHelper;

	// 添加资源，添加绑定
	@Override
	public Integer insertObject(TPower power, Integer userid) throws MyException {
		power.setAddtime(ConverUtil.timeForString(new Date()));
		powerMapper.insertTPower(power);
		// 查询当前用户的角色
		TPowerGroup role = powerGroupService.selectByUserId(userid);
		TPowerGroupRelation relation = new TPowerGroupRelation();
		relation.setGroupid(role.getId());
		relation.setPowerid(power.getId());
		// 绑定资源
		Integer insert = powerGroupRelationService.insertRelation(relation);
		if (insert <= 0) {
			return 0;
		}
		String menukey = "powerList_" + userid;
		String powerkey = "power_" + userid;
		List<PowerModel> list = redisHelper.getList(menukey, PowerModel.class);

		// List<String> strs = redisHelper.getList(powerkey, String.class);

		String str = redisHelper.get(powerkey);
		str += "," + power.getAnnotation();
		PowerModel model = new PowerModel();
		BeanUtils.copyProperties(power, model);
		list.add(model);
		redisHelper.setList(menukey, list, 60 * 20);
		redisHelper.set(powerkey, str, 60 * 20);
		return 1;
	}

	@Override
	public Integer insertOne(TPower power) throws MyException {
		return null;
	}

	// 超级管理员调用
	@Override
	public Integer updateObject(TPower params) throws MyException {
		int update = powerMapper.updateByKeySelective(params);
		if (update <= 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public Integer delObject(Integer id) throws MyException {
		// 1、判断当前资源是否绑定角色
		List<TPowerGroupRelation> list = powerGroupRelationService.selectByResouceId(id);
		// 2、删除资源
		if (list.size() == 0) {
			TPower power = new TPower();
			power.setId(id);
			power.setIsdelete(true);
			power.setIsenable(false);
			return updateObject(power);
		}
		return 0;
	}

	@Override
	public TPower selectObject(Integer id) throws MyException {
		return powerMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TPower> selectAllPower(Integer userid) throws MyException {
		return powerMapper.selectAllById(userid);
	}

	// 查询当前用户所有资源
	@Override
	public List<MenuCategoryNode> selectAll(Integer userid) throws MyException {
		List<MenuCategoryNode> node = new ArrayList<>();
		List<TPower> list = powerMapper.selectpowerByfatherid(userid, 0);
		for (TPower tPower : list) {
			MenuCategoryNode menu = new MenuCategoryNode();
			BeanUtils.copyProperties(tPower, menu);
			menu.setLabel(tPower.getDescription());
			node.add(menu);
		}
		return recursion(node,userid);
	}
	
	//权限递归
	public List<MenuCategoryNode> recursion(List<MenuCategoryNode> node,Integer userid){
		if (node.size()!=0) {
			for (MenuCategoryNode menu : node) {
				List<TPower> newarray = powerMapper.selectpowerByfatherid(userid, menu.getId());
				List<MenuCategoryNode> newnode = new ArrayList<>();
				for (TPower tPower : newarray) {
					MenuCategoryNode mn = new MenuCategoryNode();
					BeanUtils.copyProperties(tPower, mn);
					mn.setLabel(tPower.getDescription());
					newnode.add(mn);
				}
				recursion(newnode,userid);
				menu.setChildren(newnode);
			}
		}
		return node;
	}
	
	/**
	 * 根据资源ID，查询资源信息
	 */
	@Override
	public TPower selectInfo(Integer id) {
		return powerMapper.selectById(id);
	}
	
	/**
	 * 查询某一用户是否有app登陆的权限
	 */
	@Override
	public TPower selectOneById(Integer userid) throws MyException {
		return powerMapper.selectOneById(userid);
	}
	
	/**
	 * 根据fatherid查询资源
	 */
	@Override
	public List<TPower> selectByFatherid(Integer fatherid) throws MyException {
		return powerMapper.selectByFatherid(fatherid);
	}
}
