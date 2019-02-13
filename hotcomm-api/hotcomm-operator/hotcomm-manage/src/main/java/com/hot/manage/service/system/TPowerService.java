package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.MenuCategoryNode;
import com.hot.manage.entity.system.TPower;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;

public interface TPowerService extends CommonCrudService<TPower, TPower, Integer> {

	Integer insertOne(TPower power) throws MyException;

	List<MenuCategoryNode> selectAll(Integer userid) throws MyException;

	List<TPower> selectAllPower(Integer userid) throws MyException;

	TPower selectInfo(Integer id);

	TPower selectOneById(Integer userid) throws MyException;
	
	List<TPower> selectByFatherid(Integer fatherid)throws MyException;

}
