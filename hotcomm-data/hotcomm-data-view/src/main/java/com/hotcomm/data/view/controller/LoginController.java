package com.hotcomm.data.view.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.view.bean.Constant;
import com.hotcomm.data.view.config.ApplicationEnvironment;
import com.hotcomm.data.view.socket.WebSocketHandler;
import com.hotcomm.data.view.utils.HttpUtils;
import com.hotcomm.framework.utils.RedisHelper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController implements HandlerInterceptor {

	public static final String LOGIN_PAGE = "login";

	private final String LOGIN_AUTHORITY = "/member/login";
	
	@Autowired
	private ApplicationEnvironment environment;
	
	@GetMapping("/login")
	public String login(Model model) {
		return LOGIN_PAGE;
	}

	RedisHelper redisHelper;

	public LoginController(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/validateMember")
	@ResponseBody
	public Map<String, Object> loginSuccess(HttpServletRequest request, String username, String password,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		String proxyUrl = "http://" + environment.PROXY_HOST + ":" + environment.PROXY_PORT+ LOGIN_AUTHORITY;
		Map<String, Object> params = new HashMap<>();
		params.put("userName", username);
		params.put("password", password);
		params.put("sessionId", request.getSession().getId());

		try {
			String jsonResult = HttpUtils.collectApiData("POST", proxyUrl, params);
			log.info("login result -->{}"+jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(jsonResult, Map.class);
			Object data = map.get("data");

			if (data != null) {
				request.getSession().setAttribute(Constant.LOGIN_INDEX, 1);
				request.getSession().setAttribute("token", data);
				Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
				cookie.setMaxAge(Constant.KEEP_TIME);
				cookie.setPath("/");
				response.addCookie(cookie);//
				map.put("host", environment.PROXY_HOST);
			}

			result.putAll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		request.getSession().removeAttribute(Constant.LOGIN_INDEX);
		Object token = request.getSession().getAttribute("token");
		// String proxyUrl =
		// "http://"+PropertiesHelper.PROXY_HOST+":"+PropertiesHelper.PROXY_PORT+LOGOUT_AUTHORITY;
		Map<String, Object> params = new HashMap<>();
		params.put("token", token);
		String sessionId = request.getSession().getId();
		WebSocketHandler.removeKeyBySessionId(sessionId);

		// 在redis上删除该token
		if (token != null)
			redisHelper.del(token.toString());

		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					cookie.setMaxAge(0);
					cookie.setPath("/");// 设置作用域
					response.addCookie(cookie);// 将cookie添加到response的cookie数组中返回给客户端
					break;
				}
			}
		}
		result.put("code", 0);
		return result;
	}
	
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImg(@RequestParam(value = "imgFile") MultipartFile[] imgFile,Map<String, Object> map, HttpSession session) throws Exception{
		Map<String, Object> result = new HashMap<>();
		System.out.println(imgFile);
		return result;
	}
}
