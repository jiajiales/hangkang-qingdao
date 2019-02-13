package com.hot.manage.service.common.workorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.hot.manage.db.common.AppPush.AppPushMsgMapper;
import com.hot.manage.db.common.workorder.LKTWorkMapper;
import com.hot.manage.entity.common.AppPush.T_hk_apppush;
import com.hot.manage.entity.common.AppPush.T_hk_apppush_msg;
import com.hot.manage.entity.common.workorder.LKTWorkAllAlarm;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteApp;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteListApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetailedApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetails;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsAllevent;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsDeviceAll;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsInstructions;
import com.hot.manage.entity.common.workorder.LKTWorkDevList;
import com.hot.manage.entity.common.workorder.LKTWorkDevnum;
import com.hot.manage.entity.common.workorder.LKTWorkListFather;
import com.hot.manage.entity.common.workorder.LKTWorkMyproject;
import com.hot.manage.entity.common.workorder.LKTWorkTure;
import com.hot.manage.entity.common.workorder.LKTWorkUntreatedApp;
import com.hot.manage.entity.common.workorder.SelDevmsg;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkListcondition;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkMaintenanceAppVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkNewVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkUpdateAppVaule;
import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.SocketService;
import com.hot.manage.service.common.AppPush.AppPushService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.JSONUtil;
import com.hot.manage.utils.PushUtil;

@Transactional
@Service
public class LKTWorkServiceImpl implements LKTWorkService {

	@Autowired
	private LKTWorkMapper lktworkmapper;

	@Autowired
	private SocketService sock;

	@Autowired
	AppPushService appPushService;

	@Autowired
	AppPushMsgMapper appPushMsgMapper;

	private TMessageLog message = new TMessageLog();

	// ("查询用户下的设备总数")
	@Override
	public LKTWorkDevnum LKTWorkDevnum(Integer moduleid, Integer userid) {
		return lktworkmapper.LKTWorkDevnum(moduleid, userid);
	}

	// ("查询地图项目信息，工单数")
	@Override
	public List<LKTWorkMyproject> LKTWorkMyproject(Integer moduleid, Integer userid) {
		return lktworkmapper.LKTWorkMyproject(moduleid, userid);
	}

	// ("工单列表（可按项目组查）")
	@Override
	public Page<LKTWorkListFather> LKTWorkListFather(LKTWorkListcondition lktWorkListcondition) throws MyException {
		Page<LKTWorkListFather> list = lktworkmapper.LKTWorkListFather(lktWorkListcondition);
		return list;
	}

	// ("查询未处理报警与未处理事件自动带入设备")
	@Override
	public List<LKTWorkAllAlarm> LKTWorkAllAlarm(Integer moduleid, Integer userid) throws MyException {
		List<LKTWorkAllAlarm> alarms = lktworkmapper.LKTWorkAllAlarm(moduleid, userid, null, null);
		List<LKTWorkAllAlarm> evens = lktworkmapper.LKTWorkAllEven(moduleid, userid, null, null);
		alarms.addAll(evens);
		return alarms;
	}

