package com.hotcomm.data.service;

import com.hotcomm.data.bean.entity.sys.Log;
import com.hotcomm.data.bean.params.sys.LogParams;
import com.hotcomm.data.service.common.PageService;

public interface LogService extends PageService<LogParams, Log> {

	void saveLog(Log log);

}
