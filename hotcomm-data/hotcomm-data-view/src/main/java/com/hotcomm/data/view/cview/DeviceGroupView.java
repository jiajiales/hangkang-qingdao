package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DeviceGroupView extends AuthView {

	public static final String DEVICE_GROUP_PAGE = "service/deviceGroup";

	public static final String DEVICE_GROUP_ADD_PAGE = "service/deviceGroup-add";

	public static final String DEVICE_GROUP_UPLOAD_PAGE = "service/deviceGroup-upload";

	public static final String DEVICE_GROUP_UPDATE_PAGE = "service/deviceGroup-update";

	public static final String DEVICE_GROUP_IMPORT = "service/deviceGroup-import";

	public static final String DEVICE_GROUP_MEMBER = "service/deviceGroup-member";

	public static final String VIEW_KEY = "service-device-group";

	@GetMapping("/service/device/group")
	public String deviceGroup(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return DEVICE_GROUP_PAGE;
	}

	@GetMapping("/service/deviceGroupAdd")
	public String deviceGroupAdd(Model model) {
		return DEVICE_GROUP_ADD_PAGE;
	}

	@GetMapping("/service/deviceGroupUpload")
	public String deviceGroupUpload(Model model) {
		return DEVICE_GROUP_UPLOAD_PAGE;
	}

	@GetMapping("/service/deviceGroupUpdate")
	public String deviceGroupUpdate(Model model) {
		return DEVICE_GROUP_UPDATE_PAGE;
	}

	@GetMapping("/service/devGroupImport")
	public String devGroupImport(Model model) {
		return DEVICE_GROUP_IMPORT;
	}

	@GetMapping("/service/deviceGroupMember")
	public String devGroupMember(Model model) {
		return DEVICE_GROUP_MEMBER;
	}

}
