package com.hotcomm.prevention.service.manage.message;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.PageParam;
import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;
import com.hotcomm.prevention.db.mysql.manage.message.TMessageLogMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;

@Service
@Transactional
public class TMessageLogServiceImpl implements TMessageLogService {

	@Autowired
	private TMessageLogMapper messageLogMapper;

	@Override
	public Integer insertOne(TMessageLog message) throws MyException {
		message.setSendtime(ConverUtil.dateForString(new Date()));
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
