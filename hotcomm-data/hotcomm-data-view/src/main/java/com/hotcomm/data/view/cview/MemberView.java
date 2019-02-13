package com.hotcomm.data.view.cview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberView {

	public static final String MEMBER_PAGE = "system/member";
	public static final String MEMBER_ADD_PAGE = "system/member-add";
	public static final String MEMBER_UPDATE_PAGE = "system/member-update";
	public static final String MEMBER_PSW_SET = "system/member-psw-set";
	public static final String MEMBER_ADD_CUSTOMERMEMBER = "system/member-add-cm";

	@GetMapping("/sys/member")
	public String member(Model model) {
		model.addAttribute("keys", "member-del");
		return MEMBER_PAGE;
	}

	@GetMapping("/sys/memberAdd")
	public String memberAdd(Model model) {
		return MEMBER_ADD_PAGE;
	}

	@GetMapping("/sys/memberUpdate")
	public String memberUpdate(Model model) {
		return MEMBER_UPDATE_PAGE;
	}

	@GetMapping("/sys/memberPswSet")
	public String memberPswSet(Model model) {
		return MEMBER_PSW_SET;
	}

	@GetMapping("/sys/memberAddCm")
	public String memberAddCm(Model model) {
		return MEMBER_ADD_CUSTOMERMEMBER;
	}

}
