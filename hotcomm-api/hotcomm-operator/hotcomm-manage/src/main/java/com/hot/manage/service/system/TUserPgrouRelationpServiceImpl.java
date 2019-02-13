package com.hot.manage.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.system.TUserPgrouRelationpMapper;
import com.hot.manage.entity.system.TUserPgrouRelationp;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TUserPgrouRelationpServiceImpl implements TUserPgrouRelationpService {
	@Autowired
	private TUserPgrouRelationpMapper userPgrouRelationpMapper;

	@Override
	public Integer insertRelation(TUserPgrouRelationp relation) throws MyException {
		return userPgrouRelationpMapper.insertSelective(relation);
	}

	@Override
	public Integer updateRelation(TUserPgrouRelationp relation) throws MyException {
		relation.setIsdelete(true);
		relation.setIsenable(false);
		Example example=new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("groupid", relation.getGroupid()).andEqualTo("isenable", true);
		userPgrouRelationpMapper.updateByExampleSelective(relation, example);
		return userPgrouRelationpMapper.updateByExampleSelective(relation, relation.getGroupid());
	}

	@Override
	public List<TUserPgrouRelationp> selectOne(Integer id) throws MyException {
		Example example=new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("userid", id).andEqualTo("isenable", true);
		List<TUserPgrouRelationp> list = userPgrouRelationpMapper.selectByExample(example);
		return list;
	}

	@Override
	public Integer update(TUserPgrouRelationp relation) throws MyException {
		relation.setIsdelete(true);
		relation.setIsenable(false);
		Example example=new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("userid", relation.getUserid()).andEqualTo("isenable", true);
		return userPgrouRelationpMapper.updateByExampleSelective(relation, example);
	}

	@Override
	public List<TUserPgrouRelationp> selectByRoleId(Integer id) throws MyException {
		return userPgrouRelationpMapper.selectByRoleId(id);
	}
	
	/**
	 * 更换角色
	 */
	@Override
	public Integer replaceRole(Integer groupid,Integer userid) throws MyException {
		TUserPgrouRelationp record=new TUserPgrouRelationp();
		record.setGroupid(groupid);
		Example example=new Example(TUserPgrouRelationp.class);
		example.createCriteria().andEqualTo("userid", userid).andEqualTo("isenable", true);
		int updateByExampleSelective = userPgrouRelationpMapper.updateByExampleSelective(record, example);
		return updateByExampleSelective;
	}

}
