package com.hot.manage.db.dc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.dc.param.DCGroupParam;
import com.hot.manage.entity.dc.vo.DCGroupAddress;
import com.hot.manage.entity.dc.vo.DCGroupList;
import com.hot.manage.entity.dc.vo.DCMyGroupList;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface DCGroupMapper extends Mapper<DCGroupParam> {

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
	Page<DCGroupList> selectGroupList(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("message") String message, @Param("userid") Integer userid) throws MyException;

	/**
	 * 终端设备总数
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	Integer selectAllDevice(Integer userid) throws MyException;

	/**
	 * 查询我的项目设备情况
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<DCMyGroupList> selectMygroupList(@Param("userid") Integer userid) throws MyException;

	/**
	 * 查询我的项目坐标地址
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<DCGroupAddress> selectGroupAddress(@Param("userid") Integer userid) throws MyException;

	/**
	 * 当前用户可选责任人
	 * 
	 * @param userid
	 *            当前用户id
	 * @return
	 * @throws MyException
	 */
	List<LKTSelectHandle> SelectManager(@Param("userid") Integer userid) throws MyException;

	/**
	 * 判断楼层是否绑定设备
	 * 
	 * @param picid
	 *            图片id
	 * @param moduleid	模块id
	 * @return
	 * @throws MyException
	 */
	Integer selectPictoDevice(@Param("picid") Integer picid,@Param("moduleid") Integer moduleid) throws MyException;
}
