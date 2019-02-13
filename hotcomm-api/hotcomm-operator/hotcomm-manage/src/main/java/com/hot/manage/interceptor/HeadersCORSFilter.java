package com.hot.manage.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter(filterName="HeadersCORSFilter",urlPatterns="/*")
public class HeadersCORSFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletResponse response = (HttpServletResponse) servletResponse;
		 HttpServletRequest request = (HttpServletRequest) servletrequest;
         response.setHeader("Access-Control-Allow-Origin","*");
         response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
         response.setHeader("Access-Control-Max-Age", "3600");
         response.setHeader("Access-Control-Allow-Headers", "Origin,x-requested-with,Authorization,Content-Type, Accept");
         response.setHeader("Access-Control-Allow-Credentials","true");
         chain.doFilter(request, servletResponse);
	}

	@Override
	public void destroy() {
	}

}
