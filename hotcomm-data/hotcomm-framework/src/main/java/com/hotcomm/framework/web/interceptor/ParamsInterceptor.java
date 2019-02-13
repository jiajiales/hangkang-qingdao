package com.hotcomm.framework.web.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.log.LogManager;
import com.hotcomm.framework.utils.RegexUtils;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.param.ParamCodeManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @author wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午4:17:20
 */
@Slf4j
public class ParamsInterceptor implements HandlerInterceptor {

	LogManager logManager;

	public ParamsInterceptor() {

	}

	public ParamsInterceptor(LogManager logManager) {
		this.logManager = logManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ParamCodeManager paramCodeManager = SpringUtil.getBean(ParamCodeManager.class);
		String filterURL = request.getRequestURI();
		log.info(String.format("【参数拦截器】拦截地址：%s", filterURL));
		Enumeration<String> paramKeys = request.getParameterNames();

		while (paramKeys.hasMoreElements()) {
			String key = paramKeys.nextElement();
			String value = request.getParameter(key);
			log.info(String.format("【参数拦截器】 参数 %s =%s", key, value));
		}

		if (filterURL.startsWith("/static/"))
			return true;

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final boolean exists = method.isAnnotationPresent(ParamsValidate.class);

		if (!exists)
			return true;

		ParamsValidate paramsValidate = method.getAnnotation(ParamsValidate.class);
		Param[] params = paramsValidate.validateParams();

		if (params == null || params.length == 0)
			return true;

		for (Param param : params) {
			if (param == null)
				continue;

			String key = param.key();
			String existsKey = request.getParameter(key);

			if (existsKey == null || existsKey.trim().length() == 0) {
				String code = param.code();
				String desc = paramCodeManager.getDesc(code);
				throw new HKException("-1", desc);
			}
		}

		MethodParameter[] paramters = handlerMethod.getMethodParameters();
		Class<?> validateParamterType = null;

		for (MethodParameter paramter : paramters) {
			Class<?> paramterType = paramter.getParameterType();
			Type[] interfaces = paramterType.getGenericInterfaces();

			if (interfaces == null || interfaces.length == 0)
				continue;

			boolean validateParamsExists = true;

			for (Type type : interfaces) {
				if (type.getTypeName().equals(com.hotcomm.framework.comm.ParamsValidate.class.getName()))
					validateParamsExists = false;
			}

			if (validateParamsExists)
				continue;

			validateParamterType = paramterType;
		}

		if (validateParamterType == null)
			return true;

		Field[] fields = validateParamterType.getDeclaredFields();

		if (fields == null || fields.length == 0)
			return true;

		for (Field field : fields) {
			if (!field.isAnnotationPresent(FieldValidate.class))
				continue;

			FieldValidate fieldAnnotation = field.getDeclaredAnnotation(FieldValidate.class);
			String fieldName = field.getName();
			String objVal = request.getParameter(fieldName);

			if (objVal != null && objVal != "") {
				String limit = fieldAnnotation.limit();
				ParamType type = fieldAnnotation.type();

				if (!objVal.matches("^.{" + limit + "}"))
					throw new HKException("-1", "请确认该输入符合长度限制,正确长度限制为[" + limit + "]");

				switch (type) {
				case NUMBER:
					if (!RegexUtils.isNumber(objVal)) {
						throw new HKException("-1", "请确认该输入为纯数字");
					}
					break;
				case ENGLISH_NUMBER:
					if (!RegexUtils.isEnglishNumber(objVal)) {
						throw new HKException("-1", "请确认该输入为纯英文、纯数字或英文数字混合");
					}
					break;
				case STRING:
					if (RegexUtils.isContainSpecial(objVal)) {
						throw new HKException("-1", "该输入含有特殊字符");
					}
					break;
				case NAME:
					if (!RegexUtils.isName(objVal)) {
						throw new HKException("-1", "请确认该输入为中文姓名或英文姓名");
					}
					break;
				case EMAIL:
					if (!RegexUtils.isEmail(objVal)) {
						throw new HKException("-1", "请确认该输入为正确的邮箱地址格式");
					}
					break;
				case CUSTOM:
					String pattern = fieldAnnotation.pattern();
					if (!Pattern.matches(pattern, objVal)) {
						throw new HKException("-1", "请确认该输入能匹配正则表达式:" + pattern);
					}
					break;
				default:
					break;
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
