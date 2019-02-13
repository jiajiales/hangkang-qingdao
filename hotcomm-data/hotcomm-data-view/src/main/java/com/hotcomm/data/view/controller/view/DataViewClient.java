package com.hotcomm.data.view.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.view.controller.BaseView;
import com.hotcomm.data.view.utils.HttpUtils;

@RestController
public class DataViewClient extends BaseView {

	private final static String PAGE_REST_URL = "/service/data/page";
	private final static String GETDATA_REST_URL = "/service/data/getData";

	@PostMapping(GETDATA_REST_URL)
	public Object getData(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(GETDATA_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

	@PostMapping(PAGE_REST_URL)
	public Object page(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(PAGE_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

}
