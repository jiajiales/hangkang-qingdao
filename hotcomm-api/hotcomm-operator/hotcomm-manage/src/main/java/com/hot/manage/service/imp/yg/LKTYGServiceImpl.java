package com.hot.manage.service.imp.yg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.yg.LKTMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.system.TMessageLog;
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
import com.hot.manage.entity.yg.LKTSelectGroupWork;
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
import com.hot.manage.entity.yg.LKTWorkDetailsAppea;
import com.hot.manage.entity.yg.LKTWorkHandleApp;
import com.hot.manage.entity.yg.LKTWorkTobetreatedApp;
import com.hot.manage.entity.yg.LKTWorkUntreated;
import com.hot.manage.entity.yg.item.LKTNewWorkVaule;
import com.hot.manage.entity.yg.item.LKTPatrolscondition;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkcondition;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkconditionSon;
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
import com.hot.manage.service.SocketService;
import com.hot.manage.service.yg.LKTYGService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTYGServiceImpl implements LKTYGService {

	@Autowired
	private LKTMapper lKTMapper;

	@Autowired
	private SocketService sock;

	@Override
	public List<LKTMyproject> LkTMyproject(Integer moduleid, Integer userid) throws MyException {
		return lKTMapper.LkTMyproject(moduleid, userid);
	}

	@Override
	public LKTSelectdevicenum LKTSelectdevicenum(Integer moduleid, Integer userid) throws MyException {
		return lKTMapper.LKTSelectdevicenum(moduleid, userid);
	}

	@Override
	public PageInfo<LKTSelectGroupWorkFather> LKTSelectGroupWork(
			LKTSelectGroupWorkcondition lktSelectGroupWorkcondition) throws MyException {
		// 查询子工单数据
		LKTSelectGroupWorkconditionSon sonlist = new LKTSelectGroupWorkconditionSon();
		sonlist.setEndtime(lktSelectGroupWorkcondition.getEndtime());
		sonlist.setGroupid(lktSelectGroupWorkcondition.getGroupid());
		sonlist.setModuleid(lktSelectGroupWorkcondition.getModuleid());
		sonlist.setStarttime(lktSelectGroupWorkcondition.getStarttime());
		sonlist.setWorkname(lktSelectGroupWorkcondition.getWorkname());
		List<LKTSelectGroupWork> listson = lKTMapper.LKTSelectGroupWork(sonlist);
		// 查询父工单数据
		Page<LKTSelectGroupWorkFather> listfather = lKTMapper.LKTSelectGroupWorkFather(lktSelectGroupWorkcondition);
		for (int i = 0; i < listfather.size(); i++) {
			// 取出第i条父工单数据
			LKTSelectGroupWorkFather father = listfather.get(i);
			// 存子工单数据
			List<LKTSelectGroupWork> sonnum = new ArrayList<>();

			for (int j = 0; j < listson.size(); j++) {
				// 循环判断这条父工单的全部子工单数据
				LKTSelectGroupWork son = listson.get(j);
				if (father.getWoid().toString().equals(son.getFatherid().toString())) {
					sonnum.add(son);
				}
			}
			father.setSon(sonnum);
			// 第i条工单数据设置完成
			listfather.set(i, father);
		}
		PageHelper.startPage(lktSelectGroupWorkcondition.getPageNum(), lktSelectGroupWorkcondition.getPageSize());
		Page<LKTSelectGroupWorkFather> page = listfather;
		List<LKTSelectGroupWorkFather> list = ConverUtil.converPage(page, LKTSelectGroupWorkFather.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public List<LKTEstablishygAlarmAndEvent> LKTEstablishygAlarmAndEvent() throws MyException {
		return lKTMapper.LKTEstablishygAlarmAndEvent();
	}

	@Override
	public List<LKTEstablishygAlarmAndEvent> LKTSelectAlarmAndEvent(Integer type, String id) throws MyException {
		return lKTMapper.LKTSelectAlarmAndEvent(type, id);
	}

	@Override
	public List<LKTEstablishygDevice> LKTEstablishygDevice(Integer moduleid, Integer userid) throws MyException {
		return lKTMapper.LKTEstablishygDevice(moduleid, userid);
	}

	@Override
	public List<LKTSelctDevOnid> LKTSelctDevOnid(List<Integer> id) throws MyException {
		List<LKTSelctDevOnid> listalarm = lKTMapper.LKTSelctDevOnidAlarm(id);
		List<LKTSelctDevOnid> listevent = lKTMapper.LKTSelctDevOnidEvent(id);
		listalarm.addAll(listevent);
		return listalarm;
	}

	@Override
	public List<LKTSelectHandle> LKTSelectHandle(Integer userid) throws MyException {
		return lKTMapper.LKTSelectHandle(userid);
	}

	@Override
	public LKTCode LKTNewWork(LKTNewWorkVaule lktNewWorkVaule) {
		return lKTMapper.LKTNewWork(lktNewWorkVaule);
	}

	@Override
	public LKTSelectWork LKTSelectWork(Integer workid) throws MyException {
		return lKTMapper.LKTSelectWork(workid);
	}

	@Override
	public List<LKTSelectAllevent> LKTSelectAllevent(Integer workid) throws MyException {
		return lKTMapper.LKTSelectAllevent(workid);
	}

	@Override
	public List<LKTDeviceAll> LKTDeviceAll(Integer workid) throws MyException {
		return lKTMapper.LKTDeviceAll(workid);
	}

	@Override
	public List<LKTInstructionsAll> LKTInstructionsAll(Integer workid) throws MyException {
		return lKTMapper.LKTInstructionsAll(workid);
	}

	@Override
	public List<LKTTureWork> LKTTureWork(Integer workid) throws MyException {
		return lKTMapper.LKTTureWork(workid);
	}

	@Override
	public LKTCode LKTDeleteWork(Integer workid) throws MyException {
		return lKTMapper.LKTDeleteWork(workid);
	}

	@Override
	public LKTCode LKTWorkExamine(Integer userid, Integer workid, Integer audit, Integer pdid) throws MyException {
		LKTCode code = lKTMapper.LKTWorkExamine(workid, audit);
		if (code.getCode() == 1 & audit == 0) {
			// 向审核人推送消息
			String str = "工单：" + workid + ",审核未通过";
			TMessageLog message = new TMessageLog();
			message.setUserid(userid);
			message.setMessage(str);
			message.setReceiverid(pdid.toString());
			sock.sendMessageToOne(message);
		}
		return code;
	}

	@Override
	public PageInfo<LKTPatrols> LKTPatrols(LKTPatrolscondition lktPatrolscondition) throws MyException {
		PageHelper.startPage(lktPatrolscondition.getPageNum(), lktPatrolscondition.getPageSize());
		Page<LKTPatrols> page = lKTMapper.LKTPatrols(lktPatrolscondition);
		List<LKTPatrols> list = ConverUtil.converPage(page, LKTPatrols.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public LKTCode LKTPatrolsFrozen(Integer userid, Integer isenable) throws MyException {
		return lKTMapper.LKTPatrolsFrozen(userid, isenable);
	}

	@Override
	public LKTCode LKTPatrolsDelete(Integer patrolsid) throws MyException {
		return lKTMapper.LKTPatrolsDelete(patrolsid);
	}

	@Override
	public LKTCode LKTPatrolsInsert(Integer adduserid) throws MyException {
		return lKTMapper.LKTPatrolsInsert(adduserid);
	}

	@Override
	public List<LKTPatrolsperson> LKTPatrolsperson(Integer userid) throws MyException {
		return lKTMapper.LKTPatrolsperson(userid);
	}

	@Override
	public List<LKTPatrolsperson> LKTPatrolsuser(Integer userid) throws MyException {
		
		return lKTMapper.LKTPatrolsuser(userid);
	}

	@Override
	public PageInfo<LKTSignList> LKTSignList(LKTSignListcondition lktSignListcondition) throws MyException {
		
		PageHelper.startPage(lktSignListcondition.getPageNum(), lktSignListcondition.getPageSize());
		Page<LKTSignList> page = lKTMapper.LKTSignList(lktSignListcondition);
		List<LKTSignList> list = ConverUtil.converPage(page, LKTSignList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public PageInfo<LKTSignHistory> LKTSignHistory(LKTSignHistorycondition lktSignHistorycondition) throws MyException {
		
		PageHelper.startPage(lktSignHistorycondition.getPageNum(), lktSignHistorycondition.getPageSize());
		Page<LKTSignHistory> page = lKTMapper.LKTSignHistory(lktSignHistorycondition);
		List<LKTSignHistory> list = ConverUtil.converPage(page, LKTSignHistory.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public LKTCode LKTSignListDelete(Integer id) throws MyException {
		
		return lKTMapper.LKTSignListDelete(id);
	}

	@Override
	public LKTCode LKTPatrolChange(Integer patrolsided, Integer patrolsid) throws MyException {
		
		return lKTMapper.LKTPatrolChange(patrolsided, patrolsid);
	}

	@Override
	public List<LKTSignGroupList> LKTSignGroupList() throws MyException {
		
		return lKTMapper.LKTSignGroupList();
	}

	@Override
	public LKTCode LKTSignDeviceUser(LKTSignDeviceVaule lktSignDeviceVaule) throws MyException {
		
		lKTMapper.LKTSignDevice(lktSignDeviceVaule);
		return lKTMapper.LKTSignDeviceUser(lktSignDeviceVaule);
	}

	@Override
	public LKTCode LKTSignListUpdateUser(LKTSignListUpdateVaule lktSignListUpdateVaule) throws MyException {
		
		lKTMapper.LKTSignListUpdate(lktSignListUpdateVaule);
		return lKTMapper.LKTSignListUpdateUser(lktSignListUpdateVaule);
	}

	@Override
	public PageInfo<LKTSignList> LKTSignDeviceList(LKTSignListcondition lktSignListcondition) throws MyException {
		
		PageHelper.startPage(lktSignListcondition.getPageNum(), lktSignListcondition.getPageSize());
		Page<LKTSignList> page = lKTMapper.LKTSignDeviceList(lktSignListcondition);
		List<LKTSignList> list = ConverUtil.converPage(page, LKTSignList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public LKTCode LKTSignDeviceDelete(Integer id) throws MyException {
		
		return lKTMapper.LKTSignDeviceDelete(id);
	}

	@Override
	public PageInfo<LKTSignHistory> LKTSignDeviceHistory(LKTSignHistorycondition lktSignHistorycondition)
			throws MyException {
		
		PageHelper.startPage(lktSignHistorycondition.getPageNum(), lktSignHistorycondition.getPageSize());
		Page<LKTSignHistory> page = lKTMapper.LKTSignDeviceHistory(lktSignHistorycondition);
		List<LKTSignHistory> list = ConverUtil.converPage(page, LKTSignHistory.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public LKTCode LKTSignDeviceListUser(LKTSignDeviceListUserVaule lktSignDeviceListUserVaule) throws MyException {
		
		return lKTMapper.LKTSignDeviceListUser(lktSignDeviceListUserVaule);
	}

	@Override
	public List<LKTSignDeviceListApp> LKTSignDeviceListApp(LKTSignDeviceListAppVaule lktSignDeviceListAppVaule)
			throws MyException {
		
		return lKTMapper.LKTSignDeviceListApp(lktSignDeviceListAppVaule);
	}

	@Override
	public LKTSelectUserApp LKTSelectUserApp(Integer userid) {
		
		return lKTMapper.LKTSelectUserApp(userid);
	}

	@Override
	public LKTCode LKTSignDeviceApp(Integer userid, Integer id, Integer state, Integer type) throws MyException {
		
		return lKTMapper.LKTSignDeviceApp(userid, id, state, type);
	}

	@Override
	public List<LKTSignListApp> LKTSignListApp(Integer userid) throws MyException {
		
		return lKTMapper.LKTSignListApp(userid);
	}

	@Override
	public List<LKTWorkUntreated> LKTWorkUntreated(LKTWorkUntreatedcondition lktWorkUntreatedcondition)
			throws MyException {
		
		return lKTMapper.LKTWorkUntreated(lktWorkUntreatedcondition);
	}

	@Override
	public LKTWorkDetailsApp LKTWorkDetailsApp(Integer woid) throws MyException {
		
		LKTWorkDetailsApp list = lKTMapper.LKTWorkDetailsApp(woid);
		List<LKTWorkDetailsAppea> listson = lKTMapper.LKTWorkDetailsAppea(list.getWoid(), list.getDevid());
		String img = "";
		String audio = "";
		String video = "";
		for (int i = 0; i < listson.size(); i++) {
			int type = 1;// 默认为事件1
			if (listson.get(i).getType().equals("报警")) {
				type = 2;
			}
			LKTWorkDetailsApp imglist = lKTMapper.LKTWorkDetailsAppimg(1, type, listson.get(i).getId());
			if (!imglist.getImg().equals("0")) {
				img = imglist.getImg() + "," + img;
			}
			LKTWorkDetailsApp audiolist = lKTMapper.LKTWorkDetailsAppimg(2, type, listson.get(i).getId());
			if (!audiolist.getImg().equals("0")) {
				audio = audiolist.getImg() + "," + audio;
			}
			LKTWorkDetailsApp videolist = lKTMapper.LKTWorkDetailsAppimg(3, type, listson.get(i).getId());
			if (!videolist.getImg().equals("0")) {
				video = videolist.getImg() + "," + video;
			}
		}
		list.setSon(listson);
		list.setImg(img);
		list.setAudio(audio);
		list.setVideo(video);
		return list;
	}

	@Override
	public LKTCode LKTWorkRepairApp(LKTWorkRepairAppVaule lktWorkRepairAppVaule) throws MyException {
		// 1修改盖工单处理结束时间，处理方式，处理描述，备注，处理状态改为已处理
		LKTCode code = lKTMapper.LKTWorkRepairApp(lktWorkRepairAppVaule);
		if (code.getCode() == 1) {// 1,操作成功
			String[] pictureurl = lktWorkRepairAppVaule.getPictureurl().split(",");// 分割图片资源
			for (int i = 0; i < pictureurl.length; i++) {
				lKTMapper.LKTWorkRepairAppInsert(lktWorkRepairAppVaule.getWoid(), 1, pictureurl[i]);
			}
			String[] voiceurl = lktWorkRepairAppVaule.getVoiceurl().split(",");// 分割音频资源
			for (int i = 0; i < voiceurl.length; i++) {
				lKTMapper.LKTWorkRepairAppInsert(lktWorkRepairAppVaule.getWoid(), 2, voiceurl[i]);
			}
		} // 2,上传图片，语音资源LKTWorkRepairAppInsert成功
			// 3,修改工单下与该设备关联的事件或报警记录处理状态
		List<LKTWorkDetailsAppea> alarmorevent = lKTMapper.LKTWorkDetailsAppea(lktWorkRepairAppVaule.getWoid(),
				lktWorkRepairAppVaule.getDevid());
		for (int i = 0; i < alarmorevent.size(); i++) {
			switch (alarmorevent.get(i).getType()) {
			case "事件":
				lKTMapper.LKTWorkRepairAppUpevent(alarmorevent.get(i).getId());// 修改关联的事件处理状态
				break;
			case "报警":
				lKTMapper.LKTWorkRepairAppUpalarm(lktWorkRepairAppVaule.getUserid(), alarmorevent.get(i).getId());// 修改关联的报警处理状态
				break;
			default:
				break;
			}
		}
		// 4，在审核记录表插入一条审核状态的数据记录
		LKTCode codere = lKTMapper.LKTWorkRepairAppRecord(lktWorkRepairAppVaule.getWoid(),
				lktWorkRepairAppVaule.getDevid());
		// 5.向审核人推送消息
		String str = "工单：" + lktWorkRepairAppVaule.getWoid() + ",处理完成，请尽快前去审核";
		TMessageLog message = new TMessageLog();
		message.setUserid(lktWorkRepairAppVaule.getUserid());
		message.setMessage(str);
		message.setReceiverid(lktWorkRepairAppVaule.getPdid().toString());
		sock.sendMessageToOne(message);
		return codere;
	}

	@Override
	public LKTCode LKTWorkGbApp(LKTWorkGbAppVaule lktWorkGbAppVaule) throws MyException {
		
		LKTCode code = lKTMapper.LKTWorkGbApp(lktWorkGbAppVaule.getWoid(), lktWorkGbAppVaule.getProblemdesc());
		if (code.getCode() == 1) {// 1,操作成功
			String[] pictureurl = lktWorkGbAppVaule.getPictureurl().split(",");// 分割图片资源
			for (int i = 0; i < pictureurl.length; i++) {
				lKTMapper.LKTWorkRepairAppInsert(lktWorkGbAppVaule.getWoid(), 1, pictureurl[i]);
			}
		} // 2,上传图片，语音资源LKTWorkRepairAppInsert成功
		return code;
	}

	@Override
	public LKTWorkHandleApp LKTWorkHandleApp(Integer woid) throws MyException {
		LKTWorkHandleApp list = lKTMapper.LKTWorkDetailsAppdate(woid);
		if (list == null) {
			return null;
		}
		List<LKTWorkDetailsAppea> listson = lKTMapper.LKTWorkDetailsAppea(list.getWoid(), list.getDevid());
		String img = "";
		for (int i = 0; i < listson.size(); i++) {
			int type = 1;// 默认为事件1
			if (listson.get(i).getType().equals("报警")) {
				type = 2;
			}
			LKTWorkDetailsApp imglist = lKTMapper.LKTWorkDetailsAppimg(1, type, listson.get(i).getId());
			if (!imglist.getImg().equals("0")) {
				img = imglist.getImg() + "," + img;
			}
		}
		list.setSon(listson);
		list.setImg(img);
		LKTWorkHandleApp listurl = lKTMapper.LKTWorkHandleApp(woid);
		list.setProblemdesc(listurl.getProblemdesc());
		list.setPictureurl(listurl.getPictureurl());
		return list;
	}

	@Override
	public LKTCode LKTWorkReplaceApp(LKTWorkReplaceAppVaule lktWorkReplaceAppVaule) throws MyException {
		
		LKTCode code = new LKTCode();
		// 1更换设备信息
		Integer codedev = lKTMapper.LKTWorkReplaceAppDev(lktWorkReplaceAppVaule);
		// 2更改工单信息
		Integer codedwo = lKTMapper.LKTWorkReplaceAppWo(lktWorkReplaceAppVaule);
		// 3 修改工单下与该设备关联的事件或报警记录处理状态
		List<LKTWorkDetailsAppea> alarmorevent = lKTMapper.LKTWorkDetailsAppea(lktWorkReplaceAppVaule.getWoid(),
				lktWorkReplaceAppVaule.getDevid());
		for (int i = 0; i < alarmorevent.size(); i++) {
			switch (alarmorevent.get(i).getType()) {
			case "事件":
				lKTMapper.LKTWorkRepairAppUpevent(alarmorevent.get(i).getId());// 修改关联的事件处理状态
				break;
			case "报警":
				lKTMapper.LKTWorkRepairAppUpalarm(lktWorkReplaceAppVaule.getUserid(), alarmorevent.get(i).getId());// 修改关联的报警处理状态
				break;
			default:
				break;
			}
		}
		// 4插入图片资源数据
		String[] pictureurl = lktWorkReplaceAppVaule.getPictureurl().split(",");// 分割图片资源
		for (int i = 0; i < pictureurl.length; i++) {
			lKTMapper.LKTWorkRepairAppInsert(lktWorkReplaceAppVaule.getWoid(), 1, pictureurl[i]);
		}
		String[] voiceurl = lktWorkReplaceAppVaule.getVoiceurl().split(",");// 分割音频资源
		for (int i = 0; i < voiceurl.length; i++) {
			lKTMapper.LKTWorkRepairAppInsert(lktWorkReplaceAppVaule.getWoid(), 2, voiceurl[i]);
		}
		if (codedev != 1 || codedwo != 1) {
			code.setCode(0);
			return code;
		}
		// 5.向审核人推送消息
		String str = "工单：" + lktWorkReplaceAppVaule.getWoid() + ",处理完成，请尽快前去审核";
		TMessageLog message = new TMessageLog();
		message.setUserid(lktWorkReplaceAppVaule.getUserid());
		message.setMessage(str);
		message.setReceiverid(lktWorkReplaceAppVaule.getPdid().toString());
		sock.sendMessageToOne(message);
		code.setCode(1);
		return code;
	}

	@Override
	public List<LKTWorkTobetreatedApp> LKTWorkTobetreatedApp(LKTWorkUntreatedcondition lktWorkUntreatedcondition)
			throws MyException {
		List<LKTWorkTobetreatedApp> list = lKTMapper.LKTWorkTobetreatedApp(lktWorkUntreatedcondition);
		for (int i = 0; i < list.size(); i++) {
			LKTWorkTobetreatedApp listtime = list.get(i);
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

	@Override
	public LKTCode LKTReminder(Integer userid, Integer workid, Integer assignid) {
		
		String str = "请尽快前去处理：工单：" + workid;
		TMessageLog message = new TMessageLog();
		message.setUserid(userid);
		message.setMessage(str);
		message.setReceiverid(assignid.toString());
		sock.sendMessageToOne(message);
		LKTCode code = new LKTCode();
		code.setCode(1);
		return code;
	}

}
