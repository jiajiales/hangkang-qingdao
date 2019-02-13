package com.hot.manage.controller.common.AppPush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.entity.common.AppPush.T_hk_apppush;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.AppPush.AppPushService;
import com.hot.manage.utils.ApiResult;

@RestController
@RequestMapping("AppPush/AppPushController")
public class AppPushController {

	@Autowired
	private AppPushService appPushService;

	// 向所有用户推送消息
	@PostMapping("/sendAll")
	public ApiResult sendAll(String title, String message) throws MyException {
		return ApiResult.resultInfo("1", "成功", appPushService.sendAll(title, message));
	}

	// 更新用户regid信息
	@PostMapping("/setAppPush")
	public ApiResult setAppPush(T_hk_apppush t_hk_apppush) throws MyException {
		return ApiResult.resultInfo("1", "成功", appPushService.setAppPush(t_hk_apppush));
	}
}
