package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DeviceABPView extends AuthView {

	public static final String DEVICEABP_PAGE = "loraDevice/device-abp";
	public static final String DEVICEABP_ADD_PAGE = "loraDevice/device-abp-add";
	public static final String DEVICEABP_UPDATE_PAGE =  "loraDevice/device-abp-update";
	public static final String VIEW_KEY = "device-ABP";
	
	final String ABP_TEMP_DOWNLOAD_ADDRESS = "static/download/device/ABP.xlsx";

	@GetMapping("/deviceABP")
	public String deviceABP(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return DEVICEABP_PAGE;
	}

	@GetMapping("/service/deviceABPAdd")
	public String deviceABPAdd(Model model) {
		return DEVICEABP_ADD_PAGE;
	}

	@GetMapping("/service/deviceABPUpdate")
	public String deviceABPUpdate(Model model) {
		return DEVICEABP_UPDATE_PAGE;
	}

}
