package com.hotcomm.data.view.cview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleView {

	public static final String ROLE_PAGE = "system/role";
	public static final String ROLE_ADD_PAGE = "system/role-add";
	public static final String ROLE_UPDATE_PAGE = "system/role-update";

	@GetMapping("/sys/role")
	public String role(Model model) {
		return ROLE_PAGE;
	}

	@GetMapping("/sys/roleAdd")
	public String roleAdd(Model model) {
		return ROLE_ADD_PAGE;
	}

	@GetMapping("/sys/roleUpdate")
	public String roleUpdate(Model model) {
		return ROLE_UPDATE_PAGE;
	}

}
