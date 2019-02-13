package com.hot.manage.controller.ljt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.group.IsxistController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.ljt.LJTDev;
import com.hot.manage.entity.ljt.LJTDevone;
import com.hot.manage.entity.ljt.LJTDevv;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.ljt.LJTDevService;
import com.hot.manage.utils.ApiResult;

@RestController
@RequestMapping("/ljtdev")
public class LJTDevController {
	@Autowired
	private LJTDevService ljtDevService;

	@Autowired
	private IsxistController isxistController;

	// 设备表
	@ResponseBody
	@PostMapping("/devlist")
	@Permissions("ljt:devlist:query")
	public ApiResult selectdevlist(LJTDevv ljtDevv) {
		PageInfo<LJTDev> list = ljtDevService.selectdevlist(ljtDevv);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 根据id查设备
	@ResponseBody
	@PostMapping("/selectdevbyid")
	@Permissions("ljt:selectdevbyid:query")
	public ApiResult selectdevbyid(Integer devid) {
		LJTDevone list = ljtDevService.selectdevbyid(devid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 更新设备
	@ResponseBody
	@PostMapping("/updatedev")
	@Permissions("ljt:updatedev:update")
	public ApiResult updatedevice(Integer devid, String devnum, String mac, Integer groupid, String code, Double lat,
			Double lng, Integer x, Integer y, Integer mapimgid, Integer own_id, Integer moduleid) {
		int dgcount = isxistController.countdg(1009, devnum, mac, devid);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同项目名称或编号,修改失败！", null);
		} else {
			int a = ljtDevService.updatedev(devid, devnum, mac, groupid, code, lat, lng, x, y, mapimgid, own_id,
					moduleid);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}
	}

	// 添加设备
	@ResponseBody
	@PostMapping("/insertdev")
	@Permissions("ljt:insertdev:add")
	public ApiResult insertdevice(LJTDev ljtDev) {
		String keywords1 = ljtDev.getDevnum();
		String keywords2 = ljtDev.getMac();
		int dgcount = isxistController.countdg(1009, keywords1, keywords2, 0);
		if (dgcount > 0) {
			return ApiResult.resultInfo("-1", "已有相同项目名称或编号,添加失败！", null);
		} else {
			int a = ljtDevService.insertdev(ljtDev);
			if (a > 0) {
				return ApiResult.resultInfo("1", "增加成功！", null);
			} else {
				return ApiResult.resultInfo("0", "增加失败！", null);
			}
		}
	}

	// 更新设备mac
	@ResponseBody
	@PostMapping("/updatedevmac")
	public ApiResult updatedevicemac(Integer devid, String mac) {
		String devnum = "ergtfe5rhgrtky";
		int dgcount = isxistController.countdg(1009, devnum, mac, 0);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同mac,修改失败！", null);
		} else {
			int a = ljtDevService.updatedevmac(devid, mac);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}
	}

	/**
	 * 修改责任人
	 * 
	 * @param devids
	 * @param own_id
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updateown")
	@Permissions("ljtdev:updateown:update")
	public ApiResult updateownid(String devids, Integer own_id) {
		if (own_id == null) {
			throw new MyException("0", "请选择责任人！！！");
		}
		int a = ljtDevService.updateownid(devids, own_id);
		if (a > 0) {
			return ApiResult.resultInfo("1", "修改成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	/**
	 * 垃圾桶报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectAlarmTendency")
	@Permissions("")
	public ApiResult selectAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", ljtDevService.selectAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", ljtDevService.selectAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", ljtDevService.selectAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 垃圾桶报警处理统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectAlarmForWeeken")
	@Permissions("")
	public ApiResult selectAlarmForWeeken(Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("0", "执行成功", ljtDevService.selectAlarmForWeeken(groupid));
	}

	/**
	 * 垃圾桶报警类型统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectAlarmForState")
	@Permissions("")
	public ApiResult selectAlarmForState(Integer userid, Integer TheType, Integer groupid) throws MyException {
		return ApiResult.resultInfo("0", "执行成功", ljtDevService.selectAlarmForState(groupid, TheType));
	}
}
