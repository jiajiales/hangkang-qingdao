package com.hotcomm.data.view.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.view.controller.BaseView;
import com.hotcomm.data.view.utils.HttpUtils;

@RestController
public class LogViewClient extends BaseView {
	
	private final static String PAGE_REST_URL = "/sys/log/queryPage";

	@PostMapping(PAGE_REST_URL)
	public Object getData(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(PAGE_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}
}
