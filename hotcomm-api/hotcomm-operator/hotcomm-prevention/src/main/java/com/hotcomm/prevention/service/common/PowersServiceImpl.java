package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.common.entity.TPower;
import com.hotcomm.prevention.db.mysql.common.PowerMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class PowersServiceImpl implements PowersService {
	@Autowired
	PowerMapper powerMapper;
	
	/**
	 * 菜单页面->点击相应菜单获取页面加载权限
	 */
	@Override
	public List<TPower> selectPowersByUserid(Integer fatherid, Integer userid) throws MyException {
		List<TPower> powers = new ArrayList<>();
		List<TPower> list = powerMapper.selectPowersByUserid(fatherid, userid);
		List<TPower> recursive = permissionsRecursive(list, powers, userid);
		return recursive;
	}

	/**
	 * 权限递归
	 * @return
	 */
	public List<TPower> permissionsRecursive(List<TPower> list, List<TPower> powers, Integer userid) {
		powers.addAll(list);
		if (list.size() != 0) {
			for (TPower tPower : list) {
				List<TPower> arrays = powerMapper.selectPowersByUserid(tPower.getId(), userid);
				permissionsRecursive(arrays, powers, userid);
			}
		}
		return powers;
	}
	
	/**
	 * 系统设置->资源管理->当前用户的所有资源
	 */
	@Override
	public List<TPower> selectPowers(Integer userid) throws MyException {
		return powerMapper.selectPowers(userid);
	}
	
	/**
	 * 系统设置->资源管理->修改
	 */
	@Override
	public void update(TPower power) throws MyException {
		powerMapper.updateByPrimaryKeySelective(power);
	}

}
