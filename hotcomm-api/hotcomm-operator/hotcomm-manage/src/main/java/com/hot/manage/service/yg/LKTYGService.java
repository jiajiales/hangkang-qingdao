package com.hot.manage.service.yg;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.yg.LKTDeviceAll;
import com.hot.manage.entity.yg.LKTEstablishygAlarmAndEvent;
import com.hot.manage.entity.yg.LKTEstablishygDevice;
import com.hot.manage.entity.yg.LKTInstructionsAll;
import com.hot.manage.entity.yg.LKTMyproject;
import com.hot.manage.entity.yg.LKTPatrols;
import com.hot.manage.entity.yg.LKTPatrolsperson;
import com.hot.manage.entity.yg.LKTSelctDevOnid;
import com.hot.manage.entity.yg.LKTSelectAllevent;
import com.hot.manage.entity.yg.LKTSelectGroupWorkFather;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.entity.yg.LKTSelectUserApp;
import com.hot.manage.entity.yg.LKTSelectWork;
import com.hot.manage.entity.yg.LKTSelectdevicenum;
import com.hot.manage.entity.yg.LKTSignDeviceListApp;
import com.hot.manage.entity.yg.LKTSignGroupList;
import com.hot.manage.entity.yg.LKTSignHistory;
import com.hot.manage.entity.yg.LKTSignList;
import com.hot.manage.entity.yg.LKTSignListApp;
import com.hot.manage.entity.yg.LKTTureWork;
import com.hot.manage.entity.yg.LKTWorkDetailsApp;
import com.hot.manage.entity.yg.LKTWorkHandleApp;
import com.hot.manage.entity.yg.LKTWorkTobetreatedApp;
import com.hot.manage.entity.yg.LKTWorkUntreated;
import com.hot.manage.entity.yg.item.LKTNewWorkVaule;
import com.hot.manage.entity.yg.item.LKTPatrolscondition;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkcondition;
import com.hot.manage.entity.yg.item.LKTSignDeviceListAppVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceListUserVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceVaule;
import com.hot.manage.entity.yg.item.LKTSignHistorycondition;
import com.hot.manage.entity.yg.item.LKTSignListUpdateVaule;
import com.hot.manage.entity.yg.item.LKTSignListcondition;
import com.hot.manage.entity.yg.item.LKTWorkGbAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkRepairAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkReplaceAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkUntreatedcondition;
import com.hot.manage.exception.MyException;

/**
 * @author Lktao
 *
 */
/**
 * @author Lktao
 *
 */
public interface LKTYGService {

	/**
	 * 我的项目数据and地图项目xy，工单数
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 */
	List<LKTMyproject> LkTMyproject(Integer moduleid, Integer userid) throws MyException;

	/**
	 * 地图设备总数查询
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 */
	LKTSelectdevicenum LKTSelectdevicenum(Integer moduleid, Integer userid) throws MyException;

	/**
	 * 点击地图项目组查询组下工单列表
	 * 
	 * @param lktSelectGroupWorkcondition
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTSelectGroupWorkFather> LKTSelectGroupWork(LKTSelectGroupWorkcondition lktSelectGroupWorkcondition)
			throws MyException;

	/**
	 * 查询未处理报警与未处理事件自动带入设备
	 * 
	 * @return
	 * @throws MyException
	 */
	List<LKTEstablishygAlarmAndEvent> LKTEstablishygAlarmAndEvent() throws MyException;

	/**
	 * 根据id查询未处理报警与未处理事件自动带入设备
	 * 
	 * @param type
	 * @param id
	 * @return
	 * @throws MyException
	 */
	List<LKTEstablishygAlarmAndEvent> LKTSelectAlarmAndEvent(Integer type, String id) throws MyException;

	/**
	 * 手动添加设备列表查询
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<LKTEstablishygDevice> LKTEstablishygDevice(Integer moduleid, Integer userid) throws MyException;

	/**
	 * 根据创建工单用户id查询其组下可分配任务的处理人列表
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	List<LKTSelctDevOnid> LKTSelctDevOnid(List<Integer> id) throws MyException;

	/**
	 * 根据创建工单用户id查询其组下可分配任务的处理人列表
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<LKTSelectHandle> LKTSelectHandle(Integer userid) throws MyException;

	/**
	 * 创建工单
	 * 
	 * @param lktNewWorkVaule
	 * @return
	 */
	LKTCode LKTNewWork(LKTNewWorkVaule lktNewWorkVaule);

