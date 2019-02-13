package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.bean.mysql.common.entity.TPower;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.PowersService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class PowersController {
	@Autowired
	PowersService powersService;
	
	/**
	 * 菜单页面->点击相应菜单获取页面加载权限
	 * @param fatherid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/selectPowersByUserid")
	public ApiResult selectPowersByUserid(Integer fatherid, Integer userid) throws MyException {
		List<TPower> resources = powersService.selectPowersByUserid(fatherid, userid);
		return ApiResult.resultInfo("1", "请求成功", resources);
	}
	
	/**
	 * 系统设置->资源管理->当前用户的所有资源
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/selectPower")
	public ApiResult selectPower(Integer userid)throws MyException{
		List<TPower> powers = powersService.selectPowers(userid);
		return ApiResult.resultInfo("1", "请求成功", powers);
	}
	
	/**
	 * 系统设置->资源管理->修改
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/update")
	public ApiResult update(TPower power)throws MyException{
		powersService.update(power);
		return ApiResult.resultInfo("1", "请求成功", null);
	}
}
