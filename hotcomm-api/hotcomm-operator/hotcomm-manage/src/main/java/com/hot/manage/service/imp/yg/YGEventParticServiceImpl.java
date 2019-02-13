package com.hot.manage.service.imp.yg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.yg.YGEventParticMapper;
import com.hot.manage.entity.yg.vo.YGEventPartic;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGEventParticService;

@Service
@Transactional
public class YGEventParticServiceImpl implements YGEventParticService {

	@Autowired
	private YGEventParticMapper ygEventParticMapper;

	@Override
	public Page<YGEventPartic> selectAllEventParticForTime(Integer userid, String startTime, String endTime,
			String message) throws MyException {
		// TODO Auto-generated method stub
		if (startTime != null && startTime.equals("")) {
			startTime = null;
		}
		if (endTime != null && endTime.equals("")) {
			endTime = null;
		}
		Page<YGEventPartic> list = ygEventParticMapper.selectAllEventParticForTime(userid, startTime, endTime, message);
		return list;
	}

	@Override
	public Page<YGEventPartic> selectAllEventParticForState(Integer userid, Integer eventstateid, Integer state)
			throws MyException {
		// TODO Auto-generated method stub
		Page<YGEventPartic> list = ygEventParticMapper.selectAllEventParticForState(userid, eventstateid, state);
		return list;
	}

	@Override
	public Page<YGEventPartic> selectAllEventParticForMas(String startTime, String endTime, String message,
			Integer userid, Integer eventstateid, Integer state) throws MyException {
		// TODO Auto-generated method stub
				if (startTime != null && startTime.equals("")) {
					startTime = null;
				}
				if (endTime != null && endTime.equals("")) {
					endTime = null;
				}
				Page<YGEventPartic> list = ygEventParticMapper.selectAllEventParticForMas(userid, startTime, endTime, message,eventstateid, state);
				return list;
	}

}
