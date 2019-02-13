package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroupRelation;
import com.hotcomm.prevention.db.mysql.common.TPowerGroupRelationMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.TextUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class TPowerGroupRelationServiceImpl implements TPowerGroupRelationService {
	@Autowired
	TPowerGroupRelationMapper powerGroupRelationMapper;
	
	/**
	 * 系统设置->角色管理->角色与资源绑定、解绑
	 */
	@Override
	public void RoleResouceRelation(Integer roleid, String powerid) throws MyException {
		Example example=new Example(TPowerGroupRelation.class);
		example.createCriteria().andEqualTo("groupid", roleid);
		powerGroupRelationMapper.deleteByExample(example);
		if (!TextUtils.isEmpty(powerid)) {
			String[] split = powerid.split(",");
			for (String s : split) {
				List<TPowerGroupRelation> list = new ArrayList<>();
				TPowerGroupRelation relation = new TPowerGroupRelation();
				relation.setGroupid(roleid);
				relation.setPowerid(Integer.parseInt(s));
				list.add(relation);
				powerGroupRelationMapper.insertBatch(list);
			}	
		}
	}
	
	/**
	 * 系统设置->角色管理->指定角色资源集合
	 */
	@Override
	public List<Integer> selectResByRole(Integer roleid) throws MyException {
		return powerGroupRelationMapper.selectResByRole(roleid);
	}
	
	

}
