package com.hotcomm.prevention.service.manage.workorder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush_msg;
import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;
import com.hotcomm.prevention.bean.mysql.manage.workorder.SelDevmsg;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkAllAlarm;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteListApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailedApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetails;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsAllevent;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsDeviceAll;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsInstructions;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDevList;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDevnum;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkListFather;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkMyproject;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkTure;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkUntreatedApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkListcondition;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkMaintenanceAppVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkNewVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkUpdateAppVaule;
import com.hotcomm.prevention.db.mysql.manage.appPush.AppPushMsgMapper;
import com.hotcomm.prevention.db.mysql.manage.workorder.WorkMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.appPush.AppPushService;
import com.hotcomm.prevention.service.manage.websocket.SocketService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.JSONUtil;
import com.hotcomm.prevention.utils.PushUtil;

@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private WorkMapper workmapper;

	@Autowired
	private SocketService sock;

	private TMessageLog message = new TMessageLog();
	@Autowired
	AppPushService appPushService;

	@Autowired
	AppPushMsgMapper appPushMsgMapper;

	// ("查询用户下的设备总数")
	@Override
	public WorkDevnum WorkDevnum(Integer moduleid, Integer userid) {
		return workmapper.WorkDevnum(moduleid, userid);
	}

	// ("查询地图项目信息，工单数")
	@Override
	public List<WorkMyproject> WorkMyproject(Integer moduleid, Integer userid) {
		return workmapper.WorkMyproject(moduleid, userid);
	}

	// ("工单列表（可按项目组查）")
	@Override
	public Page<WorkListFather> WorkListFather(WorkListcondition WorkListcondition) throws MyException {
		Page<WorkListFather> list = workmapper.WorkListFather(WorkListcondition);
		return list;
	}

	// ("查询未处理报警与未处理事件自动带入设备")
	@Override
	public List<WorkAllAlarm> WorkAllAlarm(Integer moduleid, Integer userid) throws MyException {
		List<WorkAllAlarm> alarms = workmapper.WorkAllAlarm(moduleid, userid, null, null);
		List<WorkAllAlarm> evens = workmapper.WorkAllEven(moduleid, userid, null, null);
		alarms.addAll(evens);
		return alarms;
	}

	// ("故障设备列表查询")
	@Override
	public List<WorkDevList> WorkDevList(Integer moduleid, Integer userid) throws MyException {
		List<WorkDevList> devLists = workmapper.WorkDevList(moduleid, userid);
		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < devLists.size(); i++) {
			id.add(devLists.get(i).getId());
		}
		if (id != null && id.size() > 0) {
			List<WorkAllAlarm> alarms = workmapper.WorkAllAlarm(moduleid, userid, id, null);
			List<WorkAllAlarm> events = workmapper.WorkAllEven(moduleid, userid, id, null);
			for (int i = 0; i < devLists.size(); i++) {
				// 取出第i条父数据
				WorkDevList father = devLists.get(i);
				// 储存子数据
				List<WorkAllAlarm> alarmson = new ArrayList<>();
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
	@Transactional
	@Override
	public Integer WorkNew(WorkNewVaule WorkNewVaule) throws MyException {
		Integer code = 0;
		String[] devid = null;
		if (WorkNewVaule.getDev_id() != null) {
			devid = WorkNewVaule.getDev_id().split(",");
			// 分割传入的报警id
			String[] alarmid = null;
			List<Integer> aeid = null;
			List<WorkAllAlarm> alarmlist = null;
			if (WorkNewVaule.getAlarm_id() != null && !WorkNewVaule.getAlarm_id().equals("")) {
				alarmid = WorkNewVaule.getAlarm_id().split(",");
				aeid = new ArrayList<Integer>();
				for (int j = 0; j < alarmid.length; j++) {
					aeid.add(Integer.parseInt(alarmid[j]));
				}
				// 查询传入报警数据信息
				alarmlist = workmapper.WorkAllAlarm(WorkNewVaule.getModuleid(), WorkNewVaule.getUserid(), null, aeid);
			}
			String[] eventid = null;
			List<Integer> eid = null;
			List<WorkAllAlarm> eventlist = null;
			if (WorkNewVaule.getEvent_id() != null && !WorkNewVaule.getEvent_id().equals("")) {
				// 分割传入的事件id
				eventid = WorkNewVaule.getEvent_id().split(",");
				eid = new ArrayList<Integer>();
				for (int j = 0; j < eventid.length; j++) {
					eid.add(Integer.parseInt(eventid[j]));
				}
				// 查询传入的事件id信息
				eventlist = workmapper.WorkAllEven(WorkNewVaule.getModuleid(), WorkNewVaule.getUserid(), null, eid);
			}
			// 添加工单数据，工单与设备关联数据
			for (int i = 0; i < devid.length; i++) {
				// 添加工单
				code = workmapper.WorkNew(WorkNewVaule);
				// 写入工单编号
				WorkNewVaule.setCode("HK_00" + WorkNewVaule.getId().toString());
				code = workmapper.WorkNewUpdate("HK_00" + WorkNewVaule.getId().toString(), null, WorkNewVaule.getId());
				if (devid != null) {
					// 添加工单与设备的关联
					workmapper.WorkNewDev(WorkNewVaule.getId(), Integer.parseInt(devid[i]), WorkNewVaule.getModuleid());
				}
				// 处理工单与报警关联
				if (alarmlist != null && alarmlist.size() > 0) {
					for (int k = 0; k < alarmid.length; k++) {
						// 根据设备id判断是否这条数据为改设备
						if (devid[i].equals(alarmlist.get(k).getDevid().toString())) {
							// 添加报警与父工单关联
							workmapper.WorkNewAE(WorkNewVaule.getId(), alarmlist.get(k).getId(), 2,
									WorkNewVaule.getModuleid());
							// 修改t_dev_alarm的handler字段为assignid参数,
							// handlestate
							// 字段改为1
							workmapper.WorkNewAlarmUp(WorkNewVaule.getAssignid(), alarmlist.get(k).getId(),
									WorkNewVaule.getModuleid());
						}
					}
				}
				// 处理工单与事件关联
				if (eventlist != null && eventlist.size() > 0) {
					for (int f = 0; f < eventlist.size(); f++) {
						if (devid[i].equals(eventlist.get(f).getDevid().toString())) {
							// 添加事件与工单关联
							workmapper.WorkNewAE(WorkNewVaule.getId(), eventlist.get(f).getId(), 1,
									WorkNewVaule.getModuleid());
							// 更改事件上报状态state为1处理中
							workmapper.WorkNewEventUp(eventlist.get(f).getId(), WorkNewVaule.getModuleid());
						}
					}
				}
			}
		} else {// 添加空工单
				// 添加工单
			code = workmapper.WorkNew(WorkNewVaule);
			code = workmapper.WorkNewUpdate("HK_00" + WorkNewVaule.getId().toString(), null, WorkNewVaule.getId());
		}
		String str = "你有新的工单，请尽快前去处理。工单编号为：" + WorkNewVaule.getCode();
		message.setUserid(WorkNewVaule.getUserid());
		message.setMessage(str);
		message.setReceiverid(WorkNewVaule.getAssignid().toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(WorkNewVaule.getAssignid());
		if (t_hk_apppush != null) {
			WorkNewVaule l = new WorkNewVaule();
			l.setId(WorkNewVaule.getId());
			l.setModuleid(WorkNewVaule.getModuleid());
			code = PushUtil.sendAllsetNotification("工单消息", str, JSONUtil.toJson(ApiResult.resultInfo("2", "工单", l)),
					workmapper.getUserRegid(WorkNewVaule.getAssignid()).getRegid(), 86400);
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
				PushUtil.sendAllsetNotification("0", "0", "0",workmapper.getUserRegid(WorkNewVaule.getAssignid()).getRegid(), 86400);
			}
		} 
		return code;
	}

	// 根据id查询未处理报警与未处理事件自动带入设备
	@Override
	public List<WorkAllAlarm> WorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type)
			throws MyException {
		String[] AEid = id.split(",");
		List<Integer> aeid = new ArrayList<Integer>();
		for (int i = 0; i < AEid.length; i++) {
			aeid.add(Integer.parseInt(AEid[i]));
		}
		if (type == 2) {
			return workmapper.WorkAllAlarm(moduleid, userid, null, aeid);
		} else if (type == 1) {
			return workmapper.WorkAllEven(moduleid, userid, null, aeid);
		}
		return null;
	}

	// 工单内容查询
	@Override
	public WorkDetails WorkDetails(Integer woid, Integer userid, Integer moduleid) {
		WorkDetails WorkDetails = workmapper.WorkDetails(woid, userid, moduleid);
		// 直接查询工单关联报警、事件记录
		List<WorkDetailsAllevent> alarms = workmapper.WorkDetailsAllAlarmonf(woid, userid, moduleid);
		List<WorkDetailsAllevent> events = workmapper.WorkDetailsAllEvenonf(woid, userid, moduleid);
		alarms.addAll(events);
		WorkDetails.setAllevents(alarms);
		// 查询工单关联设备
		WorkDetailsDeviceAll devalls = workmapper.WorkDetailsDeviceAll(woid, moduleid, userid);
		WorkDetails.setDevall(devalls);
		// 查询工单关联指示
		List<WorkDetailsInstructions> instructions = workmapper.WorkDetailsInstructions(woid, moduleid);
		WorkDetails.setInstructions(instructions);
		return WorkDetails;
	}

	// 查询已处理或挂起工单
	@Override
	public WorkTure WorkTure(Integer moduleid, Integer woid) {
		return workmapper.WorkTure(moduleid, woid);
	}

	// 删除工单
	@Transactional
	@Override
	public Integer WorkDelete(Integer moduleid, Integer userid, Integer woid) {
		Integer code = 0;
		// 删除工单
		code = workmapper.WorkDeletef(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单与报警/事件关联
		code = workmapper.WorkDeleteAE(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单下的资源文件
		code = workmapper.WorkDeleteResources(woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		// 删除工单与设备关联
		code = workmapper.WorkDeleteDev(moduleid, woid);
		if (code <= 0) {
			throw new MyException("0", "删除失败！！");
		}
		return code;
	}

	// 审核工单
	@Transactional
	@Override
	public Integer WorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid,
			Integer assignid) throws MyException {
		Integer code = 0;
		Integer state = 0;
		WorkDetails WorkDetails = WorkDetails(woid, userid, moduleid);
		String str = "你的工单：" + WorkDetails.getCode() + "，审核未通过";
		// 直接查询工单关联报警、事件记录
		List<WorkDetailsAllevent> alarms = workmapper.WorkDetailsAllAlarmonf(woid, userid, moduleid);
		List<WorkDetailsAllevent> events = workmapper.WorkDetailsAllEvenonf(woid, userid, moduleid);
		// 查询工单关联设备
		WorkDetailsDeviceAll devalls = workmapper.WorkDetailsDeviceAll(woid, moduleid, userid);
		if (audit == 1) {
			state = 2;
			str = "你的工单：" + woid.toString() + "，审核已通过";
		}
		// 更改工单数据审核状态
		code = workmapper.WorkExamine(state, audit, woid, moduleid);
		// 更改工单数据对应的维修记录表数据审核状态
		code = workmapper.WorkExamineRepair(remark, audit, woid);
		// 审核不通过,
		if (audit != 1) {
			for (int i = 0; i < alarms.size(); i++) {// 修改工单的报警记录为未处理
				code = workmapper.WorkExamineAlarmUp(alarms.get(i).getId(), moduleid);
			}
			for (int i = 0; i < events.size(); i++) {// 修改工单的事件记录为未处理
				code = workmapper.WorkExamineEventUp(events.get(i).getId(), moduleid);
			}
			if (devalls != null) {
				// 修改设备状态为正常
				code = workmapper.WorkExamineDevUp(devalls.getId(), moduleid);
			}
			// 删除该工单所有资源文件
			workmapper.WorkDeleteResources(woid);
		}
		message.setUserid(userid);
		message.setMessage(str);
		message.setReceiverid(assignid.toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(assignid);
		if (t_hk_apppush != null) {
			WorkNewVaule WorkNewVaule = new WorkNewVaule();
			WorkNewVaule.setId(woid);
			WorkNewVaule.setModuleid(moduleid);
			code = PushUtil.sendAllsetNotification("工单消息", str,
					JSONUtil.toJson(ApiResult.resultInfo("2", "工单", WorkNewVaule)), t_hk_apppush.getRegid(), 86400);
			if (code == 201) {// 201推送失败
				// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
				T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
				t_hk_apppush_msg.setTitle("工单消息");
				t_hk_apppush_msg.setContent(str);
				t_hk_apppush_msg.setMessage(JSONUtil.toJson(ApiResult.resultInfo("2", "工单", WorkNewVaule)));
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
	public Integer Reminder(Integer userid, Integer woid, Integer assignid, String code) throws MyException {
		String str = "请尽快前去处理工单：" + code;
		message.setUserid(userid);
		message.setMessage(str);
		message.setReceiverid(assignid.toString());
		sock.sendMessageToOne(message);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = appPushService.selectRegid(assignid);
		if (t_hk_apppush != null) {
			int code1 = PushUtil.sendAllsetNotification("工单消息", str,
					JSONUtil.toJson(ApiResult.resultInfo("2", "工单", workmapper.SelectWo(woid))),
					t_hk_apppush.getRegid(), 86400);
			if (code1 == 201) {// 201推送失败
				// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
				T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
				t_hk_apppush_msg.setTitle("工单消息");
				t_hk_apppush_msg.setContent(str);
				t_hk_apppush_msg
						.setMessage(JSONUtil.toJson(ApiResult.resultInfo("2", "工单", workmapper.SelectWo(woid))));
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
	public List<WorkUntreatedApp> WorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode,
			Integer moduleid) {
		/*
		 * List<WorkUntreatedApp> list = new ArrayList<>(); Integer[] moduleid =
		 * { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 }; for (int i = 0; i
		 * < moduleid.length; i++) {
		 * list.addAll(workmapper.WorkUntreatedApp(userid, readtype,
		 * dvenumorcode, moduleid[i])); } SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 数据排序 int j, k; int flag =
		 * list.size();// flag来记录最后交换的位置，也就是排序的尾边界 while (flag > 0) {// 排序未结束标志
		 * k = flag; // k 来记录遍历的尾边界 flag = 0; for (j = 1; j < k; j++) { try { if
		 * (sdf.parse(list.get(j).getAddtime()).getTime() > sdf.parse(list.get(j
		 * - 1).getAddtime()) .getTime()) {// 前面的数字大于后面的数字就交换 // 交换a[j-1]和a[j]
		 * WorkUntreatedApp temp; temp = list.get(j - 1); list.set(j - 1,
		 * list.get(j)); list.set(j, temp); // 表示交换过数据; flag = j;// 记录最新的尾边界. }
		 * } catch (ParseException e) { e.printStackTrace(); } } } // 排序结束
		 */ return workmapper.WorkUntreatedApp(userid, readtype, dvenumorcode, moduleid);
	}

	// App待处理工单详情
	@Override
	public WorkDetailedApp WorkDetailedApp(Integer moduleid, Integer woid, Integer userid) throws MyException {
		WorkDetailedApp WorkDetailedApp = workmapper.WorkDetailedApp(moduleid, woid);
		// 查询工单关联报警、事件记录
		List<WorkDetailsAllevent> alarms = workmapper.WorkDetailsAllAlarmonf(woid, userid,
				WorkDetailedApp.getModuleid());
		List<WorkDetailsAllevent> events = workmapper.WorkDetailsAllEvenonf(woid, userid,
				WorkDetailedApp.getModuleid());
		alarms.addAll(events);
		WorkDetailedApp.setSon(alarms);
		// 查询工单关联报警、事件记录关联图片
		String img = null;
		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < alarms.size(); i++) {
			id.add(alarms.get(i).getId());
		}
		WorkDetailedApp alarmimg = null;
		if (id.size() > 0) {
			alarmimg = workmapper.WorkDetailedAppimg(WorkDetailedApp.getModuleid(), id, 2);
		}
		id.clear();
		for (int i = 0; i < alarms.size(); i++) {
			id.add(alarms.get(i).getId());
		}
		WorkDetailedApp eventimg = null;
		if (id.size() > 0) {
			eventimg = workmapper.WorkDetailedAppimg(WorkDetailedApp.getModuleid(), id, 1);
		}
		if (alarmimg != null && eventimg != null) {
			img = alarmimg.getImg() + "," + eventimg.getImg();
		} else if (alarmimg == null && eventimg != null) {
			img = eventimg.getImg();
		} else if (alarmimg != null && eventimg == null) {
			img = alarmimg.getImg();
		}
		WorkDetailedApp.setImg(img);
		return WorkDetailedApp;
	}

	// App工单处理-维修
	@Transactional
	@Override
	public Integer WorkMaintenanceApp(WorkMaintenanceAppVaule WorkMaintenanceAppVaule) {
		Integer code = 0;
		// 直接查询工单关联报警、事件记录
		List<WorkDetailsAllevent> alarms = workmapper.WorkDetailsAllAlarmonf(WorkMaintenanceAppVaule.getWoid(),
				WorkMaintenanceAppVaule.getUserid(), WorkMaintenanceAppVaule.getModuleid());
		List<WorkDetailsAllevent> events = workmapper.WorkDetailsAllEvenonf(WorkMaintenanceAppVaule.getWoid(),
				WorkMaintenanceAppVaule.getUserid(), WorkMaintenanceAppVaule.getModuleid());
		// 修改工单状态,处理方式,结束时间
		WorkMaintenanceAppVaule.setHandleType(1);
		code = workmapper.WorkMaintenanceApp(WorkMaintenanceAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 插入图片资源
		if (WorkMaintenanceAppVaule.getPictureurl() != null && !WorkMaintenanceAppVaule.getPictureurl().equals("")) {
			String[] imglist = WorkMaintenanceAppVaule.getPictureurl().split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = workmapper.WorkMaintenanceAppImg(WorkMaintenanceAppVaule.getWoid(), 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		if (WorkMaintenanceAppVaule.getVoiceurl() != null && !WorkMaintenanceAppVaule.getVoiceurl().equals("")) {
			// 插入音频资源
			String[] voicelist = WorkMaintenanceAppVaule.getVoiceurl().split(",");
			for (int i = 0; i < voicelist.length; i++) {
				code = workmapper.WorkMaintenanceAppImg(WorkMaintenanceAppVaule.getWoid(), 2, voicelist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		// 修改与工单关联的报警为已处理
		for (int i = 0; i < alarms.size(); i++) {
			code = workmapper.WorkMaintenanceAppal(alarms.get(i).getId(), WorkMaintenanceAppVaule.getModuleid(), null);
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 修改与工单关联的事件为已处理
		for (int i = 0; i < events.size(); i++) {
			code = workmapper.WorkMaintenanceAppev(events.get(i).getId(), WorkMaintenanceAppVaule.getModuleid());
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 插入维修记录表数据
		code = workmapper.WorkMaintenanceApplog(WorkMaintenanceAppVaule.getWoid(), WorkMaintenanceAppVaule.getDevid(),
				WorkMaintenanceAppVaule.getUserid());
		return code;
	}

	// App工单处理-设备挂起
	@Transactional
	@Override
	public Integer WorkHangApp(String problemdesc, Integer woid, String pictureurl) {
		Integer code = 0;
		code = workmapper.WorkHangApp(2, problemdesc, woid);
		// 插入图片资源
		if (pictureurl != null && !pictureurl.equals("")) {
			String[] imglist = pictureurl.split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = workmapper.WorkMaintenanceAppImg(woid, 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		return code;
	}

	// App工单处理-设备更换
	@Transactional
	@Override
	public Integer WorkUpdateApp(WorkUpdateAppVaule WorkUpdateAppVaule) {
		Integer code = 0;
		// 直接查询工单关联报警、事件记录
		List<WorkDetailsAllevent> alarms = workmapper.WorkDetailsAllAlarmonf(WorkUpdateAppVaule.getWoid(),
				WorkUpdateAppVaule.getUserid(), WorkUpdateAppVaule.getModuleid());
		List<WorkDetailsAllevent> events = workmapper.WorkDetailsAllEvenonf(WorkUpdateAppVaule.getWoid(),
				WorkUpdateAppVaule.getUserid(), WorkUpdateAppVaule.getModuleid());
		WorkUpdateAppVaule.setHandleType(3);
		code = workmapper.WorkUpdateApp(WorkUpdateAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 插入图片资源
		if (WorkUpdateAppVaule.getPictureurl() != null && !WorkUpdateAppVaule.getPictureurl().equals("")) {
			String[] imglist = WorkUpdateAppVaule.getPictureurl().split(",");
			for (int i = 0; i < imglist.length; i++) {
				code = workmapper.WorkMaintenanceAppImg(WorkUpdateAppVaule.getWoid(), 1, imglist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		if (WorkUpdateAppVaule.getVoiceurl() != null && !WorkUpdateAppVaule.getVoiceurl().equals("")) {
			// 插入音频资源
			String[] voicelist = WorkUpdateAppVaule.getVoiceurl().split(",");
			for (int i = 0; i < voicelist.length; i++) {
				code = workmapper.WorkMaintenanceAppImg(WorkUpdateAppVaule.getWoid(), 2, voicelist[i]);
				if (code <= 0) {
					throw new MyException("0", "操作失败！！");
				}
			}
		}
		// 新设备id
		Integer devid = null;
		SelDevmsg woci = workmapper.SelWoci(WorkUpdateAppVaule.getWoid());
		if (woci.getNewdevnum() == null) {
			// 第一次更换
			// 查出设备，新增一个旧设备
			SelDevmsg selDevmsg = workmapper.SelDevmsg(WorkUpdateAppVaule.getDevid(), WorkUpdateAppVaule.getModuleid());
			selDevmsg.setModuleid(WorkUpdateAppVaule.getModuleid());
			workmapper.AddDevmsg(selDevmsg);
			devid = selDevmsg.getId();
			// 查出设备与项目关联，新增旧设备与项目关联
			SelDevmsg selDevmsggroup = workmapper.SelDevmsgGroup(WorkUpdateAppVaule.getDevid(),
					WorkUpdateAppVaule.getModuleid());
			workmapper.AddDevmsgGroup(selDevmsg.getId(), selDevmsggroup.getGroupid(), WorkUpdateAppVaule.getModuleid(),
					selDevmsg.getLat(), selDevmsg.getLng());
			// 保存工单新设备编号
			workmapper.UpdataWoDevnum(WorkUpdateAppVaule.getDevnum(), WorkUpdateAppVaule.getWoid());
			// 修改工单与设备关联
			workmapper.UpdataWoDev(selDevmsg.getId(), WorkUpdateAppVaule.getModuleid(), WorkUpdateAppVaule.getWoid());
		}
		// 修改设备信息
		code = workmapper.WorkUpdateAppdev(WorkUpdateAppVaule);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		// 修改与工单关联的报警为已处理
		for (int i = 0; i < alarms.size(); i++) {
			code = workmapper.WorkMaintenanceAppal(alarms.get(i).getId(), WorkUpdateAppVaule.getModuleid(), devid);
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 修改与工单关联的事件为已处理
		for (int i = 0; i < events.size(); i++) {
			code = workmapper.WorkMaintenanceAppev(events.get(i).getId(), WorkUpdateAppVaule.getModuleid());
			if (devid != null) {
				workmapper.UpdataEvent(devid, events.get(i).getId());
			}
			if (code <= 0) {
				throw new MyException("0", "操作失败！！");
			}
		}
		// 插入维修记录表数据
		code = workmapper.WorkMaintenanceApplog(WorkUpdateAppVaule.getWoid(), WorkUpdateAppVaule.getDevid(),
				WorkUpdateAppVaule.getUserid());
		return code;
	}

	// App工单详情-已处理或挂起
	@Override
	public WorkCompleteApp WorkCompleteApp(Integer woid, Integer moduleid, Integer userid) {
		WorkCompleteApp img = workmapper.WorkCompleteApp(woid, moduleid);
		WorkDetailedApp list = WorkDetailedApp(moduleid, woid, userid);
		WorkCompleteApp WorkCompleteApp = workmapper.WorkCompleteAppList(woid, moduleid);
		WorkCompleteApp.setProblemdesc(img.getProblemdesc());
		WorkCompleteApp.setPictureurl(img.getPictureurl());
		WorkCompleteApp.setHandleType(img.getHandleType());
		WorkCompleteApp.setImg(list.getImg());
		WorkCompleteApp.setSon(list.getSon());
		WorkCompleteApp.setDevicetype(list.getDevicetype());
		WorkCompleteApp.setRemark(img.getRemark());
		return WorkCompleteApp;
	}

	// App已处理工单列表
	@Override
	public List<WorkCompleteListApp> WorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode,
			Integer moduleid) {
		List<WorkCompleteListApp> list = workmapper.WorkCompleteListApp(userid, readtype, dvenumorcode, moduleid);
		for (int i = 0; i < list.size(); i++) {
			WorkCompleteListApp listtime = list.get(i);
			if ((Integer.parseInt(list.get(i).getOrdertime()) / 60) < 60) {
				listtime.setOrdertime(String.valueOf(Integer.parseInt(list.get(i).getOrdertime()) / 60) + "分");
			} else if (((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) < 24) {
				int min = Integer.parseInt(list.get(i).getOrdertime())
						- ((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) * 60 * 60;
				listtime.setOrdertime(String.valueOf(((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60)) + "时"
						+ String.valueOf(min / 60) + "分");
			} else {
				int mouse = Integer.parseInt(list.get(i).getOrdertime())
						- (((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) / 24) * 60 * 60 * 24;
				listtime.setOrdertime(String.valueOf((((Integer.parseInt(list.get(i).getOrdertime()) / 60) / 60) / 24))
						+ "天" + String.valueOf(((mouse / 60) / 60)) + "时");
			}
		}
		return list;
	}
}
