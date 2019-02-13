package com.hotcomm.data.view.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.view.controller.BaseView;
import com.hotcomm.data.view.utils.HttpUtils;

@RestController
public class ResourceViewClient extends BaseView {

	private final static String LIST_REST_URL = "/sys/resource/list";
	private final static String GET_REST_URL = "/sys/resource/get";
	private final static String ADD_REST_URL = "/sys/resource/add";
	private final static String UPATE_REST_URL = "/sys/resource/update";
	private final static String DEL_REST_URL = "/sys/resource/del";
	private final static String MENUS_REST_URL = "/sys/resource/menus";

	@PostMapping(MENUS_REST_URL)
	public Object menus(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(MENUS_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

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

	@PostMapping(LIST_REST_URL)
	public Object list(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(LIST_REST_URL), getparams(request));
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
