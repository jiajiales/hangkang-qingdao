package com.hotcomm.data.view.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseView {
	
	protected Map<String, Object> getErrorMap(){
		Map<String, Object> params = new HashMap<>();
		params.put("code", -1);
		params.put("msg", "系统异常");
		return params;
	}
	
	protected String joinRestUrl(final String url) {
		StringBuffer bs = new StringBuffer();
		bs.append("http://");
		bs.append("");
		bs.append(":");
		bs.append("");
		bs.append(url);
		return bs.toString();
	}
	
	
	protected Map<String, Object> getparams(HttpServletRequest request){
		Map<String, Object> params = new HashMap<>();
		Enumeration<String> ps = request.getParameterNames();
		while(ps.hasMoreElements()) {
			String key = ps.nextElement();
			String value = request.getParameter(key);
			if(value==null||value.equals("")) 
				continue;
			params.put(key, value);
		}
		return params;
	}
	
}
