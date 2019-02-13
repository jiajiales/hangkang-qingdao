package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DataView extends AuthView {

	public static final String DATA_PAGE = "service/data";
	public static final String DATA_VIEW_PAGE = "service/data-view";
	public static final String VIEW_KEY = "service-data";

	@GetMapping("/service/data")
	public String data(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		System.out.println("DataView001002");
		return DATA_PAGE;
	}

	@GetMapping("/data/view")
	public String dataView(Model model) {
		return DATA_VIEW_PAGE;
	}

}
