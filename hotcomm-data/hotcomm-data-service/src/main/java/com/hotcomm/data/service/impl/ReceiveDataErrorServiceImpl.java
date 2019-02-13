package com.hotcomm.data.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.service.ReceiveDataError;
import com.hotcomm.data.db.ReceiveDataErrorMapper;
import com.hotcomm.data.service.ReceiveDataErrorService;
import com.hotcomm.data.utils.LogUtil;

@Service
@Transactional
public class ReceiveDataErrorServiceImpl implements ReceiveDataErrorService {

	@Autowired
	private ReceiveDataErrorMapper errorMapper;

	@Override
	public void addErrorData(String data, Exception exception, String queue) {
		Logger log = LogUtil.SERVICE_LOG;
		Logger errLog = LogUtil.ERROR_LOG;
		try {
			ReceiveDataError error = new ReceiveDataError();
			error.setReceiveData(data);
			error.setQueue(queue);
			String errMsg = Arrays.toString(exception.getStackTrace());
			error.setErrprMsg("message:" + exception.getMessage() + " case :" + errMsg);
			error.setCreateTime(new Date());
			errorMapper.insertSelective(error);
			log.info("接收队列-{} 异常数据,存储亦或分发出现异常,将数据存储至异常接收数据表,数据内容:{}", queue, data);
		} catch (Exception e) {
			e.printStackTrace();
			String nid = UUID.randomUUID().toString();
			errLog.info("异常编号{},存储 队列-{} 异常数据,执行数据记录失败,相关参数-data:{},exception:{},queue:{}", nid, queue, data, exception, queue);
			errLog.error("异常编号" + nid, e);
		}
	}

}
