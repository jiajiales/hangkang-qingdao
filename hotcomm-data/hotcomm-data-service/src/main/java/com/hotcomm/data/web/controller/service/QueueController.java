package com.hotcomm.data.web.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.service.queue.QueuePageParams;
import com.hotcomm.data.bean.params.service.queue.QueueParams;
import com.hotcomm.data.bean.vo.queue.QueuePageVO;
import com.hotcomm.data.bean.vo.queue.QueueVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.QueueService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class QueueController extends BaseController implements CrudController<QueueParams, Integer, QueueVO>, 
	PageController<QueuePageParams, QueuePageVO> {

	@Autowired
	private QueueService queueService;

	@Function(key = "queue-get")
	@RequestMapping("/queue/get")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01") })
	@LogEvent(code = "QUEUE_GET")
	@Override
	public ApiResult get(Integer queueId) throws HKException {
		return ApiResult.success(queueService.getBean(queueId));
	}

	@Function(key = "queue-update")
	@RequestMapping("/queue/update")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01") })
	@LogEvent(code = "QUEUE_UPD")
	@Override
	public ApiResult update(QueueParams params) throws HKException {
		queueService.updateBean(params);
		return ApiResult.success();
	}

	@Override
	public ApiResult del(Integer id) throws HKException {
		return null;
	}

	@Override
	public ApiResult add(QueueParams params) throws HKException {
		// params.setCreateUser(getLoginMember().getUserName()); // 获取登入用户
		// queueService.addBean(params);
		// return ApiResult.success();
		return null;
	}

	@Function(key = "queue-page")
	@RequestMapping("/queue/page")
	@ParamsValidate(validateParams = { @Param(key = "page", code = "QUEUE_F05"), 
									   @Param(key = "rows", code = "QUEUE_F06") })
	@LogEvent(code = "QUEUE_GETPAG")
	@Override
	public PageInfo<QueuePageVO> page(QueuePageParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setLoginMemberId(member.getId());
		params.setLoginUserType(member.getUserType());
		return queueService.queryPage(params);
	}

	@Function(key = "queue-run")
	@RequestMapping("/queue/run")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01") })
	@LogEvent(code = "QUEUE_RUN")
	public ApiResult run(QueueParams params) throws HKException {
		queueService.queueRun(params.getQueueId());
		return ApiResult.success();
	}

	@Function(key = "queue-pause")
	@RequestMapping("/queue/pause")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01") })
	@LogEvent(code = "QUEUE_PAUSE")
	public ApiResult pause(QueueParams params) throws HKException {
		queueService.queuePause(params.getQueueId());
		return ApiResult.success();
	}

	@Function(key = "queue-rename")
	@RequestMapping("/queue/rename")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01"), 
									   @Param(key = "queueName", code = "QUEUE_F02") })
	@LogEvent(code = "QUEUE_RENAME")
	public ApiResult queueRename(QueueParams params) throws HKException {
		queueService.queueRename(params);
		return ApiResult.success();
	}

	@Function(key = "queue-update-holeTime")
	@RequestMapping("/queue/update/holeTime")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01"), 
									   @Param(key = "holeTime", code = "QUEUE_F03") })
	@LogEvent(code = "QUEUE_UPD_HOLETIME")
	public ApiResult updateHoleTime(QueueParams params) throws HKException {
		queueService.updateHoleTime(params);
		return ApiResult.success();
	}

	@Function(key = "queue-update-filternums")
	@RequestMapping("/queue/update/filternums")
	@ParamsValidate(validateParams = { @Param(key = "queueId", code = "QUEUE_F01"), 
									   @Param(key = "sendFilterInterval", code = "QUEUE_F04") })
	@LogEvent(code = "QUEUE_UPD_FILTERNUMS")
	public ApiResult updateFilternums(QueueParams params) throws HKException {
		queueService.updateFilternums(params);
		return ApiResult.success();
	}

	@Function(key = "queue-rabbit-view")
	@RequestMapping("/queue/rabbit/view")
	@ParamsValidate(validateParams = { @Param(key = "queueName", code = "QUEUE_F02") })
	@LogEvent(code = "QUEUE_RABBIT_VIEW")
	public ApiResult getQueueRabbitView(QueueParams params) throws HKException {
		return ApiResult.success(queueService.getQueueRabbitView(params.getQueueName()));
	}

	@Function(key = "queue-view")
	@RequestMapping("/queue/view")
	@ParamsValidate(validateParams = { @Param(key = "memberId", code = "QUEUE_F07") })
	@LogEvent(code = "QUEUE_VIEW")
	public ApiResult getQueueView(QueueParams params) throws HKException {
		return ApiResult.success(queueService.getQueueView(params));
	}

}
