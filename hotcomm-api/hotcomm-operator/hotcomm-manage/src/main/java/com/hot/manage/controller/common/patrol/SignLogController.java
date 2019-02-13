package com.hot.manage.controller.common.patrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordVo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.patrol.THkSignlogService;
import com.hot.manage.utils.ApiResult;

@RestController
public class SignLogController {

	@Autowired
	private THkSignlogService HkSignlogService;

	/**
	 * 指定（终端设备）设备历史签到记录
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectPageForDev")
	@Permissions("patrol:selectPageForDev:query")
	public ApiResult selectPageForDev(SignRecordParam param) throws MyException {
		PageInfo<SignRecordVo> page = HkSignlogService.selectPageForDev(param);
		return ApiResult.resultInfo("1", "成功", page);
	}

	/**
	 * 指定摇一摇签到设备历史记录
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectPageForShake")
	@Permissions("patrol:selectPageForShake:query")
	public ApiResult selectPageForShake(ShakeRecordParam param) throws MyException {
		PageInfo<SignRecordVo> page = HkSignlogService.selectPageForShake(param);
		return ApiResult.resultInfo("1", "成功", page);
	}

	/**
	 * 设备签到（终端设备）全部历史记录
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectPageInfoAllForDev")
	@Permissions("patrol:selectPageInfoAllForDev:query")
	public ApiResult selectPageInfoAllForDev(SignRecordParam param) throws MyException {
		PageInfo<SignRecordVo> page = HkSignlogService.selectPageInfoAllForDev(param);
		return ApiResult.resultInfo("1", "成功", page);
	}

	/**
	 * 指定设备签到（app签到）
	 * 
	 * @param userid
	 * @param deviceid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/insertDevSignLog")
	@Permissions("patrol:insertDevSignLog:add")
	public ApiResult insertDevSignLog(Integer userid, Integer deviceid, Integer moduleid) throws MyException {
		Integer insertDevSign = HkSignlogService.insertDevSign(userid, deviceid, moduleid);
		if (insertDevSign <= 0) {
			return ApiResult.resultInfo("0", "签到失败", null);
		} else {
			return ApiResult.resultInfo("1", "签到成功", null);
		}
	}

}
