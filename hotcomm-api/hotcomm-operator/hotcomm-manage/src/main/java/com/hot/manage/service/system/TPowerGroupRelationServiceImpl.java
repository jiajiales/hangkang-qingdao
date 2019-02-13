package com.hot.manage.service.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.manage.db.system.TPowerGroupRelationMapper;
import com.hot.manage.db.system.TPowerMapper;
import com.hot.manage.entity.system.TPowerGroupRelation;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.entity.Example;
@Service
@Transactional
public class TPowerGroupRelationServiceImpl implements TPowerGroupRelationService {
	
	@Autowired
	private TPowerGroupRelationMapper TPowerGroupRelationMapper;
	@Autowired
	TPowerMapper PowerMapper;

	//添加资源和角色关联
	@Override
	public Integer insertRelation(TPowerGroupRelation params) throws MyException {	
		return TPowerGroupRelationMapper.insertSelective(params);
	}
	//解除资源和角色关联
	@Override
	public Integer delRelation(TPowerGroupRelation params) throws MyException {
		return TPowerGroupRelationMapper.deleteByExample(params);
	}
	
	@Override
	public Integer delByResouce(Integer resouceId) throws MyException {
		return TPowerGroupRelationMapper.deleteByExample(resouceId);
	}
	
	@Override
	public List<TPowerGroupRelation> selectByResouceId(Integer id) throws MyException {
		return TPowerGroupRelationMapper.selectByResouceId(id);
	}
	
	/**
	 * 角色与资源绑定、解除绑定
	 */
	@Override
	public void RoleResouceRelation( Integer roleid, String powerid) throws MyException {
		Example example=new Example(TPowerGroupRelation.class);
		example.createCriteria().andEqualTo("groupid", roleid);
		TPowerGroupRelationMapper.deleteByExample(example);
		if (!TextUtils.isEmpty(powerid)) {
			String[] split = powerid.split(",");
			for (String s : split) {
				List<TPowerGroupRelation> list = new ArrayList<>();
				TPowerGroupRelation relation = new TPowerGroupRelation();
				relation.setGroupid(roleid);
				relation.setPowerid(Integer.parseInt(s));
				list.add(relation);
				TPowerGroupRelationMapper.insertBatch(list);
			}	
		}
	}
	/**
	 * 根据角色ID查询资源
	 */
	@Override
	public List<Integer> selectResByRole(Integer roleid) {
		return TPowerGroupRelationMapper.selectResByRole(roleid);
	}
	
	/**
	 * 根据父类ID查询资源
	 */
	@Override
	public String selectResFatherId(Integer roleid, Integer fatherId) {
		return TPowerGroupRelationMapper.selectResFatherId(roleid,fatherId);
	}
}
