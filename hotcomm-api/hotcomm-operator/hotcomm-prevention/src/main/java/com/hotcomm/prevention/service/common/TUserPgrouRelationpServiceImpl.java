package com.hotcomm.prevention.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserPgrouRelationp;
import com.hotcomm.prevention.db.mysql.common.TUserPgrouRelationpMapper;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
public class TUserPgrouRelationpServiceImpl implements TUserPgrouRelationpService {
	
	@Autowired
	TUserPgrouRelationpMapper userPgrouRelationpMapper;
	
	//查询用户的角色ID
	@Override
	public Integer selectRoleid(Integer userid) throws MyException {
		Example example = new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("userid", userid).andEqualTo("isenable", true);
		List<TUserPgrouRelationp> list = userPgrouRelationpMapper.selectByExample(example);
		if (list.size() != 0) {
			return list.get(0).getGroupid();
		}
		return null;
	}
	
	/**
	 * 系统设置->用户管理->解除用户与角色绑定
	 */
	@Override
	public void update(TUserPgrouRelationp relation) throws MyException {
		Example example=new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("userid", relation.getUserid()).andEqualTo("isenable", true);
		userPgrouRelationpMapper.updateByExampleSelective(relation, example);
	}
	
	/**
	 * 系统设置->用户管理->添加绑定关系
	 */
	@Override
	public void insert(TUserPgrouRelationp relation) throws MyException {
		userPgrouRelationpMapper.insertSelective(relation);
	}

}
