package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DeviceOTAAView extends AuthView {

	public static final String DEVICEOTAA_PAGE = "loraDevice/device-otaa";
	public static final String DEVICEOTAA_ADD_PAGE = "loraDevice/device-otaa-add";
	public static final String DEVICEOTAA_UPDATE_PAGE =  "loraDevice/device-otaa-update";
	public static final String VIEW_KEY = "device-OTAA";
	
	final String OTAA_TEMP_DOWNLOAD_ADDRESS = "static/download/device/OTAA.xlsx";
	
	@GetMapping("/deviceOTAA")
	public String deviceOTAA(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return DEVICEOTAA_PAGE;
	}

	@GetMapping("/service/deviceOTAAAdd")
	public String deviceOTAAAdd(Model model) {
		return DEVICEOTAA_ADD_PAGE;
	}

	@GetMapping("/service/deviceOTAAUpdate")
	public String deviceOTAAUpdate(Model model) {
		return DEVICEOTAA_UPDATE_PAGE;
	}

}
