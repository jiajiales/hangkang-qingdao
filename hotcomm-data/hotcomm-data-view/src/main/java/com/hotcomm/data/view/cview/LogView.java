package com.hotcomm.data.view.cview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogView {

	public static final String LOG_PAGE = "system/log";

	@GetMapping("/sys/log")
	public String log(Model model) {
		model.addAttribute("keys", "member-del");
		return LOG_PAGE;
	}

}
