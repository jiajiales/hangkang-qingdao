package com.hot.manage.service.common.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.hot.manage.db.common.event.EventDevRelationMapper;
import com.hot.manage.db.common.event.EventMapper;
import com.hot.manage.db.common.event.EventResRelationMapper;
import com.hot.manage.db.common.event.EventStateMapper;
import com.hot.manage.entity.common.alarm.PictureUrlVo;
import com.hot.manage.entity.common.alarm.VoiceUrlVo;
import com.hot.manage.entity.common.event.AppEventInfo;
import com.hot.manage.entity.common.event.AppEventList;
import com.hot.manage.entity.common.event.EventDeviceRele;
import com.hot.manage.entity.common.event.EventFinishVo;
import com.hot.manage.entity.common.event.EventHandling;
import com.hot.manage.entity.common.event.EventInfoVo;
import com.hot.manage.entity.common.event.EventInstructRele;
import com.hot.manage.entity.common.event.EventListParam;
import com.hot.manage.entity.common.event.EventListVo;
import com.hot.manage.entity.common.event.EventParam;
import com.hot.manage.entity.common.event.T_event_state;
import com.hot.manage.entity.common.event.T_hk_evdevrelation;
import com.hot.manage.entity.common.event.T_hk_event;
import com.hot.manage.entity.common.event.T_hk_evresrelation;
import com.hot.manage.entity.common.event.T_module;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.dev.DeviceService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private EventStateMapper eventStateMapper;

	@Autowired
	private EventDevRelationMapper devRelationMapper;

	@Autowired
	private EventResRelationMapper eventResRelationMapper;

	@Override
	public List<EventInstructRele> selectWorkInstruction(Integer eventid, Integer moduleid) throws MyException {
		List<EventInstructRele> list = eventMapper.selectWorkInstruction(eventid, moduleid, 1);
		if (list.size() == 0) {
			return null;
		}

		return eventMapper.selectWorkInstruction(eventid, moduleid, 1);
	}

	@Override
	public List<EventDeviceRele> selectEventDeviceRe(Integer eventid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		return eventMapper.selectEventDeviceRe(eventid, moduleid);
	}

	@Override
	public EventInfoVo selectEventInfo(Integer eventid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		EventInfoVo eventInfoVo = eventMapper.selectEventInfo(eventid, moduleid);
		List<VoiceUrlVo> voiceUrl = eventMapper.getVoiceUrl(eventid, moduleid);
		if (voiceUrl.size() == 0) {
			voiceUrl = null;
		}
		eventInfoVo.setVoiceUrl(voiceUrl);
		List<PictureUrlVo> pictureUrl = eventMapper.getPictureUrl(eventid, moduleid);
		if (pictureUrl.size() == 0) {
			pictureUrl = null;
		}
		eventInfoVo.setPictureUrl(pictureUrl);
		return eventInfoVo;
	}

	@Override
	public EventHandling selectEventHandling(Integer eventid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		return eventMapper.selectEveintHanding(eventid, moduleid);
	}

	@Override
	public EventFinishVo selectEventFinish(Integer eventid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		Integer i = eventMapper.getishander(eventid, moduleid);
		EventFinishVo eventFinishVo = null;
		List<PictureUrlVo> list = null;
		if (i != 0) {
			eventFinishVo = eventMapper.eventFinishToWo(eventid, moduleid);
			if (eventMapper.getFinishWoPictureUrl(eventFinishVo.getWoid(), moduleid).size() != 0) {
				list = eventMapper.getFinishWoPictureUrl(eventFinishVo.getWoid(), moduleid);
			}
		} else {
			eventFinishVo = eventMapper.eventFinishToEvent(eventid, moduleid);
			if (eventMapper.getFinishEventPictureUrl(eventid, moduleid).size() != 0) {
				list = eventMapper.getFinishEventPictureUrl(eventid, moduleid);
			}
		}
		eventFinishVo.setPrictureUrl(list);
		return eventFinishVo;
	}

	@Override
	public Page<EventListVo> selectEventList(EventListParam eventListParam, Integer userid, Integer moduleid)
			throws MyException {
		// TODO Auto-generated method stub
		Page<EventListVo> list = eventMapper.selectEventList(eventListParam, userid, moduleid);
		return list;
	}

	@Override
	public List<T_event_state> selectAllEventState(Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		Example example = new Example(T_event_state.class);
		example.selectProperties("id").selectProperties("state_name").selectProperties("module_id")
				.selectProperties("addtime").selectProperties("type").createCriteria().andEqualTo("module_id", moduleid)
				.andEqualTo("isdelete", 0);
		example.orderBy("addtime").desc();
		List<T_event_state> list = eventStateMapper.selectByExample(example);
		return list;
	}

	@Override
	public T_event_state selectEventStateByOne(Integer stateid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		Example example = new Example(T_event_state.class);
		example.selectProperties("id").selectProperties("state_name").selectProperties("module_id")
				.selectProperties("addtime").selectProperties("type").createCriteria().andEqualTo("module_id", moduleid)
				.andEqualTo("isdelete", 0).andEqualTo("id", stateid);
		List<T_event_state> list = eventStateMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	public Integer updateEventStateByOne(T_event_state eventAlarm) throws MyException {
		// TODO Auto-generated method stub

		Example example = new Example(T_event_state.class);
		example.createCriteria().andEqualTo("state_name", eventAlarm.getState_name())
				.andEqualTo("module_id", eventAlarm.getModule_id()).andNotEqualTo("id", eventAlarm.getId())
				.andNotEqualTo("isdelete", 1);
		Integer result = eventStateMapper.selectCountByExample(example);
		System.out.println(eventStateMapper.selectByExample(example).toString());
		if (result >= 1) {
			getresult(0, "状态名有重复");
		}
		Integer i = eventStateMapper.updateByPrimaryKeySelective(eventAlarm);
		return getresult(i, "修改失败");
	}

	@Override
	public Integer deleteEventStateByOne(Integer stateid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub

		Integer sum = eventStateMapper.checkState(stateid) <= 0 ? 1 : 0;
		getresult(sum, "该状态有事件在使用,无法删除");
		T_event_state alarm = new T_event_state();
		alarm.setId(stateid);
		alarm.setModule_id(moduleid);
		alarm.setIsdelete(1);
		Integer i = eventStateMapper.updateByPrimaryKeySelective(alarm);
		return getresult(i, "删除事件状态失败");
	}

	@Override
	public Integer insertEventStateOne(T_event_state eventAlarm) throws MyException {
		// TODO Auto-generated method stub
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Example example = new Example(T_event_state.class);
		example.createCriteria().andEqualTo("state_name", eventAlarm.getState_name())
				.andEqualTo("module_id", eventAlarm.getModule_id()).andNotEqualTo("isdelete", 1);
		Integer result = eventStateMapper.selectCountByExample(example);
		System.out.println(eventStateMapper.selectByExample(example).toString());
		if (result >= 1) {
			getresult(0, "状态名有重复");
		}
		eventAlarm.setAddtime(sdf.format(d));
		eventAlarm.setIsdelete(0);
		Integer i = eventStateMapper.insertSelective(eventAlarm);
		return getresult(i, "添加事件状态失败");
	}

	/**
	 * 判断返回结果是否成功
	 * 
	 * @param i
	 *            返回的结果情况
	 * @param msg
	 *            失败需返回的信息
	 * @return
	 * @throws MyException
	 */
	private Integer getresult(Integer i, String msg) throws MyException {
		if (i <= 0) {
			throw new MyException("0", msg);
		} else {
			return i;
		}

	}

	@Override
	public Integer insertOneEvent(Integer userid, EventParam eventParam, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		if(deviceService.selectAppDeviceForDevnum(eventParam.getDevnum()).getModuleid()!=moduleid){
			getresult(0, "该设备并不是该类型的");
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer byDevid = eventMapper.selectEventCountByDevid(moduleid, eventParam.getDevid());
		if (byDevid > 0) {
			getresult(0, "该设备已上报,请勿重复添加");
		}
		T_hk_event event = new T_hk_event();
		event.setAdduserid(userid);
		event.setDescribe(eventParam.getDescribe());
		event.setLevel(eventParam.getLevel());
		event.setInstructions(eventParam.getInstructions());
		event.setIsdispatch(eventParam.getIsdispatch());
		event.setModuleid(moduleid);
		event.setAddtime(sdf.format(d));
		if (eventParam.getIsdispatch() == 0) {
			event.setState(3);
			eventMapper.updateDeviceState(moduleid, 0, eventParam.getDevid());
		} else {
			event.setState(0);
			Integer type = eventStateMapper.selectByPrimaryKey(eventParam.getDescribe()).getType();
			eventMapper.updateDeviceState(moduleid, type, eventParam.getDevid());
		}
		getresult(eventMapper.insertSelective(event), "添加失败");
		event.setCode("0000" + event.getId());
		getresult(eventMapper.updateByPrimaryKeySelective(event), "修改事件编号失败");
		T_hk_evdevrelation evdevrelation = new T_hk_evdevrelation();
		evdevrelation.setDevid(eventParam.getDevid());
		evdevrelation.setEventid(event.getId());
		getresult(devRelationMapper.insertSelective(evdevrelation), "添加关联设备失败");
		if (eventParam.getPictureUrl() != null) {
			for (String pictureUrl : eventParam.getPictureUrl()) {
				T_hk_evresrelation evresrelation = new T_hk_evresrelation();
				evresrelation.setModuleid(moduleid);
				evresrelation.setEventid(event.getId());
				evresrelation.setResourcestype(1);
				evresrelation.setType(1);
				evresrelation.setUrl(pictureUrl);
				int insertSelective = eventResRelationMapper.insertSelective(evresrelation);
				getresult(insertSelective, "添加图片资源失败");
			}
		}
		if (eventParam.getVoiceUrl() != null) {
			for (String voiceUrl : eventParam.getVoiceUrl()) {
				T_hk_evresrelation evresrelation = new T_hk_evresrelation();
				evresrelation.setModuleid(moduleid);
				evresrelation.setEventid(event.getId());
				evresrelation.setResourcestype(2);
				evresrelation.setType(1);
				evresrelation.setUrl(voiceUrl);
				int insertSelective = eventResRelationMapper.insertSelective(evresrelation);
				getresult(insertSelective, "添加图片资源失败");
			}
		}
		return 1;
	}

	@Override
	public AppEventInfo selectAPPEventInfo(Integer moduleid, Integer eventid) throws MyException {
		// TODO Auto-generated method stub
		AppEventInfo appEventInfo = null;
		if (eventMapper.getishander(eventid, moduleid) != 0) {
			appEventInfo = eventMapper.selectAPPEventInfoToWo(moduleid, eventid);
		} else {
			appEventInfo = eventMapper.selectAPPEventInfo(moduleid, eventid);
		}
		return appEventInfo;
	}

	@Override
	public Integer closeEvent(Integer eventid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		return eventMapper.closeEvent(eventid, moduleid);
	}

	@Override
	public List<T_module> selectAPPModule() throws MyException {
		// TODO Auto-generated method stub
		List<T_module> list = eventMapper.selectAPPModule();
		T_module module0 = new T_module();
		module0.setModuleid(0);
		module0.setModulename("全部");
		list.add(0, module0);
		return list;
	}

	@Override
	public List<AppEventList> selectAppEventList(Integer moduleid, Integer timeout, String message) throws MyException {
		// TODO Auto-generated method stub
		eventMapper.dropTemporaryTable();
		eventMapper.createTemporaryTable();
		return eventMapper.getAppEventList(moduleid, timeout, message);
	}

	@Override
	public Integer selectEventCount(Integer userid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		Integer selectEventCount = eventMapper.selectEventCount(userid, moduleid);
		return selectEventCount;
	}

	@Override
	public List<EventListVo> selectEventListMaps(Integer userid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		List<EventListVo> selectEventListMaps = eventMapper.selectEventListMaps(userid, moduleid);
		if (selectEventListMaps.size() == 0) {
			selectEventListMaps = null;
		}
		return selectEventListMaps;
	}
}