package com.hot.manage.service.dc;

import java.util.List;

import com.github.pagehelper.Page;
import com.hot.manage.entity.dc.param.DCGroupByIdParam;
import com.hot.manage.entity.dc.vo.DCGroupAddress;
import com.hot.manage.entity.dc.vo.DCGroupList;
import com.hot.manage.entity.dc.vo.DCMyGroupList;
import com.hot.manage.entity.dc.vo.GroupByOne;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.exception.MyException;

public interface DCGroupService {

	/**
	 * 终端设备总数
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	Integer selectAllDevice(Integer userid) throws MyException;

	/**
	 * 我的项目
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<DCMyGroupList> selectMyGroupList(Integer userid) throws MyException;

	/**
	 * 我的项目坐标地址
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<DCGroupAddress> selectGroupAddress(Integer userid) throws MyException;

	/**
	 * 项目列表(可根据时间,内容筛选)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param message
	 *            搜索内容
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	Page<DCGroupList> selectGroupList(String startTime, String endTime, String message, Integer userid)
			throws MyException;

	/**
	 * 根据id 查询
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	GroupByOne selectById(Integer id) throws MyException;

	/**
	 * 根据内容修改
	 * 
	 * @param dcGroupByIdParam
	 * @return
	 * @throws MyException
	 */
	Integer updateById(DCGroupByIdParam dcGroupByIdParam) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	Integer deleteById(Integer groupid, Integer userid) throws MyException;

	/**
	 * 查询当前用户可选责任人
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<LKTSelectHandle> SelectManager(Integer userid) throws MyException;

	/**
	 * 添加项目组
	 * 
	 * @param deviceGroup
	 * @param tuserDgroupRelation
	 * @param tItemPicList
	 * @return
	 * @throws MyException
	 */
	Integer insertGroup(Integer userid, TDeviceGroup deviceGroup, List<TItemPic> tItemPicList) throws MyException;

	/**
	 * 判断新增的楼层是否重复(*)
	 * 
	 * @param itemid
	 *            项目组id
	 * @param site
	 *            楼层
	 * @return	1:没有重复;0:重复了
	 * @throws MyException
	 */
	Integer ItemPicIsExtsis(Integer itemid, String site) throws MyException;

	/**
	 * 判断楼层是否绑定设备
	 * 
	 * @param picid
	 *            图片id
	 * @return true:没有绑定;false:绑定了
	 * @throws MyException
	 */
	public boolean PicisbindDevice(Integer picid) throws MyException;
}
