package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.system.TPowerGroupRelation;
import com.hot.manage.exception.MyException;

public interface TPowerGroupRelationService{
	
	Integer insertRelation(TPowerGroupRelation params) throws MyException;
	
	Integer delRelation(TPowerGroupRelation params) throws MyException;
	
	Integer delByResouce(Integer resouceId) throws MyException;
	
	List<TPowerGroupRelation> selectByResouceId(Integer id)throws MyException;
	
	void RoleResouceRelation(Integer roleid,String powerid)throws MyException;
	
	List<Integer> selectResByRole(Integer roleid);

	String selectResFatherId(Integer roleid, Integer fatherId);
}
