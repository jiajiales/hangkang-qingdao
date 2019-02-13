package com.hotcomm.data.view.cview;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthView {
	
	@SuppressWarnings("unchecked")
	public void storeAuthButtons(HttpServletRequest request,Model model,String onlyViewKey) throws JsonProcessingException {
		Object obj = request.getSession().getAttribute("authButtons");
		if(obj!=null) {
			Map<Object, Map<String, Boolean>> authButtons = (Map<Object, Map<String, Boolean>>)obj;
			Map<String, Boolean> b = authButtons.get(onlyViewKey);
			ObjectMapper mapper = new ObjectMapper();
			model.addAttribute("buttons",mapper.writeValueAsString(b));
		}
	}
	
}
