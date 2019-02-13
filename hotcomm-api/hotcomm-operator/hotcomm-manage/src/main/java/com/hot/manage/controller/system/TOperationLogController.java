package com.hot.manage.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.system.OperationLogPageParam;
import com.hot.manage.entity.system.TOperationLogVo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TOperationLogService;
import com.hot.manage.utils.ApiResult;

@RestController
public class TOperationLogController {
	@Autowired
	private TOperationLogService operationLogService;

	@PostMapping("/operationLog/selectAllPageInfo")
	@Permissions("operationLog:selectAllPageInfo:query")
	// ("查询当前用户能看到的所有操作日志")
	public ApiResult selectAllPageInfo(OperationLogPageParam param) throws MyException {
		PageInfo<TOperationLogVo> selectPageInfo = operationLogService.selectPageInfo(param);
		return ApiResult.resultInfo("1", "成功", selectPageInfo);
	}

	@PostMapping("/operationLog/selectPageByUserId")
	@Permissions("operationLog:selectPageByUserId:query")
	// ("查询当前用户的操作日志")
	public ApiResult selectPageByUserId(OperationLogPageParam param) throws MyException {
		PageInfo<TOperationLogVo> selectByUserId = operationLogService.selectByUserId(param);
		return ApiResult.resultInfo("1", "成功", selectByUserId);
	}
}
