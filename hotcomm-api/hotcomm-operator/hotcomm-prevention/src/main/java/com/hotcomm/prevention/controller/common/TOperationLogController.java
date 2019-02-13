package com.hotcomm.prevention.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.params.OperationLogPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TOperationLogVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.OperationLogService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class TOperationLogController {
	@Autowired
	OperationLogService operationLogService;
	
	/**
	 * 系统设置->操作日志管理->操作日志分页
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/operationLog/selectAllPageInfo")
	public ApiResult selectAllPageInfo(OperationLogPageParam param) throws MyException {
		PageInfo<TOperationLogVo> selectPageInfo = operationLogService.selectPageInfo(param);
		return ApiResult.resultInfo("1", "成功", selectPageInfo);
	}

}
