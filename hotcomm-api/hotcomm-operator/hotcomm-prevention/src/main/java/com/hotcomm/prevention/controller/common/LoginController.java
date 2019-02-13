package com.hotcomm.prevention.controller.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.common.entity.TUserToken;
import com.hotcomm.prevention.bean.mysql.common.params.LoginModle;
import com.hotcomm.prevention.bean.mysql.common.vo.UserModle;
import com.hotcomm.prevention.config.RedisHelper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.PowersService;
import com.hotcomm.prevention.service.common.TUserPgrouRelationpService;
import com.hotcomm.prevention.service.common.TUserTokenService;
import com.hotcomm.prevention.service.common.UserService;
import com.hotcomm.prevention.service.manage.websocket.SocketService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.MD5Util;
import com.hotcomm.prevention.utils.TextUtils;
import com.hotcomm.prevention.utils.ValidateCodeUtil;

@RestController
public class LoginController {

	@Autowired
	RedisHelper redisHelper;
	@Autowired
	UserService userService;
	@Autowired
	TUserTokenService userTokenService;
	@Autowired
	TUserPgrouRelationpService userPgrouRelationpService;
	@Autowired
	PowersService powersService;
	@Autowired
	SocketService socketService;
	
	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	@GetMapping("/login/getValidateCode")
	public ApiResult getValidateCode(HttpServletResponse response, HttpServletRequest request)
			throws MyException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("image/jpeg");
		ValidateCodeUtil codeUtil = new ValidateCodeUtil();
		BufferedImage image = codeUtil.getImage();
		String code = codeUtil.getText();
		Map<String, String> map = new HashMap<>();
		// 生成uuid
		String uuid = UUID.randomUUID().toString();
		map.put("uuid", uuid);
		// 存入缓存 300秒过期
		redisHelper.set(uuid, code, 300);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", out);
		byte[] array = out.toByteArray();
		String imgString = Base64.encodeBase64String(array);
		imgString = "data:image/JPEG;base64," + imgString;
		// 获取通过base64加密后图形码字符串
		map.put("img", imgString);
		return ApiResult.resultInfo("1", "验证码", map);
	}

	@PostMapping("/login/getUserInfo")
	public ApiResult getUserInfo(LoginModle modle, HttpServletRequest request) throws MyException {
		if (TextUtils.isEmpty(modle.getUsername()) || TextUtils.isEmpty(modle.getPassword())) {
			return ApiResult.resultInfo("0", "登陆失败，账号或密码不能为空", null);
		}
		// PC端登陆
		if (modle.getLogintype() == 1) {
			return pcLogin(modle, request);
		} else {
			return appLogin(modle, request);
		}
	}

	private ApiResult pcLogin(LoginModle modle, HttpServletRequest request) {
		String code = modle.getCode().trim().toUpperCase();
		String uuid = modle.getUuid();
		if (code == null || uuid == null) {
			return ApiResult.resultInfo("0", "登陆失败，验证码或uuid不能为空", null);
		}
		String oldCode = redisHelper.get(uuid);
		if (oldCode == null) {
			return ApiResult.resultInfo("0", "登陆失败，验证码已失效", null);
		}
		if (!oldCode.toUpperCase().equals(code)) {
			return ApiResult.resultInfo("0", "登陆失败，验证码不正确", null);
		}
		TUser user = userService.selectByUsername(modle.getUsername());
		if (user == null) {
			return ApiResult.resultInfo("0", "登陆失败，你登陆的用户不存在", null);
		} else if (!user.getIsenable()) {
			return ApiResult.resultInfo("0", "登陆失败，该用户不可用，请联系管理员", null);
		} else if (user.getIsPcUser() != null && user.getIsPcUser() != 1) {
			return ApiResult.resultInfo("0", "登陆失败，该用户不PC端用户", null);
		} else if (!MD5Util.isPasswordInvalid(user.getPassword(), modle.getPassword(), MD5Util.SALT)) {
			return ApiResult.resultInfo("0", "登陆失败，密码不正确", null);
		}
		String token = UUID.randomUUID().toString();
		redisHelper.set(token+"_pc", token, 30 * 60);
		UserModle userModle = getUserModle(modle, request, user, token);
		//socketService.sendLogin(token, user.getId());
		redisHelper.del(uuid);
		return ApiResult.resultInfo("1", "登陆成功", userModle);
	}

	private UserModle getUserModle(LoginModle modle, HttpServletRequest request, TUser user, String token) {
		// 记录用户的登录信息
		TUserToken userToken = new TUserToken();
		userToken.setIp(request.getRemoteAddr());
		userToken.setLogintime(ConverUtil.dateForString(new Date()));
		userToken.setLogintype(modle.getLogintype());
		userToken.setToken(token);
		userToken.setUserid(user.getId());
		userTokenService.insertUserToken(userToken);
		// 修改用户的最后登陆时间和IP地址
		TUser user2 = new TUser();
		user2.setId(user.getId());
		user2.setLastloginip(request.getRemoteAddr());
		user2.setLastlogintime(ConverUtil.dateForString(new Date()));
		userService.updateById(user2);
		// 返回给前端的userModle
		UserModle userModle = new UserModle();
		userModle.setId(user.getId());
		userModle.setToken(token);
		userModle.setUsername(user.getLoginname());
		userModle.setRoleid(userPgrouRelationpService.selectRoleid(user.getId()));
		userModle.setPowers(powersService.selectPowersByUserid(0, user.getId()));
		return userModle;
	}

	public ApiResult appLogin(LoginModle modle, HttpServletRequest request) {
		TUser user = userService.selectByUsername(modle.getUsername());
		if (user == null) {
			return ApiResult.resultInfo("0", "登陆失败，你登陆的用户不存在", null);
		} else if (!user.getIsenable()) {
			return ApiResult.resultInfo("0", "登陆失败，该用户不可用，请联系管理员", null);
		} else if (user.getIsAppUser() != null && user.getIsPcUser() != 1) {
			return ApiResult.resultInfo("0", "登陆失败，该用户不PC端用户", null);
		} else if (!MD5Util.isPasswordInvalid(user.getPassword(), modle.getPassword(), MD5Util.SALT)) {
			return ApiResult.resultInfo("0", "登陆失败，密码不正确", null);
		}
		String token = UUID.randomUUID().toString();
		redisHelper.set(token+"_app", token, 30 * 60);
		UserModle userModle = getUserModle(modle, request, user, token);
		//redisHelper.del(modle.getUuid());
		return ApiResult.resultInfo("1", "登陆成功", userModle);
	}

	/**
	 * 判断请求来自PC端还是移动端
	 * 
	 * @param requestHeader
	 * @return
	 */
	public static boolean isMobileDevice(String requestHeader) {
		String[] deviceArray = { "android", "ios", "windows phone" };
		if (requestHeader == null) {
			return false;
		}
		requestHeader = requestHeader.toLowerCase();
		for (String ss : deviceArray) {
			if (requestHeader.indexOf(ss) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 注销
	 * @param userid
	 * @param request
	 * @throws MyException
	 */
	@PostMapping("/logout")
	public ApiResult logout(String token, HttpServletRequest request) throws MyException {
		String requestHeader = request.getHeader("user-agent");
		boolean mobileDevice = isMobileDevice(requestHeader);
		if (mobileDevice) {
			redisHelper.del(token+"_app");
		} else {
			redisHelper.del(token+"_pc");
		}
		return ApiResult.resultInfo("1", "成功", null);
	}
}
