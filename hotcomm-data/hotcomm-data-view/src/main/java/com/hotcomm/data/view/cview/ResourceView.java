package com.hotcomm.data.view.cview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceView {

	public static final String RESOURCE_PAGE = "system/resource";
	public static final String RESOURCE_ADD_PAGE = "system/resource-add";
	public static final String RESOURCE_UPDATE_PAGE = "system/resource-update";

	@GetMapping("/sys/resource")
	public String resource(Model model) {
		return RESOURCE_PAGE;
	}

	@GetMapping("/sys/resourceAdd")
	public String resourceAdd(Model model) {
		return RESOURCE_ADD_PAGE;
	}

	@GetMapping("/sys/resourceUpdate")
	public String resourceUpdate(Model model) {
		return RESOURCE_UPDATE_PAGE;
	}

}
