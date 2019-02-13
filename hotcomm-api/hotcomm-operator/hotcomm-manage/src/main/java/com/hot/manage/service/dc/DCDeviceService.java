package com.hot.manage.service.dc;

import java.util.List;

import com.github.pagehelper.Page;
import com.hot.manage.entity.dc.param.DCChangeUser;
import com.hot.manage.entity.dc.param.DCDeviceParam;
import com.hot.manage.entity.dc.param.VideoList;
import com.hot.manage.entity.dc.vo.DCDeviceAllGroup;
import com.hot.manage.entity.dc.vo.DCDeviceFloor;
import com.hot.manage.entity.dc.vo.DCDeviceIMG;
import com.hot.manage.entity.dc.vo.DCDeviceList;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.exception.MyException;

public interface DCDeviceService {

	/**
	 * 项目下的设备总数
	 * 
	 * @param groupid
	 *            项目id
	 * @return
	 * @throws MyException
	 */
	Integer selectDevAllByGroup(Integer groupid) throws MyException;

	/**
	 * 项目下各个楼层的设备分布
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	List<DCDeviceFloor> selectDeviceToFloor(Integer groupid) throws MyException;

	/**
	 * 该楼层所有设备的位置
	 * 
	 * @param imgId
	 *            楼层id
	 * @return
	 * @throws MyException
	 */
	List<DCDeviceIMG> selectDevicImg(Integer imgId) throws MyException;

	/**
	 * 设备查询(可根据时间,内容,电量情况筛选查询结果)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param message
	 *            筛选内容
	 * @param Battery
	 *            电量
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	Page<DCDeviceList> selectList(String startTime, String endTime, String message, Integer Battery, Integer userid,Integer groupid)
			throws MyException;

	/**
	 * 根据id查询设备
	 * 
	 * @param id
	 *            设备id
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */
	DCDeviceList selectDeviceById(Integer userid, Integer id) throws MyException;

	/**
	 * 根据id删除设备
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	Integer deleteDeviceById(Integer id, Integer userid) throws MyException;

	/**
	 * 当前用户所有项目
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	List<DCDeviceAllGroup> DCDeviceAllGroup(Integer userid) throws MyException;

	/**
	 * 根据内容修改设备信息
	 * 
	 * @param dcDeviceParam
	 * @return
	 * @throws MyException
	 */
	Integer updateDeviceById(DCDeviceParam dcDeviceParam, Integer userid, TDeviceGroupRelation tdeviceGroupRelation,
			TDevItemPic tdip,String videoid) throws MyException;

	/**
	 * 添加设备
	 * 
	 * @param dcDeviceParam
	 *            设备
	 * @param tdeviceGroupRelation
	 *            设备与组的关联表
	 * @return
	 * @throws MyException
	 */
	Integer insertDevice(Integer userid, DCDeviceParam dcDeviceParam, TDeviceGroupRelation tdeviceGroupRelation,
			TDevItemPic tdip,VideoList videoList) throws MyException;
	/**
	 * 更换设备
	 * 
	 * @param dcDeviceParam
	 *            设备
	 * @return
	 * @throws MyException
	 */
	Integer changeDevice(DCDeviceParam dcDeviceParam)throws MyException;
	/**
	 * 修改责任人
	 * 
	 * @param DCChangeUser
	 *            设备idList 、责任人id等
	 * @return
	 * @throws MyException
	 */
	Integer changeDevOwn(DCChangeUser dCChangeUser);
	/**
	 * 加入设备签到列表
	 * 
	 * @param moduleid     
	 *            模块id  
	 *  @param   devid  
	 *            设备id集合
	 * @return
	 * @throws MyException
	 */
	Integer LKTAddSignDevList(Integer moduleid, String devid,Integer patrolid);

}
