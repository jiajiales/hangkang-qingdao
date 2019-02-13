package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DeviceTypeView extends AuthView {

	public static final String DEVICETYPE_PAGE = "service/deviceType";
	public static final String DEVICETYPE_ADD_PAGE = "service/deviceType-add";
	public static final String DEVICETYPE_UPDATE_PAGE = "service/deviceType-update";
	public static final String DEVICETYPE_DELETE_PAGE = "service/deviceType-delete";
	public static final String VIEW_KEY = "service-deviceType";

	@GetMapping("/service/deviceType")
	public String deviceType(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return DEVICETYPE_PAGE;
	}

	@GetMapping("/service/deviceTypeAdd")
	public String deviceTypeAdd(Model model) {
		return DEVICETYPE_ADD_PAGE;
	}

	@GetMapping("/service/deviceTypeUpdate")
	public String deviceTypeUpdate(Model model) {
		return DEVICETYPE_UPDATE_PAGE;
	}

	@GetMapping("/service/deviceTypeDelete")
	public String deviceTypeDelete(Model model) {
		return DEVICETYPE_DELETE_PAGE;
	}

}
