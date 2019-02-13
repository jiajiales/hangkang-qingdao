package com.hotcomm.prevention.service.manage.alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AlarmInfo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AlarmState;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AppAlarmList;
import com.hotcomm.prevention.bean.mysql.manage.alarm.ModuleAlarmCount;
import com.hotcomm.prevention.bean.mysql.manage.alarm.T_dev_alarm;
import com.hotcomm.prevention.bean.mysql.manage.alarm.T_hk_evresrelation;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceFinishVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceHandingVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceInfoVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmListVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AppAlarmFinish;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.HistoricalDateVO;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.HistoricalStateDateVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.PictureUrlVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.VoiceUrlVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmHandleParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmListParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmStateParam;
import com.hotcomm.prevention.db.mysql.manage.alarm.AlarmMapper;
import com.hotcomm.prevention.db.mysql.manage.alarm.StateMapper;
import com.hotcomm.prevention.db.mysql.manage.event.EventMapper;
import com.hotcomm.prevention.db.mysql.manage.event.EventResRelationMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.CloneTools;
import com.hotcomm.prevention.utils.PushUtil;
import com.hotcomm.prevention.websocket.MyWebSocket;

import tk.mybatis.mapper.entity.Example;
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmMapper alarmMapper;
	
	@Autowired
	private StateMapper stateMapper;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private EventResRelationMapper eventResRelationMapper;

	
	/**
	 * 判断是否成功
	 * 
	 * @param i
	 * @param msg
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
	public Page<AlarmListVo> selectAlarmList(Integer pageNum, Integer pageSize, Integer userid, Integer moduleid,
			AlarmListParam alarmListParam) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<AlarmListVo> list = alarmMapper.selectAlarmList(alarmListParam, userid, moduleid);
		if (list.getTotal() != 0) {
			return list;
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public Integer closealarmById(Integer alarmid, Integer moduleid) throws MyException {
		Date handleTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       
		return getresult(alarmMapper.closealarmById(alarmid, moduleid, sdf.format(handleTime)), "操作失败");
	}

	@Override
	public List<AlarmListVo> selectAlarmMaps(Integer userid, Integer moduleid) throws MyException {
	
		
		return alarmMapper.selectAlarmMaps(userid, moduleid);
	}

	@Override
	public List<HistoricalDateVO> selectHistoricalDate(Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
	
		return alarmMapper.selectHistoricalDate(deviceid, Theyear, moduleid);

	}

	@Override
	public List<HistoricalStateDateVo> selectHistoricalStateDate(Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
		
		return alarmMapper.selectHistoricalStateDate(deviceid, Theyear, moduleid);

	}

	@Override
	public AlarmDeviceInfoVo selectByAlarmid(Integer alarmid, Integer moduleid) throws MyException {
		return alarmMapper.selectByAlarmid(alarmid, moduleid);

	}

	@Override
	public AlarmDeviceHandingVo selectAlarmDevicHanding(Integer alarmid, Integer moduleid) throws MyException {
		List<VoiceUrlVo> voicelist = alarmMapper.getVoice(alarmid, moduleid);
		List<PictureUrlVo> picturelist = alarmMapper.getpicture(alarmid, moduleid);
		AlarmDeviceHandingVo info = alarmMapper.selectAlarmDevicHanding(alarmid, moduleid);
		if (voicelist.size() != 0) {
			info.setVoiceUrl(voicelist);
		}
		if (picturelist.size() != 0) {
			info.setPictureUrl(picturelist);
		}
		return info;
	}
	

	@Override
	public AlarmDeviceFinishVo selectAlarmDeviceFinish(Integer alarmid, Integer moduleid) throws MyException {
		
		return alarmMapper.selectAlarmDeviceFinish(alarmid, moduleid);
	}

	@Override
	public List<AlarmState> selectAllState(Integer moduleid) throws MyException {
		Example example = new Example(AlarmState.class);
		example.selectProperties("id").selectProperties("state_name").selectProperties("module_id")
				.selectProperties("level").selectProperties("addtime").selectProperties("type").createCriteria()
				.andEqualTo("module_id", moduleid).andEqualTo("isdelete", 0);
		example.orderBy("addtime").desc();
		List<AlarmState> list = stateMapper.selectByExample(example);
		return list;
	}

	@Override
	public AlarmState selectStateOneByid(Integer stateid, Integer moduleid) throws MyException {
		Example example = new Example(AlarmState.class);
		example.selectProperties("id").selectProperties("state_name").selectProperties("module_id")
				.selectProperties("level").selectProperties("addtime").selectProperties("type").createCriteria()
				.andEqualTo("module_id", moduleid).andEqualTo("isdelete", 0).andEqualTo("id", stateid);
		example.orderBy("addtime").desc();
	
		List<AlarmState> selectByExample = stateMapper.selectByExample(example);
	
		return selectByExample.get(0);
	}

	@Override
	public Integer updateStateOneByid(AlarmStateParam alarmStateParam) throws MyException {
		Example example = new Example(AlarmState.class);
		example.createCriteria().andEqualTo("state_name", alarmStateParam.getState_name())
				.andEqualTo("module_id", alarmStateParam.getModule_id()).andNotEqualTo("id", alarmStateParam.getId())
				.andNotEqualTo("isdelete", 1);
		Integer result = stateMapper.selectCountByExample(example);
		if (result >= 1) {
			getresult(0, "状态名有重复");
		}

		AlarmState alarmState = CloneTools.cloneObj(alarmStateParam, AlarmState.class);
		Integer i = stateMapper.updateByPrimaryKeySelective(alarmState);
		return getresult(i, "修改失败");
	}

	@Override
	public Integer deleteStateOneByid(Integer stateid, Integer moduleid) throws MyException {
		Integer sum = alarmMapper.checkState(stateid) <= 0 ? 1 : 0;// 如果有报警在使用,则不给删除
		getresult(sum, "有使用此状态的报警,无法删除");
		AlarmState alarmState = new AlarmState();
		alarmState.setId(stateid);
		alarmState.setModule_id(moduleid);
		alarmState.setIsdelete(1);
		Integer i = stateMapper.updateByPrimaryKeySelective(alarmState);
		return getresult(i, "删除失败");
	}

	@Override
	public Integer insertStateOne(AlarmStateParam alarmStateParam) throws MyException {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Example example = new Example(AlarmState.class);
		example.createCriteria().andEqualTo("state_name", alarmStateParam.getState_name())
				.andEqualTo("module_id", alarmStateParam.getModule_id()).andNotEqualTo("isdelete", 1);
		Integer result = stateMapper.selectCountByExample(example);
		if (result >= 1) {
			getresult(0, "状态名有重复");
		}
		alarmStateParam.setAddtime(sdf.format(d));
		AlarmState alarmState = CloneTools.cloneObj(alarmStateParam, AlarmState.class);
		alarmState.setIsdelete(0);
		Integer i = stateMapper.insertSelective(alarmState);
		return getresult(i, "添加失败");
	}

	@Override
	public AppAlarmFinish selectAppAlarmFinish(Integer alarmid, Integer moduleid) throws MyException {
		alarmMapper.getishandler(alarmid, moduleid);
		if (alarmMapper.getishandler(alarmid, moduleid) != 0) {
			AppAlarmFinish alarmFinishToWo = alarmMapper.selectAppAlarmFinishToWo(alarmid, moduleid);
			return alarmFinishToWo;
		} else {
			AppAlarmFinish alarmFinishToWo = alarmMapper.selectAppAlarmFinish(alarmid, moduleid);
			return alarmFinishToWo;
		}
	}

	@Override
	public Integer APPAlarmHandle(Integer userid, Integer moduleid, AlarmHandleParam alarmHandleParam)
			throws MyException {
		T_dev_alarm selectByPrimaryKey = alarmMapper.selectByPrimaryKey(alarmHandleParam.getAlarmid());
		if(selectByPrimaryKey.getIsdispatch()!=null){
			getresult(0, "该报警已处理,请勿重复操作");
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		T_dev_alarm alarm = new T_dev_alarm();
		alarm.setModuleid(moduleid);
		alarm.setId(alarmHandleParam.getAlarmid());
		alarm.setHandleresult(alarmHandleParam.getHandleresult());
		alarm.setRemark(alarmHandleParam.getRemark());
		alarm.setIsdispatch(alarmHandleParam.getIsdispatch());
		if (alarmHandleParam.getIsdispatch() != 1) {// 不需要派工
			alarm.setHandlestate(2);// 更改为已处理
			alarm.setHandleTime(sdf.format(d));// 加入处理时间
			alarm.setHandler(userid);// 修改处理人
			getresult(alarmMapper.updateByPrimaryKeySelective(alarm), "提交失败");// 进行修改报警情况
			Integer result = eventMapper.updateDeviceState(moduleid, 0,
					alarmMapper.selectByPrimaryKey(alarmHandleParam.getAlarmid()).getDeviceid());// 修改设备状态
			getresult(result, "提交失败");
		} else {// 需要派工
			getresult(alarmMapper.updateByPrimaryKeySelective(alarm), "提交失败");// 进行修改报警情况
			Integer result = eventMapper.updateDeviceState(moduleid,
					stateMapper.selectByPrimaryKey(alarmHandleParam.getHandleresult()).getType(),
					alarmMapper.selectByPrimaryKey(alarmHandleParam.getAlarmid()).getDeviceid());// 修改设备状态
			getresult(result, "提交失败");
		}
		if (alarmHandleParam.getPictureUrl() != null && alarmHandleParam.getPictureUrl() != "") {// 如果有传入图片
			String pictureUrl[] = alarmHandleParam.getPictureUrl().split(",");// 分割每张图片
			for (String url : pictureUrl) {
				T_hk_evresrelation record = new T_hk_evresrelation();
				record.setModuleid(moduleid);
				record.setResourcestype(1);
				record.setType(2);
				record.setEventid(alarmHandleParam.getAlarmid());
				record.setUrl(url);
				getresult(eventResRelationMapper.insertSelective(record), "图片提交失败");
			}
		}
		if (alarmHandleParam.getVoiceUrl() != null && alarmHandleParam.getVoiceUrl() != "") {
			String pictureUrl[] = alarmHandleParam.getVoiceUrl().split(",");
			for (String string : pictureUrl) {
				T_hk_evresrelation record = new T_hk_evresrelation();
				record.setModuleid(moduleid);
				record.setResourcestype(2);
				record.setType(2);
				record.setEventid(alarmHandleParam.getAlarmid());
				record.setUrl(string);
				getresult(eventResRelationMapper.insertSelective(record), "图片提交失败");
			}
		}
//		PushUtil.sendAllsetNotification("可以刷新未处理列表了", "1","1", "", 1);
		return 1;
	}

	@Override
	public List<AppAlarmList> selectAppAlarmList(Integer userid, Integer moduleid, Integer state, Integer timeout,
			String message) throws MyException {
		List<AppAlarmList> appAlarmList = alarmMapper.getAppAlarmList(userid, moduleid, state, timeout, message);
		return appAlarmList;
	}

	@Override
	public Integer selectAlarmCount(Integer userid, Integer moduleid) throws MyException {
		
		return alarmMapper.selectAlarmCount(userid,moduleid);
	}

	@Override
	public List<ModuleAlarmCount> queryAlarmCount(Integer userid,Integer fatherid) {
		
		return alarmMapper.queryAlarmCount(userid,fatherid);

	}
	/**
	 * app轮询当前巡检未处理报警信息
	 */
	@Override
	public List<AlarmDeviceInfoVo> queryUnhandleAlarm(Integer userid) throws MyException {
		List<AlarmDeviceInfoVo> list = new ArrayList<>();
		List<AlarmInfo> queryUnhandleAlarm = alarmMapper.queryUnhandleAlarm(userid);
		if (queryUnhandleAlarm.size() != 0 || queryUnhandleAlarm != null) {
			for (AlarmInfo alarmInfo : queryUnhandleAlarm) {
				AlarmDeviceInfoVo queryDevByAlarm = alarmMapper.queryDevByAlarm(alarmInfo.getDevid(),
						alarmInfo.getModuleid());
				BeanUtils.copyProperties(alarmInfo, queryDevByAlarm);
				list.add(queryDevByAlarm);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> appIndexInfoAboutAlarmAndEvent(Integer userid) throws MyException, ParseException {
		
		Map<String, Object> test=alarmMapper.appIndexInfoAboutAlarmAndEvent(userid);
		
		String res=test.get("newAlarmTime").toString().substring(0, test.get("newAlarmTime").toString().length()-2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(res);
        long ts = date.getTime();
        res = String.valueOf(ts);
		
		
		test.put("newAlarmTime", res.substring(0, res.length()-3));
		System.out.println(test);
		return test;
	}	

}
