package com.hot.manage.service.mc;

import java.util.List;

import com.hot.manage.entity.GroupParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.mc.ItemData;
import com.hot.manage.exception.MyException;

public interface McGroupService {

	Integer insert(Integer userid, GroupParam params, String pics) throws MyException;

	Integer update(GroupParam params, String pics) throws MyException;

	Integer del(Integer groupid, Integer moduleid, Integer userid) throws MyException;

	List<TDeviceGroupVo> selectMcGroupInfo(Integer userid, Integer moduleid) throws MyException;

	PageInfo<TDeviceGroupVo> selectMcGroupPage(ItemParam param) throws MyException;

	Integer selectMcDevNum(Integer userid, Integer moduleid) throws MyException;

	Integer selectMcDevNumByGroupId(Integer groupid, Integer moduleid) throws MyException;

	List<ItemData> selectMcItemData(Integer userid, Integer moduleid) throws MyException;

	TDeviceGroupVo selectMcGroup(Integer groupid, Integer moduleid) throws MyException;

}
