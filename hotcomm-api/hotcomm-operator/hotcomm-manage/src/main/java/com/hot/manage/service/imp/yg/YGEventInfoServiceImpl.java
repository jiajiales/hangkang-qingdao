package com.hot.manage.service.imp.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.yg.YGEventInfoMapper;
import com.hot.manage.entity.yg.vo.YGEventDeviceRele;
import com.hot.manage.entity.yg.vo.YGEventFinish;
import com.hot.manage.entity.yg.vo.YGEventHandling;
import com.hot.manage.entity.yg.vo.YGEventInfo;
import com.hot.manage.entity.yg.vo.YGEventInstructRele;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGEventInfoService;

@Service
@Transactional
public class YGEventInfoServiceImpl implements YGEventInfoService {

	@Autowired
	private YGEventInfoMapper ygEventInfoMapper;

	@Override
	public YGEventInfo selectYGEventInfo(Integer eventid) throws MyException {
		// TODO Auto-generated method stub
		return ygEventInfoMapper.selectYGEventInfo(eventid);
	}

	@Override
	public List<YGEventDeviceRele> selectEventDevice(Integer eventid) throws MyException {
		// TODO Auto-generated method stub
		List<YGEventDeviceRele> list = ygEventInfoMapper.selectEventDevice(eventid);
		return list;
	}

	@Override
	public List<YGEventInstructRele> selectEventInstruct(Integer eventid) throws MyException {
		// TODO Auto-generated method stub
		List<YGEventInstructRele> list = ygEventInfoMapper.selectEventInstruct(eventid);
		return list;
	}

	@Override
	public YGEventHandling selectEventHandling(Integer eventid) throws MyException {

		return ygEventInfoMapper.selectEventHandling(eventid);
	}

	@Override
	public YGEventFinish selectEventFinish(Integer eventid) throws MyException {
		return ygEventInfoMapper.selectEventFinish(eventid);
	}

}
