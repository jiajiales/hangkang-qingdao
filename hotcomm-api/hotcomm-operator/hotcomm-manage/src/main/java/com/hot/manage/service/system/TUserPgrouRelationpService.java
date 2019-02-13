package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.system.TUserPgrouRelationp;
import com.hot.manage.exception.MyException;

public interface TUserPgrouRelationpService {
	
	Integer insertRelation(TUserPgrouRelationp relation) throws MyException;
	
	Integer updateRelation(TUserPgrouRelationp relation) throws MyException;
	
	List<TUserPgrouRelationp> selectOne(Integer id) throws MyException;
	
	Integer update(TUserPgrouRelationp relation) throws MyException;
	
	List<TUserPgrouRelationp> selectByRoleId(Integer id)throws MyException;
	
	Integer replaceRole(Integer groupid,Integer userid)throws MyException;
}
