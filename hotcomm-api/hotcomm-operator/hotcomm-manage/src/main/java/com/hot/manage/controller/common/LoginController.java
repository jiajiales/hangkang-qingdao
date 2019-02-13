package com.hot.manage.controller.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.config.RedisHelper;
import com.hot.manage.entity.common.user.LoginModel;
import com.hot.manage.entity.common.user.PowerModel;
import com.hot.manage.entity.common.user.UserModel;
import com.hot.manage.entity.system.TPowerGroup;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.SocketService;
import com.hot.manage.service.common.LoginService;
import com.hot.manage.service.system.TPowerGroupService;
import com.hot.manage.service.system.TPowerService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;
import com.hot.manage.utils.IpUtil;
import com.hot.manage.utils.MD5Util;
import com.hot.manage.utils.ValidateCodeUtil;

@RestController
public class LoginController {

	@Autowired
	RedisHelper redisHelper;
	@Autowired
	LoginService loginService;
	@Autowired
	SocketService SocketService;
	@Autowired
	TPowerService PowerService;
	@Autowired
	TPowerGroupService PowerGroupService;

	/**
	 * PC端验证码获取接口
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/kaptcha/defaultKaptcha")
	public ModelMap getValidateCode(HttpServletResponse response, HttpServletRequest request)
			throws IOException, MyException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("image/jpeg");
		ValidateCodeUtil codeUtil = new ValidateCodeUtil();
		BufferedImage image = codeUtil.getImage();
		String code = codeUtil.getText();
		// 生成验证码uuid
		ModelMap model = new ModelMap();
		String uuid = UUID.randomUUID().toString();
		model.put("id", uuid);
		// 存入缓存 300秒过期
		redisHelper.set(uuid, code, 300);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", out);
		byte[] array = out.toByteArray();
		String imgString = Base64.encodeBase64String(array);
		imgString = "data:image/JPEG;base64," + imgString;
		model.put("img", imgString);// 获取通过base64加密后图形码字符串
		return model;
	}

	/**
	 * 登陆接口
	 * 
	 * @param loginmodel
	 * @param request
	 * @return
	 */
	@PostMapping("/login/getUserInfo")
	public ApiResult getUserInfo(LoginModel loginmodel, HttpServletRequest request) throws MyException {
		// String requestHeader = request.getHeader("user-agent");
		if (loginmodel.getLogintype() != 1) {
			return mobileLogin(loginmodel, request);
		} else {
			return PCLogin(loginmodel, request);
		}
	}

	private ApiResult mobileLogin(LoginModel loginmodel, HttpServletRequest request) throws MyException {
		if (loginmodel.getUsername() == null || loginmodel.getPassword() == null) {
			return ApiResult.resultInfo("0", "账户或密码不能为空！", null);
		}
		String encodePassword = MD5Util.encodePassword(loginmodel.getPassword(), MD5Util.SALT);
		// 登陆
		TUser user = loginService.LoginFun(loginmodel.getUsername().trim());
		if (user == null) {
			return ApiResult.resultInfo("0", "此用户不存在！", null);
		} else if (!user.getPassword().equals(encodePassword)) {
			return ApiResult.resultInfo("0", "账户名或密码不正确！", null);
		} else if (!user.getIsenable()) {
			return ApiResult.resultInfo("0", "账户不可用，请联系管理员！", null);
		} else if (user.getIsAppUser() != 1) {
			return ApiResult.resultInfo("0", "你不是app用户！", null);
		}
		// 判断角色，如过不是施工队或巡检人员，登录失败，返回你不是app用户？？？？？？？
		/*TPower one = PowerService.selectOneById(user.getId());
		if (one == null) {
			return ApiResult.resultInfo("0", "你不是app用户", null);
		}*/
		// 登陆时间和登陆ip
		String ip = request.getRemoteAddr();
		loginService.updateUser(ip, ConverUtil.timeForString(new Date()), user.getId());
		// 登录成功，切是app用户
		UserModel userModel = new UserModel();
		String loginToken = UUID.randomUUID().toString();
		userModel.setToken(loginToken);
		// 去缓存中查找用户的权限页面，没有去数据库中查找
		List<PowerModel> list = redisHelper.getList("mplist_" + user.getId(), PowerModel.class);
		if (list == null || list.size() == 0 || list.equals(null)) {
			list = loginService.GetPowerList(user.getId());
			userModel.setPowerList(list);
			redisHelper.setList("mplist_" + user.getId(), list, 24 * 60 * 60);
		}
		userModel.setPowerList(list);
		// 应用服务其验证权限使用
		StringBuffer sb = new StringBuffer();
		for (PowerModel powerModel : userModel.getPowerList()) {
			sb.append(powerModel.getAnnotation()).append(",");
		}
		/*String powers = sb.toString().substring(0, sb.toString().length() - 1);
		redisHelper.set("mp_" + user.getId(), powers, 60 * 40);*/
		// token存入数据库
		loginService.insertToken(user.getId(), loginToken, loginmodel.getLogintype(),
				IpUtil.getIpAddrByRequest(request));
		// token存入缓存 60*20秒过期
		redisHelper.set("token_" + user.getId(), loginToken, 24 * 60 * 60);
		userModel.setId(user.getId());
		userModel.setUsername(loginmodel.getUsername());
		// SocketService.sendLogin(loginToken, loginFun);
		TPowerGroup role = PowerGroupService.selectByUserId(user.getId());
		userModel.setRoleid(role.getId());
		return ApiResult.resultInfo("1", "登陆成功", userModel);
	}

