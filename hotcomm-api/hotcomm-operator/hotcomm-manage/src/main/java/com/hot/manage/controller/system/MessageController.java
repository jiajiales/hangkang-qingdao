package com.hot.manage.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TMessageLogService;
import com.hot.manage.utils.ApiResult;

@RestController
public class MessageController {

	@Autowired
	private TMessageLogService messageLogService;

	@PostMapping("/message/selectByReceiverId")
	@Permissions("message:selectByReceiverId:query")
	public ApiResult selectByReceiverId(PageParam param) throws MyException {
		PageInfo<TMessageLog> selectByReceiverId = messageLogService.selectByReceiverId(param);
		return ApiResult.resultInfo("1", "成功", selectByReceiverId);
	}

	@PostMapping("/message/selectByUserId")
	@Permissions("message:selectByUserId:query")
	public ApiResult selectByUserId(PageParam param) throws MyException {
		PageInfo<TMessageLog> selectByUserId = messageLogService.selectByUserId(param);
		return ApiResult.resultInfo("1", "成功", selectByUserId);
	}

}
