package com.hot.manage.controller.sj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.group.IsxistController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sj.SJAlarmNum;
import com.hot.manage.entity.sj.SJDev;
import com.hot.manage.entity.sj.SJDevone;
import com.hot.manage.entity.sj.SJDevv;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sj.SJDevService;
import com.hot.manage.utils.ApiResult;

@Controller
@RequestMapping("/sjdev")
public class SJDevController {
	@Autowired
	private SJDevService sjDevService;

	@Autowired
	private IsxistController isxistController;

	// 设备表
	@ResponseBody
	@PostMapping("/devlist")
	@Permissions("sj:devlist:query")
	public ApiResult selectdevlist(SJDevv sjDevv) {
		PageInfo<SJDev> list = sjDevService.selectdevlist(sjDevv);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 根据id查设备
	@ResponseBody
	@PostMapping("/selectdevbyid")
	@Permissions("sj:selectdevbyid:query")
	public ApiResult selectdevbyid(Integer devid) {
		SJDevone list = sjDevService.selectdevbyid(devid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 更新设备
	@ResponseBody
	@PostMapping("/updatedev")
	@Permissions("sj:updatedev:update")
	public ApiResult updatedevice(Integer devid, String devnum, String mac, Integer groupid, String code, Double lat,
			Double lng, Integer x, Integer y, Integer mapimgid, Integer own_id,Integer moduleid) {
		int dgcount = isxistController.countdg(1010, devnum, mac, devid);
		if (dgcount > 0) {
			return ApiResult.resultInfo("-1", "已有相同项目名称或编号,修改失败！", null);
		} else {
			int a = sjDevService.updatedev(devid, devnum, mac, groupid, code, lat, lng, x, y, mapimgid,own_id,moduleid);
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
	@Permissions("sj:insertdev:add")
	public ApiResult insertdevice(SJDev sjDev) {
		String keywords1 = sjDev.getDevnum();
		String keywords2 = sjDev.getMac();
		int dgcount = isxistController.countdg(1010, keywords1, keywords2, 0);
		if (dgcount > 0) {
			return ApiResult.resultInfo("-1", "已有相同项目名称或编号,添加失败！", null);
		} else {
			int a = sjDevService.insertdev(sjDev);
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
		int dgcount = isxistController.countdg(1010, devnum, mac, 0);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同mac,修改失败！", null);
		} else {
			int a = sjDevService.updatedevmac(devid, mac);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}
	}
	
	/**
	 * 修改责任人
	 * @param devids
	 * @param own_id
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updateown")
	@Permissions("sjdev:updateown:update")
	public ApiResult updateownid(String devids, Integer own_id) {
		if (own_id == null) {
			throw new MyException("0", "请选择责任人！！！");
		}
		int a = sjDevService.updateownid(devids, own_id);
		if (a > 0) {
			return ApiResult.resultInfo("1", "修改成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}
	
	
	//******项目概况********
	
	//设备状况
	
	
	//报警类型统计
	@ResponseBody
	@PostMapping("/selectSJAlarmNums")
	@Permissions("sjdev:updateown:update")

	public List<SJAlarmNum> selectSJAlarmNums(Integer Time, Integer moduleID, Integer userid) {
		
		List<SJAlarmNum> list = sjDevService.selectSJAlarmNums(Time, moduleID, userid);
		return list;
	}
	
	
	

}