	private ApiResult PCLogin(LoginModel loginmodel, HttpServletRequest request) throws MyException {
		String codeToken = loginmodel.getCodetoken();
		String userCode = loginmodel.getCode().trim().toUpperCase();
		if (codeToken == null || userCode == null) {
			return ApiResult.resultInfo("0", "请求不合法！", null);
		}
		String code = redisHelper.get(codeToken);
		if (code == null) {
			return ApiResult.resultInfo("0", "验证码失效！", null);
		}
		if (code.toUpperCase().equals(userCode)) {
			String encodePassword = MD5Util.encodePassword(loginmodel.getPassword(), MD5Util.SALT);
			// 登陆
			TUser user = loginService.LoginFun(loginmodel.getUsername().trim());
			if (user == null) {
				return ApiResult.resultInfo("0", "此用户不存在！", null);
			} else if (!user.getPassword().equals(encodePassword)) {
				return ApiResult.resultInfo("0", "账户名或密码不正确！", null);
			} else if (!user.getIsenable()) {
				return ApiResult.resultInfo("0", "账户不可用，请联系管理员！", null);
			} else if (user.getIsPcUser() != null && user.getIsPcUser() != 1) {
				return ApiResult.resultInfo("0", "你不是PC用户！", null);
			}
			// 登陆时间和登陆ip
			String ip = request.getRemoteAddr();
			loginService.updateUser(ip, ConverUtil.timeForString(new Date()), user.getId());
			UserModel userModel = new UserModel();
			String loginToken = UUID.randomUUID().toString();
			userModel.setToken(loginToken);
			// 去缓存中查找用户的权限页面，没有去数据库中查找
			List<PowerModel> list = redisHelper.getList("powerList_" + user.getId(), PowerModel.class);
			if (list == null || list.size() == 0 || list.equals(null)) {
				list = loginService.GetPowerList(user.getId());
				userModel.setPowerList(list);
				redisHelper.setList("powerList_" + user.getId(), list, 24 * 60 * 60);
			}
			userModel.setPowerList(list);
			// 应用服务其验证权限使用
			StringBuffer sb = new StringBuffer();
			List<PowerModel> powerList = userModel.getPowerList();
			for (PowerModel powerModel : powerList) {
				sb.append(powerModel.getAnnotation()).append("-").append(powerModel.getModuleid()).append(",");
			}
			/*String powers = sb.toString().substring(0, sb.toString().length() - 1);
			redisHelper.set("power_" + user.getId(), powers, 60 * 40);*/
			// token存入数据库
			loginService.insertToken(user.getId(), loginToken, loginmodel.getLogintype(),
					IpUtil.getIpAddrByRequest(request));
			// token存入缓存 60*20秒过期
			redisHelper.set("token_" + user.getId(), loginToken, 24 * 60 * 60);
			userModel.setId(user.getId());
			userModel.setUsername(loginmodel.getUsername());
			SocketService.sendLogin(loginToken, user.getId());
			redisHelper.del(codeToken);
			return ApiResult.resultInfo("1", "登陆成功", userModel);
		}
		return ApiResult.resultInfo("0", "验证码不正确，请重新输入", null);
	}

	/**
	 * 判断请求来自PC端还是移动端
	 * 
	 * @param requestHeader
	 * @return
	 */
	public static boolean isMobileDevice(String requestHeader) {
		String[] deviceArray = { "android", "mac os", "windows phone" };
		if (requestHeader == null) {
			return false;
		}
		requestHeader = requestHeader.toLowerCase();
		for (String ss : deviceArray) {
			if (requestHeader.indexOf(ss) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 注销
	 * 
	 * @param userid
	 * @param request
	 * @throws MyException
	 */
	@PostMapping("/logout")
	public ApiResult logout(Integer userid, HttpServletRequest request) throws MyException {
		String requestHeader = request.getHeader("user-agent");
		boolean mobileDevice = isMobileDevice(requestHeader);
		if (mobileDevice) {
			redisHelper.del("mplist_" + userid);
		} else {
			redisHelper.del("powerList_" + userid);
		}
		return ApiResult.resultInfo("1", "成功", null);
	}

}
