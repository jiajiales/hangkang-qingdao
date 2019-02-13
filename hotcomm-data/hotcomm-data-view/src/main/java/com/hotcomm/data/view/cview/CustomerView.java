package com.hotcomm.data.view.cview;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class CustomerView extends AuthView {

	public static final String CUSTOMER_PAGE = "service/customer";
	public static final String CUSTOMER_ADD_PAGE = "service/customer-add";
	public static final String CUSTOMER_UPDATE_PAGE = "service/customer-update";
	public static final String CUSTOMER_VHOST_CONFIG_PAGE = "service/customer-vhost-config";
	public static final String VIEW_KEY = "service-customer";

	@GetMapping("/service/customer")
	public String customer(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return CUSTOMER_PAGE;
	}

	@GetMapping("/service/customerAdd")
	public String customerAdd(Model model) {
		return CUSTOMER_ADD_PAGE;
	}

	@GetMapping("/service/customerUpdate")
	public String customerUpdate(Model model) {
		return CUSTOMER_UPDATE_PAGE;
	}

	@GetMapping("/service/customerVhostConfig")
	public String customerVhostConfig(Model model) {
		return CUSTOMER_VHOST_CONFIG_PAGE;
	}

}