	/**
	 * 工单内容查询
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	LKTSelectWork LKTSelectWork(Integer workid) throws MyException;

	/**
	 * 工单关联事件
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	List<LKTSelectAllevent> LKTSelectAllevent(Integer workid) throws MyException;

	/**
	 * 工单关联设备
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	List<LKTDeviceAll> LKTDeviceAll(Integer workid) throws MyException;

	/**
	 * 工单关联工作指示
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	List<LKTInstructionsAll> LKTInstructionsAll(Integer workid) throws MyException;

	/**
	 * 查询已处理或挂起工单
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	List<LKTTureWork> LKTTureWork(Integer workid) throws MyException;

	/**
	 * 删除工单
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTDeleteWork(Integer workid) throws MyException;

	/**
	 * 审核工单
	 * 
	 * @param workid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTWorkExamine(Integer userid, Integer workid, Integer audit, Integer pdid) throws MyException;

	/**
	 * 巡检人员列表
	 * 
	 * @param startTime
	 * @param endTime
	 * @param userNum
	 * @param contacts
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTPatrols> LKTPatrols(LKTPatrolscondition lktPatrolscondition) throws MyException;

	/**
	 * 巡查人员冻结
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTPatrolsFrozen(Integer userid, Integer isenable) throws MyException;

	/**
	 * 巡检人员删除
	 * 
	 * @param patrolsid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTPatrolsDelete(Integer patrolsid) throws MyException;

	/**
	 * 巡检人员添加
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTPatrolsInsert(Integer adduserid) throws MyException;

	/**
	 * 查询可添加为巡检人员的用户列表
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<LKTPatrolsperson> LKTPatrolsperson(Integer userid) throws MyException;

	/**
	 * 查询可更换的巡检人员列表
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<LKTPatrolsperson> LKTPatrolsuser(Integer userid) throws MyException;

	/**
	 * 更换巡检人员
	 * 
	 * @param patrolsided
	 * @param patrolsid
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTPatrolChange(Integer patrolsided, Integer patrolsid) throws MyException;

	/**
	 * 摇一摇签到列表
	 * 
	 * @param lktSignListcondition
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTSignList> LKTSignList(LKTSignListcondition lktSignListcondition) throws MyException;

	/**
	 * 签到历史记录
	 * 
	 * @param lktSignHistorycondition
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTSignHistory> LKTSignHistory(LKTSignHistorycondition lktSignHistorycondition) throws MyException;

	/**
	 * 摇一摇签到列表数据删除
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignListDelete(Integer id) throws MyException;

	/**
	 * 添加签到设备可选的关联项目
	 * 
	 * @return
	 * @throws MyException
	 */
	List<LKTSignGroupList> LKTSignGroupList() throws MyException;

	/**
	 * 添加签到设备
	 * 
	 * @param lktSignDeviceVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignDeviceUser(LKTSignDeviceVaule lktSignDeviceVaule) throws MyException;

	/**
	 * 修改签到列表数据
	 * 
	 * @param lktSignListUpdateVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignListUpdateUser(LKTSignListUpdateVaule lktSignListUpdateVaule) throws MyException;

	/**
	 * 设备签到列表
	 * 
	 * @param lktSignListcondition
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTSignList> LKTSignDeviceList(LKTSignListcondition lktSignListcondition) throws MyException;

	/**
	 * 设备签到列表数据删除
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignDeviceDelete(Integer id) throws MyException;

	/**
	 * 设备签到历史记录
	 * 
	 * @param lktSignHistorycondition
	 * @return
	 * @throws MyException
	 */
	PageInfo<LKTSignHistory> LKTSignDeviceHistory(LKTSignHistorycondition lktSignHistorycondition) throws MyException;

	/**
	 * 分配设备巡检人员
	 * 
	 * @param lktSignDeviceListUserVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignDeviceListUser(LKTSignDeviceListUserVaule lktSignDeviceListUserVaule) throws MyException;

	/**
	 * APP巡检人员设备签到列表
	 * 
	 * @param lktSignDeviceListAppVaule
	 * @return
	 * @throws MyException
	 */
	List<LKTSignDeviceListApp> LKTSignDeviceListApp(LKTSignDeviceListAppVaule lktSignDeviceListAppVaule)
			throws MyException;

	/**
	 * 根据userid查询用户工号姓名APP
	 * 
	 * @param userid
	 * @return
	 */
	LKTSelectUserApp LKTSelectUserApp(Integer userid) throws MyException;

	/**
	 * App巡检人员设备签到
	 * 
	 * @param userid
	 * @param id
	 * @param type
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTSignDeviceApp(Integer userid, Integer id, Integer state, Integer type) throws MyException;

	/**
	 * App巡检-摇一摇查询巡检设备
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<LKTSignListApp> LKTSignListApp(Integer userid) throws MyException;

	/**
	 * App待处理工单
	 * 
	 * @param lktWorkUntreatedcondition
	 * @return
	 * @throws MyException
	 */
	List<LKTWorkUntreated> LKTWorkUntreated(LKTWorkUntreatedcondition lktWorkUntreatedcondition) throws MyException;

	/**
	 * App工单详情
	 * 
	 * @param woid
	 * @return
	 * @throws MyException
	 */
	LKTWorkDetailsApp LKTWorkDetailsApp(Integer woid) throws MyException;

	/**
	 * App工单处理-设备维修
	 * 
	 * @param lktSignDeviceListAppVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTWorkRepairApp(LKTWorkRepairAppVaule lktWorkRepairAppVaule) throws MyException;

	/**
	 * App工单处理-设备挂起
	 * 
	 * @param lktWorkGbAppVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTWorkGbApp(LKTWorkGbAppVaule lktWorkGbAppVaule) throws MyException;

	/**
	 * App工单详情-已处理或挂起
	 * 
	 * @param woid
	 * @return
	 * @throws MyException
	 */
	LKTWorkHandleApp LKTWorkHandleApp(Integer woid) throws MyException;

	/**
	 * App工单处理-设备更换
	 * 
	 * @param lktWorkReplaceAppVaule
	 * @return
	 * @throws MyException
	 */
	LKTCode LKTWorkReplaceApp(LKTWorkReplaceAppVaule lktWorkReplaceAppVaule) throws MyException;

	/**
	 * App已处理工单列表
	 * 
	 * @param lktWorkUntreatedcondition
	 * @return
	 * @throws MyException
	 */
	List<LKTWorkTobetreatedApp> LKTWorkTobetreatedApp(LKTWorkUntreatedcondition lktWorkUntreatedcondition)
			throws MyException;

	/**
	 * 催单
	 * 
	 * @param workid
	 * @param assignid
	 * @return
	 */
	LKTCode LKTReminder(Integer userid, Integer workid, Integer assignid) throws MyException;
}
