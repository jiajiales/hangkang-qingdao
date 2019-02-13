package com.hotcomm.prevention.service.common;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.params.UserItemParam;
import com.hotcomm.prevention.bean.mysql.common.vo.ModuleItemNode;
import com.hotcomm.prevention.exception.MyException;

public interface TUserDgroupRelationService{
	
	List<ModuleItemNode> ModuleItemNode(Integer userid)throws MyException;
	
	void userItemRelation(UserItemParam param)throws MyException;

}