	// ("故障设备列表查询")
	@Override
	public List<LKTWorkDevList> LKTWorkDevList(Integer moduleid, Integer userid) throws MyException {
		List<LKTWorkDevList> devLists = lktworkmapper.LKTWorkDevList(moduleid, userid);
		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < devLists.size(); i++) {
			id.add(devLists.get(i).getId());
		}
		if (id != null && id.size() > 0) {
			List<LKTWorkAllAlarm> alarms = lktworkmapper.LKTWorkAllAlarm(moduleid, userid, id, null);
			List<LKTWorkAllAlarm> events = lktworkmapper.LKTWorkAllEven(moduleid, userid, id, null);
			for (int i = 0; i < devLists.size(); i++) {
				// 取出第i条父数据
				LKTWorkDevList father = devLists.get(i);
				// 储存子数据
				List<LKTWorkAllAlarm> alarmson = new ArrayList<>();
				for (int j = 0; j < alarms.size(); j++) {
					if (devLists.get(i).getId().toString().equals(alarms.get(j).getDevid().toString())) {
						alarmson.add(alarms.get(j));
					}
				}
				for (int j = 0; j < events.size(); j++) {
					if (devLists.get(i).getId().toString().equals(events.get(j).getDevid().toString())) {
						alarmson.add(events.get(j));
					}
				}
				System.out.println(alarmson);
				if (alarmson != null && alarmson.size() > 0) {
					father.setSon(alarmson);
					devLists.set(i, father);
				} else {
					devLists.remove(i);
					i--;
				}
			}
		}
		return devLists;
	}

	// ("创建工单")
	@Override
	public Integer LKTWorkNew(LKTWorkNewVaule lktWorkNewVaule) throws MyException {
		Integer code = 0;
		String[] devid = null;
		if (lktWorkNewVaule.getDev_id() != null) {
			devid = lktWorkNewVaule.getDev_id().split(",");
			// 分割传入的报警id
			String[] alarmid = null;
			List<Integer> aeid = null;
			List<LKTWorkAllAlarm> alarmlist = null;
			if (lktWorkNewVaule.getAlarm_id() != null && !lktWorkNewVaule.getAlarm_id().equals("")) {
				alarmid = lktWorkNewVaule.getAlarm_id().split(",");
				aeid = new ArrayList<Integer>();
				for (int j = 0; j < alarmid.length; j++) {
					aeid.add(Integer.parseInt(alarmid[j]));
				}
				// 查询传入报警数据信息
				alarmlist = lktworkmapper.LKTWorkAllAlarm(lktWorkNewVaule.getModuleid(), lktWorkNewVaule.getUserid(),
						null, aeid);
			}
			String[] eventid = null;
			List<Integer> eid = null;
			List<LKTWorkAllAlarm> eventlist = null;
			if (lktWorkNewVaule.getEvent_id() != null && !lktWorkNewVaule.getEvent_id().equals("")) {
				// 分割传入的事件id
				eventid = lktWorkNewVaule.getEvent_id().split(",");
				eid = new ArrayList<Integer>();
				for (int j = 0; j < eventid.length; j++) {
					eid.add(Integer.parseInt(eventid[j]));
				}
				// 查询传入的事件id信息
				eventlist = lktworkmapper.LKTWorkAllEven(lktWorkNewVaule.getModuleid(), lktWorkNewVaule.getUserid(),
						null, eid);
			}
			// 添加工单数据，工单与设备关联数据
			for (int i = 0; i < devid.length; i++) {
				// 添加工单
				code = lktworkmapper.LKTWorkNew(lktWorkNewVaule);
				// 写入工单编号
				lktWorkNewVaule.setCode("HK_00" + lktWorkNewVaule.getId().toString());
				code = lktworkmapper.LKTWorkNewUpdate("HK_00" + lktWorkNewVaule.getId().toString(), null,
						lktWorkNewVaule.getId());
				if (devid != null) {
					// 添加工单与设备的关联
					lktworkmapper.LKTWorkNewDev(lktWorkNewVaule.getId(), Integer.parseInt(devid[i]),
							lktWorkNewVaule.getModuleid());
				}
				// 处理工单与报警关联
				if (alarmlist != null && alarmlist.size() > 0) {
					for (int k = 0; k < alarmid.length; k++) {
						// 根据设备id判断是否这条数据为改设备
						if (devid[i].equals(alarmlist.get(k).getDevid().toString())) {
							// 添加报警与父工单关联
							lktworkmapper.LKTWorkNewAE(lktWorkNewVaule.getId(), alarmlist.get(k).getId(), 2,
									lktWorkNewVaule.getModuleid());
							// 修改t_dev_alarm的handler字段为assignid参数,
							// handlestate
							// 字段改为1
							lktworkmapper.LKTWorkNewAlarmUp(lktWorkNewVaule.getAssignid(), alarmlist.get(k).getId(),
									lktWorkNewVaule.getModuleid());
						}
					}
				}
				// 处理工单与事件关联
				if (eventlist != null && eventlist.size() > 0) {
					for (int f = 0; f < eventlist.size(); f++) {
						if (devid[i].equals(eventlist.get(f).getDevid().toString())) {
							// 添加事件与工单关联
							lktworkmapper.LKTWorkNewAE(lktWorkNewVaule.getId(), eventlist.get(f).getId(), 1,
									lktWorkNewVaule.getModuleid());
							// 更改事件上报状态state为1处理中
							lktworkmapper.LKTWorkNewEventUp(eventlist.get(f).getId(), lktWorkNewVaule.getModuleid());
						}
					}
				}
			}
		} else {// 添加空工单
				// 添加工单
			code = lktworkmapper.LKTWorkNew(lktWorkNewVaule);
			code = lktworkmapper.LKTWorkNewUpdate("HK_00" + lktWorkNewVaule.getId().toString(), null,
					lktWorkNewVaule.getId());
		}
		String str = "你有新的工单，请尽快前去处理。工单编号为：" + lktWorkNewVaule.getCode();
		message.setUserid(lktWorkNewVaule.getUserid());
		message.setMessage(str);
		message.setReceiverid(lktWorkNewVaule.getAssignid().toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(lktWorkNewVaule.getAssignid());
		if (t_hk_apppush != null) {
			LKTWorkNewVaule l = new LKTWorkNewVaule();
			l.setId(lktWorkNewVaule.getId());
			l.setModuleid(lktWorkNewVaule.getModuleid());
			code = PushUtil.sendAllsetNotification("工单消息", str, JSONUtil.toJson(ApiResult.resultInfo("2", "工单", l)),
					t_hk_apppush.getRegid(), 86400);
			if (code == 201) {// 201推送失败
				// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
				T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
				t_hk_apppush_msg.setTitle("工单消息");
				t_hk_apppush_msg.setContent(str);
				t_hk_apppush_msg.setMessage(JSONUtil.toJson(ApiResult.resultInfo("2", "工单", l)));
				t_hk_apppush_msg.setRegids(t_hk_apppush.getRegid());
				t_hk_apppush_msg.setTimeToLive(String.valueOf(86400));
				t_hk_apppush_msg.setUserid(t_hk_apppush.getUserid());
				// 插入推送消息数据库
				appPushMsgMapper.insertSelective(t_hk_apppush_msg);
			}
		}
		return code;
	}

	// 根据id查询未处理报警与未处理事件自动带入设备
	@Override
	public List<LKTWorkAllAlarm> LKTWorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type)
			throws MyException {
		String[] AEid = id.split(",");
		List<Integer> aeid = new ArrayList<Integer>();
		for (int i = 0; i < AEid.length; i++) {
			aeid.add(Integer.parseInt(AEid[i]));
		}
		if (type == 2) {
			return lktworkmapper.LKTWorkAllAlarm(moduleid, userid, null, aeid);
		} else if (type == 1) {
			return lktworkmapper.LKTWorkAllEven(moduleid, userid, null, aeid);
		}
		return null;
	}

	// 工单内容查询
	@Override
	public LKTWorkDetails LKTWorkDetails(Integer woid, Integer userid, Integer moduleid) {
		LKTWorkDetails lktWorkDetails = lktworkmapper.LKTWorkDetails(woid, userid, moduleid);
		// 直接查询工单关联报警、事件记录
		List<LKTWorkDetailsAllevent> alarms = lktworkmapper.LKTWorkDetailsAllAlarmonf(woid, userid, moduleid);
		List<LKTWorkDetailsAllevent> events = lktworkmapper.LKTWorkDetailsAllEvenonf(woid, userid, moduleid);
		alarms.addAll(events);
		lktWorkDetails.setAllevents(alarms);
		// 查询工单关联设备
		LKTWorkDetailsDeviceAll devalls = lktworkmapper.LKTWorkDetailsDeviceAll(woid, moduleid, userid);
		lktWorkDetails.setDevall(devalls);
		// 查询工单关联指示
		List<LKTWorkDetailsInstructions> instructions = lktworkmapper.LKTWorkDetailsInstructions(woid, moduleid);
		lktWorkDetails.setInstructions(instructions);
		return lktWorkDetails;
	}

	// 查询已处理或挂起工单
	@Override
	public LKTWorkTure LKTWorkTure(Integer moduleid, Integer woid) {
		return lktworkmapper.LKTWorkTure(moduleid, woid);
	}

	// 删除工单
	@Override
	public Integer LKTWorkDelete(Integer moduleid, Integer userid, Integer woid) {
		Integer code = 0;
		// 删除工单
		code = lktworkmapper.LKTWorkDeletef(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单与报警/事件关联
		code = lktworkmapper.LKTWorkDeleteAE(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单下的资源文件
		code = lktworkmapper.LKTWorkDeleteResources(woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单与设备关联
		code = lktworkmapper.LKTWorkDeleteDev(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		return code;
	}

	// 审核工单
	@Override
	public Integer LKTWorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid,
			Integer assignid) throws MyException {
		Integer code = 0;
		Integer state = 0;
		LKTWorkDetails lktWorkDetails = LKTWorkDetails(woid, userid, moduleid);
		String str = "你的工单：" + lktWorkDetails.getCode() + "，审核未通过";
		// 直接查询工单关联报警、事件记录
		List<LKTWorkDetailsAllevent> alarms = lktworkmapper.LKTWorkDetailsAllAlarmonf(woid, userid, moduleid);
		List<LKTWorkDetailsAllevent> events = lktworkmapper.LKTWorkDetailsAllEvenonf(woid, userid, moduleid);
		// 查询工单关联设备
		LKTWorkDetailsDeviceAll devalls = lktworkmapper.LKTWorkDetailsDeviceAll(woid, moduleid, userid);
		if (audit == 1) {
			state = 2;
			str = "你的工单：" + woid.toString() + "，审核已通过";
		}
		// 更改工单数据审核状态
		code = lktworkmapper.LKTWorkExamine(state, audit, woid, moduleid);
		// 更改工单数据对应的维修记录表数据审核状态
		code = lktworkmapper.LKTWorkExamineRepair(remark, audit, woid);
		// 审核不通过,
		if (audit != 1) {
			for (int i = 0; i < alarms.size(); i++) {// 修改工单的报警记录为未处理
				code = lktworkmapper.LKTWorkExamineAlarmUp(alarms.get(i).getId(), moduleid);
			}
			for (int i = 0; i < events.size(); i++) {// 修改工单的事件记录为未处理
				code = lktworkmapper.LKTWorkExamineEventUp(events.get(i).getId(), moduleid);
			}
			if (devalls != null) {
				// 修改设备状态为正常
				code = lktworkmapper.LKTWorkExamineDevUp(devalls.getId(), moduleid);
			}
			// 删除该工单所有资源文件
			lktworkmapper.LKTWorkDeleteResources(woid);
		} else {
			lktworkmapper.LKTWorkExamineDelRs(woid);
		}
		message.setUserid(userid);
		message.setMessage(str);
		message.setReceiverid(assignid.toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(assignid);
		if (t_hk_apppush != null) {
			LKTWorkNewVaule lktWorkNewVaule = new LKTWorkNewVaule();
			lktWorkNewVaule.setId(woid);
			lktWorkNewVaule.setModuleid(moduleid);
			code = PushUtil.sendAllsetNotification("工单消息", str,
					JSONUtil.toJson(ApiResult.resultInfo("2", "工单", lktWorkNewVaule)), t_hk_apppush.getRegid(), 86400);
			if (code == 201) {// 201推送失败
				// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
				T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
				t_hk_apppush_msg.setTitle("工单消息");
				t_hk_apppush_msg.setContent(str);
				t_hk_apppush_msg.setMessage(JSONUtil.toJson(ApiResult.resultInfo("2", "工单", lktWorkNewVaule)));
				t_hk_apppush_msg.setRegids(t_hk_apppush.getRegid());
				t_hk_apppush_msg.setTimeToLive(String.valueOf(86400));
				t_hk_apppush_msg.setUserid(t_hk_apppush.getUserid());
				// 插入推送消息数据库
				appPushMsgMapper.insertSelective(t_hk_apppush_msg);
			}
		}
		return 1;
	}

	// 催促工单
	@Override
	public Integer LKTReminder(Integer userid, Integer woid, Integer assignid, String code) throws MyException {
		String str = "请尽快前去处理工单：" + code;
		message.setUserid(userid);
		message.setMessage(str);
		message.setReceiverid(assignid.toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(assignid);
		if (t_hk_apppush != null) {
			int code1 = PushUtil.sendAllsetNotification("工单消息", str,
					JSONUtil.toJson(ApiResult.resultInfo("2", "工单", lktworkmapper.SelectWo(woid))),
					t_hk_apppush.getRegid(), 86400);
			if (code1 == 201) {// 201推送失败
				// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
				T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
				t_hk_apppush_msg.setTitle("工单消息");
				t_hk_apppush_msg.setContent(str);
				t_hk_apppush_msg
						.setMessage(JSONUtil.toJson(ApiResult.resultInfo("2", "工单", lktworkmapper.SelectWo(woid))));
				t_hk_apppush_msg.setRegids(t_hk_apppush.getRegid());
				t_hk_apppush_msg.setTimeToLive(String.valueOf(86400));
				t_hk_apppush_msg.setUserid(t_hk_apppush.getUserid());
				// 插入推送消息数据库
				appPushMsgMapper.insertSelective(t_hk_apppush_msg);
			}
		}
		return 1;
	}

	// App待处理工单列表
	@Override
	public List<LKTWorkUntreatedApp> LKTWorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode) {
		List<LKTWorkUntreatedApp> list = new ArrayList<>();
		Integer[] moduleid = { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		for (int i = 0; i < moduleid.length; i++) {
			list.addAll(lktworkmapper.LKTWorkUntreatedApp(userid, readtype, dvenumorcode, moduleid[i]));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 数据排序
		int j, k;
		int flag = list.size();// flag来记录最后交换的位置，也就是排序的尾边界
		while (flag > 0) {// 排序未结束标志
			k = flag; // k 来记录遍历的尾边界
			flag = 0;
			for (j = 1; j < k; j++) {
				try {
					if (sdf.parse(list.get(j).getAddtime()).getTime() > sdf.parse(list.get(j - 1).getAddtime())
							.getTime()) {// 前面的数字大于后面的数字就交换
						// 交换a[j-1]和a[j]
						LKTWorkUntreatedApp temp;
						temp = list.get(j - 1);
						list.set(j - 1, list.get(j));
						list.set(j, temp);
						// 表示交换过数据;
						flag = j;// 记录最新的尾边界.
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 排序结束
		return list;
	}

	// App待处理工单详情
	@Override
	public LKTWorkDetailedApp LKTWorkDetailedApp(Integer moduleid, Integer woid, Integer userid) throws MyException {
		LKTWorkDetailedApp lktWorkDetailedApp = lktworkmapper.LKTWorkDetailedApp(moduleid, woid);
		// 查询工单关联报警、事件记录
		List<LKTWorkDetailsAllevent> alarms = lktworkmapper.LKTWorkDetailsAllAlarmonf(woid, userid,
				lktWorkDetailedApp.getModuleid());
		List<LKTWorkDetailsAllevent> events = lktworkmapper.LKTWorkDetailsAllEvenonf(woid, userid,
				lktWorkDetailedApp.getModuleid());
		alarms.addAll(events);
		lktWorkDetailedApp.setSon(alarms);
		// 查询工单关联报警、事件记录关联图片
		String img = null;
		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < alarms.size(); i++) {
			id.add(alarms.get(i).getId());
		}
		LKTWorkDetailedApp alarmimg = null;
		if (id.size() > 0) {
			alarmimg = lktworkmapper.LKTWorkDetailedAppimg(lktWorkDetailedApp.getModuleid(), id, 2);
		}
		id.clear();
		for (int i = 0; i < alarms.size(); i++) {
			id.add(alarms.get(i).getId());
		}
		LKTWorkDetailedApp eventimg = null;
		if (id.size() > 0) {
			eventimg = lktworkmapper.LKTWorkDetailedAppimg(lktWorkDetailedApp.getModuleid(), id, 1);
		}
		if (alarmimg != null && eventimg != null) {
			img = alarmimg.getImg() + "," + eventimg.getImg();
		} else if (alarmimg == null && eventimg != null) {
			img = eventimg.getImg();
		} else if (alarmimg != null && eventimg == null) {
			img = alarmimg.getImg();
		}
		lktWorkDetailedApp.setImg(img);
		return lktWorkDetailedApp;
	}

	// App工单处理-维修
	@Override
	public Integer LKTWorkMaintenanceApp(LKTWorkMaintenanceAppVaule lktWorkMaintenanceAppVaule) {
		Integer code = 0;
		// 直接查询工单关联报警、事件记录
		List<LKTWorkDetailsAllevent> alarms = lktworkmapper.LKTWorkDetailsAllAlarmonf(
				lktWorkMaintenanceAppVaule.getWoid(), lktWorkMaintenanceAppVaule.getUserid(),
				lktWorkMaintenanceAppVaule.getModuleid());
		List<LKTWorkDetailsAllevent> events = lktworkmapper.LKTWorkDetailsAllEvenonf(
				lktWorkMaintenanceAppVaule.getWoid(), lktWorkMaintenanceAppVaule.getUserid(),
				lktWorkMaintenanceAppVaule.getModuleid());
		// 修改工单状态,处理方式,结束时间
		lktWorkMaintenanceAppVaule.setHandleType(1);
		code = lktworkmapper.LKTWorkMaintenanceApp(lktWorkMaintenanceAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 插入图片资源
		if (lktWorkMaintenanceAppVaule.getPictureurl() != null
				&& !lktWorkMaintenanceAppVaule.getPictureurl().equals("")) {
			String[] imglist = lktWorkMaintenanceAppVaule.getPictureurl().split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = lktworkmapper.LKTWorkMaintenanceAppImg(lktWorkMaintenanceAppVaule.getWoid(), 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		if (lktWorkMaintenanceAppVaule.getVoiceurl() != null && !lktWorkMaintenanceAppVaule.getVoiceurl().equals("")) {
			// 插入音频资源
			String[] voicelist = lktWorkMaintenanceAppVaule.getVoiceurl().split(",");
			for (int i = 0; i < voicelist.length; i++) {
				code = lktworkmapper.LKTWorkMaintenanceAppImg(lktWorkMaintenanceAppVaule.getWoid(), 2, voicelist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		// 修改与工单关联的报警为已处理
		for (int i = 0; i < alarms.size(); i++) {
			code = lktworkmapper.LKTWorkMaintenanceAppal(alarms.get(i).getId(),
					lktWorkMaintenanceAppVaule.getModuleid(), null);
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 修改与工单关联的事件为已处理
		for (int i = 0; i < events.size(); i++) {
			code = lktworkmapper.LKTWorkMaintenanceAppev(events.get(i).getId(),
					lktWorkMaintenanceAppVaule.getModuleid());
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 插入维修记录表数据
		code = lktworkmapper.LKTWorkMaintenanceApplog(lktWorkMaintenanceAppVaule.getWoid(),
				lktWorkMaintenanceAppVaule.getDevid(), lktWorkMaintenanceAppVaule.getUserid());
		return code;
	}

	// App工单处理-设备挂起
	@Override
	public Integer LKTWorkHangApp(String problemdesc, Integer woid, String pictureurl) {
		Integer code = 0;
		code = lktworkmapper.LKTWorkHangApp(2, problemdesc, woid);
		// 插入图片资源
		if (pictureurl != null && !pictureurl.equals("")) {
			String[] imglist = pictureurl.split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = lktworkmapper.LKTWorkMaintenanceAppImg(woid, 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		return code;
	}

	// App工单处理-设备更换
	@Override
	public Integer LKTWorkUpdateApp(LKTWorkUpdateAppVaule lktWorkUpdateAppVaule) {
		Integer code = 0;
		// 直接查询工单关联报警、事件记录
		List<LKTWorkDetailsAllevent> alarms = lktworkmapper.LKTWorkDetailsAllAlarmonf(lktWorkUpdateAppVaule.getWoid(),
				lktWorkUpdateAppVaule.getUserid(), lktWorkUpdateAppVaule.getModuleid());
		List<LKTWorkDetailsAllevent> events = lktworkmapper.LKTWorkDetailsAllEvenonf(lktWorkUpdateAppVaule.getWoid(),
				lktWorkUpdateAppVaule.getUserid(), lktWorkUpdateAppVaule.getModuleid());
		lktWorkUpdateAppVaule.setHandleType(3);
		code = lktworkmapper.LKTWorkUpdateApp(lktWorkUpdateAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 插入图片资源
		if (lktWorkUpdateAppVaule.getPictureurl() != null && !lktWorkUpdateAppVaule.getPictureurl().equals("")) {
			String[] imglist = lktWorkUpdateAppVaule.getPictureurl().split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = lktworkmapper.LKTWorkMaintenanceAppImg(lktWorkUpdateAppVaule.getWoid(), 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		if (lktWorkUpdateAppVaule.getVoiceurl() != null && !lktWorkUpdateAppVaule.getVoiceurl().equals("")) {
			// 插入音频资源
			String[] voicelist = lktWorkUpdateAppVaule.getVoiceurl().split(",");
			for (int i = 0; i < voicelist.length; i++) {
				code = lktworkmapper.LKTWorkMaintenanceAppImg(lktWorkUpdateAppVaule.getWoid(), 2, voicelist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		// 新设备id
		Integer devid = null;
		SelDevmsg woci = lktworkmapper.SelWoci(lktWorkUpdateAppVaule.getWoid());
		if (woci.getNewdevnum() == null) {
			// 第一次更换
			// 查出设备，新增一个旧设备
			SelDevmsg selDevmsg = lktworkmapper.SelDevmsg(lktWorkUpdateAppVaule.getDevid(),
					lktWorkUpdateAppVaule.getModuleid());
			selDevmsg.setModuleid(lktWorkUpdateAppVaule.getModuleid());
			lktworkmapper.AddDevmsg(selDevmsg);
			devid = selDevmsg.getId();
			// 查出设备与项目关联，新增旧设备与项目关联
			SelDevmsg selDevmsggroup = lktworkmapper.SelDevmsgGroup(lktWorkUpdateAppVaule.getDevid(),
					lktWorkUpdateAppVaule.getModuleid());
			lktworkmapper.AddDevmsgGroup(selDevmsg.getId(), selDevmsggroup.getGroupid(),
					lktWorkUpdateAppVaule.getModuleid(), selDevmsg.getLat(), selDevmsg.getLng());
			// 保存工单新设备编号
			lktworkmapper.UpdataWoDevnum(lktWorkUpdateAppVaule.getDevnum(), lktWorkUpdateAppVaule.getWoid());
			// 修改工单与设备关联
			lktworkmapper.UpdataWoDev(selDevmsg.getId(), lktWorkUpdateAppVaule.getModuleid(),
					lktWorkUpdateAppVaule.getWoid());
		}
		// 修改设备信息
		code = lktworkmapper.LKTWorkUpdateAppdev(lktWorkUpdateAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 修改与工单关联的报警为已处理
		for (int i = 0; i < alarms.size(); i++) {
			code = lktworkmapper.LKTWorkMaintenanceAppal(alarms.get(i).getId(), lktWorkUpdateAppVaule.getModuleid(),
					devid);
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 修改与工单关联的事件为已处理
		for (int i = 0; i < events.size(); i++) {
			code = lktworkmapper.LKTWorkMaintenanceAppev(events.get(i).getId(), lktWorkUpdateAppVaule.getModuleid());
			if (devid != null) {
				lktworkmapper.UpdataEvent(devid, events.get(i).getId());
			}
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 插入维修记录表数据
		code = lktworkmapper.LKTWorkMaintenanceApplog(lktWorkUpdateAppVaule.getWoid(), lktWorkUpdateAppVaule.getDevid(),
				lktWorkUpdateAppVaule.getUserid());
		return code;
	}

	// App工单详情-已处理或挂起
	@Override
	public LKTWorkCompleteApp LKTWorkCompleteApp(Integer woid, Integer moduleid, Integer userid) {
		LKTWorkCompleteApp img = lktworkmapper.LKTWorkCompleteApp(woid, moduleid);
		LKTWorkDetailedApp list = LKTWorkDetailedApp(moduleid, woid, userid);
		LKTWorkCompleteApp lktWorkCompleteApp = lktworkmapper.LKTWorkCompleteAppList(woid, moduleid);
		lktWorkCompleteApp.setProblemdesc(img.getProblemdesc());
		lktWorkCompleteApp.setPictureurl(img.getPictureurl());
		lktWorkCompleteApp.setHandleType(img.getHandleType());
		lktWorkCompleteApp.setImg(list.getImg());
		lktWorkCompleteApp.setSon(list.getSon());
		lktWorkCompleteApp.setDevicetype(list.getDevicetype());
		lktWorkCompleteApp.setRemark(img.getRemark());
		return lktWorkCompleteApp;
	}

	// App已处理工单列表
	@Override
	public List<LKTWorkCompleteListApp> LKTWorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode) {
		Integer[] moduleid = { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		List<LKTWorkCompleteListApp> listson = new ArrayList<>();
		for (int j = 0; j < moduleid.length; j++) {
			List<LKTWorkCompleteListApp> list = lktworkmapper.LKTWorkCompleteListApp(userid, readtype, dvenumorcode,
					moduleid[j]);
			for (int i = 0; i < list.size(); i++) {
				LKTWorkCompleteListApp listtime = list.get(i);
				if ((Integer.parseInt(list.get(i).getOrdertime()) / 60) < 60) {
					listtime.setOrdertime(String.valueOf(Integer.parseInt(list.get(i).getOrdertime()) / 60) + "分");
				} else if (((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) < 24) {
					int min = Integer.parseInt(list.get(i).getOrdertime())
							- ((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) * 60 * 60;
					listtime.setOrdertime(String.valueOf(((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60))
							+ "时" + String.valueOf(min / 60) + "分");
				} else {
					int mouse = Integer.parseInt(list.get(i).getOrdertime())
							- (((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) / 24) * 60 * 60 * 24;
					listtime.setOrdertime(
							String.valueOf((((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) / 24)) + "天"
									+ String.valueOf(((mouse / 60) / 60)) + "时");
				}
			}
			listson.addAll(list);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 数据排序
		int j, k;
		int flag = listson.size();// flag来记录最后交换的位置，也就是排序的尾边界
		while (flag > 0) {// 排序未结束标志
			k = flag; // k 来记录遍历的尾边界
			flag = 0;
			for (j = 1; j < k; j++) {
				try {
					if (sdf.parse(listson.get(j).getAddtime()).getTime() > sdf.parse(listson.get(j - 1).getAddtime())
							.getTime()) {// 前面的数字大于后面的数字就交换
						// 交换a[j-1]和a[j]
						LKTWorkCompleteListApp temp;
						temp = listson.get(j - 1);
						listson.set(j - 1, listson.get(j));
						listson.set(j, temp);
						// 表示交换过数据;
						flag = j;// 记录最新的尾边界.
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 排序结束
		return listson;
	}
}
