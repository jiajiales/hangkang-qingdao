package com.hot.manage.service.imp.yg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.yg.YGDeviceInfoMapper;
import com.hot.manage.db.yg.YGEventParticMapper;
import com.hot.manage.entity.yg.param.TAlarmDispose;
import com.hot.manage.entity.yg.param.YGEvent;
import com.hot.manage.entity.yg.param.YGEventParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGAppService;

@Service
@Transactional
public class YGAppServiceImpl implements YGAppService {

	@Autowired
	private YGEventParticMapper ygEventParticMapper;
	@Autowired
	private YGDeviceInfoMapper ygDeviceInfoMapper;

	@Override
	public Integer insertEvent(YGEventParam ygEventParam) throws MyException {
		// TODO Auto-generated method stub.
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		YGEvent ygevent = new YGEvent();
		ygevent.setYgeventParam(ygEventParam);
		ygevent.setAddtime(sdf.format(time));
		ygevent.setState(ygEventParam.getIsdispatch() == 0 ? 3 : 0);
		Integer a = ygEventParticMapper.insertOneEvent(ygevent);
		ygevent.setCode("0000" + ygevent.getId());
		Integer b = ygEventParticMapper.updateEventCode(ygevent.getId(), ygevent.getCode());
		Integer c = ygEventParticMapper.insertEventDevRel(ygevent.getId(), ygevent.getYgeventParam().getDevid());
		int d = 0, e = 0;
		if (ygevent.getYgeventParam().getPictureUrl() != null) {
			for (String pictureUrl : ygevent.getYgeventParam().getPictureUrl()) {
				d = ygDeviceInfoMapper.insertDevResource(ygevent.getId(), 1, 1, pictureUrl);
			}
		} else {
			d = 1;
		}
		if (ygevent.getYgeventParam().getVoiceUrl() != null) {
			for (String voiceUrl : ygevent.getYgeventParam().getVoiceUrl()) {
				e = ygDeviceInfoMapper.insertDevResource(ygevent.getId(), 2, 1, voiceUrl);
			}
		} else {
			e = 1;
		}

		return a <= 0 && b <= 0 && c <= 0 && d <= 0 && e <= 0 ? 0 : 1;
	}

	@Override
	public Integer DevInfoDispose(TAlarmDispose talarmDispose) throws MyException {
		int handlestate = talarmDispose.getIsdispatch() == 0 ? 2 : 1;

		Integer param1 = ygDeviceInfoMapper.updateDevInfo(talarmDispose.getAlarmstateid(),
				talarmDispose.getIsdispatch(), talarmDispose.getRemark(), talarmDispose.getYgalarmid(), handlestate,talarmDispose.getId());
		if (talarmDispose.getPictureUrl() != null) {
			for (String PictureUrl : talarmDispose.getPictureUrl()) {
				Integer param2 = ygDeviceInfoMapper.insertDevResource(talarmDispose.getYgalarmid(), 1, 2, PictureUrl);
				if (param2 <= 0) {
					return null;
				}
			}
		}
		if (param1 <= 0) {
			return null;
		}
		return 1;
	}

}
