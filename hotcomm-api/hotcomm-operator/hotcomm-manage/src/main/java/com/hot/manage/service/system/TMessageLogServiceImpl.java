package com.hot.manage.service.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.system.TMessageLogMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Service
@Transactional
public class TMessageLogServiceImpl implements TMessageLogService {

	@Autowired
	private TMessageLogMapper messageLogMapper;

	@Override
	public Integer insertOne(TMessageLog message) throws MyException {
		message.setSendtime(ConverUtil.timeForString(new Date()));
		//message.setMessage(JSONUtil.toJson(message.getMessage()));
		return messageLogMapper.insertSelective(message);
	}

	@Override
	public void insertBath(List<TMessageLog> message) throws MyException {
		messageLogMapper.insertBacth(message);
	}
	
	@Override
	public PageInfo<TMessageLog> selectByReceiverId(PageParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TMessageLog> page = messageLogMapper.selectByReceiverId(param.getUserid());
		List<TMessageLog> list = ConverUtil.converPage(page, TMessageLog.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public PageInfo<TMessageLog> selectByUserId(PageParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TMessageLog> page = messageLogMapper.selectByUserid(param.getUserid());
		List<TMessageLog> list = ConverUtil.converPage(page, TMessageLog.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer update(TMessageLog message) throws MyException {
		return messageLogMapper.update(message);
	}

	@Override
	public List<TMessageLog> selectUnsent(TMessageLog message) throws MyException {
		return messageLogMapper.selectUnsent(message.getReceiverid());
	}

}
