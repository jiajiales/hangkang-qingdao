package com.hotcomm.data.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.sys.Log;
import com.hotcomm.data.bean.params.sys.LogParams;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.LogMapper;
import com.hotcomm.data.service.LogService;
import com.hotcomm.data.service.common.AbstractFunServiceImpl;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class LogServiceImpl extends AbstractFunServiceImpl<Log, Log> implements LogService {

	@Autowired
	LogMapper logMapper;

	@Override
	public PageInfo<Log> queryPage(LogParams params) throws HKException {
		PageHelper.startPage(params.getPage(), params.getRows());
//		// 将params.getEndTime()增加一天
//		if (params.getEndTime() != null && params.getEndTime() != "") {
//			int EndTimeStr = Integer.parseInt(params.getEndTime().substring(params.getEndTime().length() - 1, params.getEndTime().length()));
//			EndTimeStr++;
//			params.setEndTime(params.getEndTime().substring(0, params.getEndTime().length() - 1) + EndTimeStr);
//		}
		Page<Log> pageList = logMapper.queryLogInPage(params.getStartTime(), params.getEndTime(), params.getRecordUser());
		List<Log> logList = converPage(pageList, Log.class);
		PageInfo<Log> info = new PageInfo<>(pageList.getTotal(), logList);
		return info;
	}

	@Override
	@Transactional
	public void saveLog(Log log) {
		try {
			log.setRecordTime(new Date());
			logMapper.insertSelective(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
