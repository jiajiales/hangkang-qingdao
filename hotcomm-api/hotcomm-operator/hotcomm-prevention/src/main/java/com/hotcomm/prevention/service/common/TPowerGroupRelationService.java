package com.hotcomm.prevention.service.common;

import java.util.List;

import com.hotcomm.prevention.exception.MyException;

public interface TPowerGroupRelationService {

	void RoleResouceRelation(Integer roleid, String powerid) throws MyException;

	List<Integer> selectResByRole(Integer roleid) throws MyException;

}
