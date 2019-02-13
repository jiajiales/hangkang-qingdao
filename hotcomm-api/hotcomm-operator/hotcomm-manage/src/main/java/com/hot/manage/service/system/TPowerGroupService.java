package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.system.TPowerGroup;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;

public interface TPowerGroupService extends CommonCrudService<TPowerGroup, TPowerGroup, Integer> {

	Integer insertRole(TPowerGroup powerGroup) throws MyException;

	List<TPowerGroup> selectAllRoleByUserId(Integer userid) throws MyException;
	
	TPowerGroup selectByUserId(Integer userid) throws MyException;
}
