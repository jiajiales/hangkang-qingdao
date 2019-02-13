package com.hotcomm.data.view.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.view.controller.BaseView;
import com.hotcomm.data.view.utils.HttpUtils;

@RestController
public class RoleViewClient extends BaseView {

	private final static String LIST_REST_URL = "/sys/role/list";
	private final static String GET_REST_URL = "/sys/role/get";
	private final static String ADD_REST_URL = "/sys/role/add";
	private final static String UPATE_REST_URL = "/sys/role/update";
	private final static String DEL_REST_URL = "/sys/role/del";

	private final static String GET_ROLE_RESOURCE_REST_URL = "/sys/role/getRoleResource";
	private final static String ADD_ROLE_RESOURCE_REST_URL = "/sys/role/addRoleResource";
	private final static String CHECK_ROLE_RESOURCE_REST_URL = "/sys/role/chexisRole";

	@PostMapping(CHECK_ROLE_RESOURCE_REST_URL)
	public Object chexisRole(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(CHECK_ROLE_RESOURCE_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

	@PostMapping(ADD_ROLE_RESOURCE_REST_URL)
	public Object addRoleResource(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(ADD_ROLE_RESOURCE_REST_URL), getparams(request));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorMap();
		}
	}

	@PostMapping(GET_ROLE_RESOURCE_REST_URL)
	public Object getRoleResource(HttpServletRequest request) {
		try {
			String result = HttpUtils.collectApiData("POST", joinRestUrl(GET_ROLE_RESOURCE_REST_URL), getparams(request));
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
