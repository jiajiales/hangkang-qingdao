package com.hotcomm.data.web.controller.sys;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.entity.sys.Log;
import com.hotcomm.data.bean.params.sys.LogParams;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.LogService;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.web.exception.HKException;

@RestController
public class LogController implements PageController<LogParams, Log> {

	@Resource
	private LogService logService;

	@Function(key = "sys-log-queryPage")
	@RequestMapping("/sys/log/queryPage")
	public PageInfo<Log> page(LogParams params) throws HKException {
		return logService.queryPage(params);
	}

}
