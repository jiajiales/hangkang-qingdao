package com.hot.manage.service.item;

import java.util.List;

import com.hot.manage.entity.ModuleItemNode;
import com.hot.manage.entity.item.TUserDgroupRelation;
import com.hot.manage.entity.item.UserItemParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;

public interface TUserDgroupRelationService
		extends CommonCrudService<TUserDgroupRelation, TUserDgroupRelation, Integer> {

	TUserDgroupRelation selectObjectByParams(TUserDgroupRelation params) throws MyException;

	Integer insertObject(TUserDgroupRelation relation) throws MyException;

	Integer updateBatch(UserItemParam param) throws MyException;
	
	List<ModuleItemNode> ModuleItemNode(Integer userid)throws MyException;
	
	void userItemRelation(UserItemParam param)throws MyException;

}
