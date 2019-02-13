package com.hotcomm.data.service;

import com.hotcomm.data.bean.params.service.data.DataPageParams;
import com.hotcomm.data.bean.vo.data.DataVO;
import com.hotcomm.data.bean.vo.data.ReceiveDataVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.model.rabbitmq.receive.TransportData;
import com.hotcomm.framework.web.exception.HKException;

public interface DataService {

	ReceiveDataVO getData(Long dataId) throws HKException;

	void pushSuccess(String code) throws HKException;

	void pushFail(String code) throws HKException;

	PageInfo<DataVO> queryPage(DataPageParams params);

	void saveData(TransportData data);

	void executePushWaitSendData(Integer queueId, int limit, long waitSendNums) throws HKException;

}
