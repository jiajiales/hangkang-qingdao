package com.hotcomm.prevention.service.common;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroup;
import com.hotcomm.prevention.exception.MyException;

public interface TPowerGroupService {

	void insert(TPowerGroup powerGroup) throws MyException;

	void update(TPowerGroup powerGroup) throws MyException;

	void delete(Integer id) throws MyException;
	
	TPowerGroup selectByUserid(Integer userid)throws MyException;
	
	List<TPowerGroup> selectAllRoles(Integer userid)throws MyException;
}
