package com.hot.manage.db.dc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.dc.param.DCChangeUser;
import com.hot.manage.entity.dc.param.DCDeviceParam;
import com.hot.manage.entity.dc.vo.DCDeviceAllGroup;
import com.hot.manage.entity.dc.vo.DCDeviceFloor;
import com.hot.manage.entity.dc.vo.DCDeviceIMG;
import com.hot.manage.entity.dc.vo.DCDeviceList;
import com.hot.manage.entity.dc.vo.DCGroupIMG;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface DCDeviceMapper extends Mapper<DCDeviceParam> {

	/**
	 * 当前项目下设备总数
	 * 
	 * @param groupid
	 *            项目id
	 * @return
	 * @throws MyException
	 */
	Integer selectDevAllByGroup(@Param("groupid") Integer groupid) throws MyException;

	/**
	 * 该项目下的所有楼层
	 * 
	 * @param groupid
	 *            组id
	 * @return
	 * @throws MyException
	 */
	List<DCDeviceFloor> selectDeviceToFloor(@Param("groupid") Integer groupid) throws MyException;

	/**
	 * 该楼层所有设备的位置
	 * 
	 * @param imgId
	 *            图片id
	 * @return
	 * @throws MyException
	 */
	List<DCDeviceIMG> selectDeviceIMG(@Param("imgId") Integer imgId) throws MyException;

	/**
	 * 设备查询(可根据时间,内容,电量情况筛选查询结果)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param message
	 *            搜索内容
	 * @param Battery
	 *            电量
	 * @param groupid
	 *            项目组id
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	Page<DCDeviceList> selectList(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("message") String message, @Param("Battery") Integer Battery, @Param("userid") Integer userid,@Param("groupid") Integer groupid)
			throws MyException;

	/**
	 * 查询该项目的所有图片路径
	 * 
	 * @param moduleid
	 *            模块id
	 * @param groupid
	 *            组id
	 * @return
	 * @throws MyException
	 */
	List<DCGroupIMG> selectGroupPic(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid)
			throws MyException;

	/**
	 * 修改设备位置
	 * 
	 * @param devid
	 *            设备id
	 * @param picId
	 *            图片id
	 * @param imgX
	 *            imgX
	 * @param imgY
	 *            imgY
	 * @return
	 * @throws MyException
	 */
	Integer updateDeviceXY(@Param("devid") Integer devid, @Param("picId") Integer picId, @Param("imgX") String imgX,
			@Param("imgY") String imgY) throws MyException;

	/**
	 * 按id查询
	 * 
	 * @param userid
	 *            用户id
	 * @param id
	 *            设备id
	 * @return
	 * @throws MyException
	 */
	DCDeviceList selectDeviceById(@Param("userid") Integer userid, @Param("id") Integer id) throws MyException;

	/**
	 * 修改设备项目关联表
	 * 
	 * @param groupid
	 *            组id
	 * @param devid
	 *            设备id
	 * @return
	 * @throws MyException
	 */
	Integer updateDeviceREitem(@Param("groupid") Integer groupid, @Param("devid") Integer devid) throws MyException;

	/**
	 * 查询该模块所有项目
	 * 
	 * @param moduleid
	 *            模块id
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	List<DCDeviceAllGroup> selectAllGroup(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid)
			throws MyException;

	/**
	 * 更换设备
	 * 
	 * @param moduleid
	 *            模块id
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	Integer changeDevice(DCDeviceParam dcDeviceParam);

	/**
	 * 更换责任人
	 * 
	 * @param dCChangeUser
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer changeDevOwn(@Param("dCChangeUser") DCChangeUser dCChangeUser);

	/**
	 * 根据mac查找设备id
	 * 
	 * @param dCChangeUser
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer LKTDCDevIdByMac(@Param("mac") String mac);
	
}