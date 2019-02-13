package com.hotcomm.prevention.service.common;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserPgrouRelationp;
import com.hotcomm.prevention.exception.MyException;

public interface TUserPgrouRelationpService {

	Integer selectRoleid(Integer userid) throws MyException;

	void update(TUserPgrouRelationp relation) throws MyException;

	void insert(TUserPgrouRelationp relation) throws MyException;

}
