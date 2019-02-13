package com.hot.manage.service.item;
import java.util.List;

import com.hot.manage.entity.GroupParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.mc.ItemData;
import com.hot.manage.exception.MyException;


public interface TDeviceGroupService {
	
	Integer insert(Integer userid,GroupParam params,String pics) throws MyException;

	Integer update(GroupParam params,String pics) throws MyException;

	Integer del(Integer groupid, Integer moduleid, Integer userid) throws MyException;

	TDeviceGroup selectByName(String name,Integer moduleid) throws MyException;

	PageInfo<TDeviceGroupVo> selectItems(ItemParam params) throws MyException;
	
	List<TDeviceGroupVo> selectAllItems(Integer userid,Integer moduleid)throws MyException;
	
	Integer selectDevNum(Integer userid,Integer moduleid) throws MyException;
	
	Integer selectItemNum(Integer userid,Integer moduleid) throws MyException;
	
	TDeviceGroupVo selectById(Integer groupid,Integer moduleid) throws MyException;
	
	List<TDeviceGroup> selectItemByUserId(Integer userid,Integer moduleid)throws MyException;
	
	PageInfo<ItemData> selectYgItemData(Integer pageNum,Integer pageSize,Integer userid, Integer moduleid)throws MyException;
}
