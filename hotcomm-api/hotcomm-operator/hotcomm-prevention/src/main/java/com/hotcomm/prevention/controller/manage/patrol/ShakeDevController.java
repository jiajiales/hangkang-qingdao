package com.hotcomm.prevention.controller.manage.patrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignLogPageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlacePageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.NewSignPlaceParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignLogPageInfoParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignPlacePageInfoParam;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.patrol.ShakeDevService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("sign")
public class ShakeDevController {

	@Autowired
	private ShakeDevService shakeDevService;

	/*
	 * 签到地点分配巡检人员
	 */
	@PostMapping("/patUserRelationSign")
	public ApiResult patUserRelationSign(String patUser, String signId) throws MyException {
		return ApiResult.resultInfo("1", "分配成功", shakeDevService.patUserRelationSign(patUser, signId));
	}

	/*
	 * 删除签到地点
	 */
	@PostMapping("/deleteSignPlace")
	public ApiResult deleteSignPlace(Integer signId) throws MyException {
		return ApiResult.resultInfo("1", "删除成功", shakeDevService.deleteSignPlace(signId));
	}

	/*
	 * 更新设备签到地点
	 */
	@PostMapping("/updateSignPlace")
	public ApiResult updateSignPlace(NewSignPlaceParam param) throws MyException {
		int in = shakeDevService.updateSignPlace(param);
		if (in == 201) {
			return ApiResult.resultInfo("0", "修改失败，签到点编号已存在", null);
		}
		return ApiResult.resultInfo("1", "修改成功", shakeDevService.updateSignPlace(param));
	}

	/*
	 * 添加签到地点
	 */
	@PostMapping("/addSignAddress")
	public ApiResult addSignAddress(NewSignPlaceParam param) throws MyException {
		Integer in = shakeDevService.addSignAddress(param);
		if (in <= 0) {
			return ApiResult.resultInfo("0", "添加失败", null);
		} else {
			if (in == 201) {
				return ApiResult.resultInfo("0", "添加失败，签到地点编号已存在", null);
			} else {
				return ApiResult.resultInfo("1", "添加成功", null);
			}
		}
	}

	/*
	 * 查询单个签到地点
	 */
	@PostMapping("/selectSignPlaceOnid")
	public ApiResult selectSignPlaceOnid(Integer signId) throws MyException {
		return ApiResult.resultInfo("1", "查询成功", shakeDevService.selectSignPlaceOnid(signId));
	}

	/*
	 * 分页查询签到记录
	 */
	@PostMapping("/selectSignLogPageInfo")
	public ApiResult selectSignLogPageInfo(SignLogPageInfoParam param) throws MyException {
		PageInfo<SignLogPageInfoVO> pageinfo = shakeDevService.selectSignLogPageInfo(param);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/*
	 * 获取签到地点列表
	 */
	@PostMapping("/selectSignPlacePageInfo")
	public ApiResult selectSignPlacePageInfo(SignPlacePageInfoParam param) throws MyException {
		PageInfo<SignPlacePageInfoVO> pageinfo = shakeDevService.selectSignPlacePageInfo(param);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/*
	 * app扫码签到
	 */
	@PostMapping("/AppSign")
	public ApiResult AppSignlogs(SignInfo signInfo) {
		Integer code = shakeDevService.AppSigns(signInfo);
		if (code == 0) {
			return ApiResult.resultInfo("0", "用户未绑定当前签到点签到失败", null);
		} else if (code == 2) {
			return ApiResult.resultInfo("0", "签到地点与设备地点距离大于100米签到失败", null);
		} else if (code == 3) {
			return ApiResult.resultInfo("0", "未成功定位签到失败", null);
		}
		return ApiResult.resultInfo("1", "签到成功", null);
	}
}