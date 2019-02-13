package com.hot.manage.service.ywj;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJItemList;
import com.hot.manage.entity.ywj.LKTYWJItemListMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddGroupVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateItemVaule;
import com.hot.manage.exception.MyException;

public interface LKTYWJItemService {

	LKTYWJDevNum LKTYWJDevNum(Integer moduleid, Integer userid) throws MyException;

	PageInfo<LKTYWJItemList> LKTYWJItemList(LKTYWJDevListVaule lktywjDevListVaule) throws MyException;

	List<LKTYWJGroupList> LKTYWJGroupListOnid(Integer userid, Integer id, Integer moduleid) throws MyException;

	Integer LKTYWJDeleteItem(Integer id) throws MyException;

	List<LKTYWJItemListMap> LKTYWJItemListMap(Integer moduleid, Integer userid) throws MyException;

	Integer LKTYWJAddGroup(LKTYWJAddGroupVaule lktywjAddGroupVaule) throws MyException;
	
	Integer LKTYWJUpdateItem(LKTYWJUpdateItemVaule lktywjUpdateItemVaule) throws MyException;
}
