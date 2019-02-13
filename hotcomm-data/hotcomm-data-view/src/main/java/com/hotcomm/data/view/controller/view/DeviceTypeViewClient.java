package com.hotcomm.data.view.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.view.controller.BaseView;
import com.hotcomm.data.view.utils.HttpUtils;

@RestController
public class DeviceTypeViewClient extends BaseView {

	private final static String PAGE_REST_URL = "/device/type/page";
	private final static String DEL_REST_URL = "/device/type/del";
	private final static String ADD_REST_URL = "/device/type/add";
	private final static String GET_REST_URL = "/device/type/get";
	private final static String UPATE_REST_URL = "/device/type/update";

	@PostMapping(ADD_REST_URL)
	public Object add(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(ADD_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

	@PostMapping(DEL_REST_URL)
	public Object del(HttpServletRequest request) {
		try {
			return HttpUtils.collectApiData("POST", joinRestUrl(DEL_REST_URL), getparams(request));
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

	@PostMapping(GET_REST_URL)
	public Object get(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(GET_REST_URL), getparams(request));
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

	@PostMapping(UPATE_REST_URL)
	public Object update(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(UPATE_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

}
