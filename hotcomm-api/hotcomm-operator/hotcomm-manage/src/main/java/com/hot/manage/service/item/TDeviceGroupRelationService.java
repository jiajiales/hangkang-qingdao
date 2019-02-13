package com.hot.manage.service.item;

import java.util.List;

import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.exception.MyException;

public interface TDeviceGroupRelationService {

	Integer updateById(TDeviceGroupRelation relation) throws MyException;
	
	Integer updateByDevIdAndModuleId(TDeviceGroupRelation relation)throws MyException;
	
	Integer insertDev(TDeviceGroupRelation relation) throws MyException;

	Integer insertDevBacht(List<TDeviceGroupRelation> relations) throws MyException;

	List<TDeviceGroupRelation> selectListById(Integer id) throws MyException;

	TDeviceGroupRelation selectRelation(Integer deviceid, Integer moduleid) throws MyException;
	
	List<TDeviceGroupRelation> selectListByExample(Integer groupid, Integer moduleid) throws MyException;
}
