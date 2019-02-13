package com.hot.manage.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.group.IsxistController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.TDeviceYg;
import com.hot.manage.entity.yg.YGDev;
import com.hot.manage.entity.yg.YGDevone;
import com.hot.manage.entity.yg.YGDevv;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGDevService;
import com.hot.manage.utils.ApiResult;

@Controller
@RequestMapping("/ygdev")
public class YGDevController {
	@Autowired
	private YGDevService yGDevService;

	@Autowired
	private IsxistController isxistController;
	
	// 设备表
	@ResponseBody
	@PostMapping("/devlist")
	@Permissions("ygdev:devlist:query")
	public ApiResult selectdevlist(YGDevv yGDevv) {
		PageInfo<YGDev> list = yGDevService.selectdevlist(yGDevv);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 根据id查设备
	@ResponseBody
	@PostMapping("/selectdevbyid")
	@Permissions("ygdev:selectdevbyid:query")
	public ApiResult selectdevbyid(Integer devid) {
		YGDevone list = yGDevService.selectdevbyid(devid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 更新设备
	@ResponseBody
	@PostMapping("/updatedev")
	@Permissions("ygdev:updatedev:update")
	public ApiResult updatedevice(Integer devid, String devnum, String mac, Integer groupid, String code, String lat,
			String lng, Double x, Double y, Integer mapimgid, Integer ownId, Integer moduleid) {

		int dgcount = isxistController.countdg(1002, devnum, mac, devid);
		if (dgcount > 0) {
			return ApiResult.resultInfo("-1", "已有相同项目名称或编号,修改失败！", null);
		} else {
			int a = yGDevService.updatedev(devid, devnum, mac, groupid, code, lat, lng, x, y, mapimgid, ownId,
					moduleid);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}
	}

	// 更新设备mac
	@ResponseBody
	@PostMapping("/updatedevmac")
	@Permissions("ygdev:updatedevmac:update")
	public ApiResult updatedevicemac(Integer devid, String mac) {
		String devnum = "ergtfe5rhgrtky";
		int dgcount = isxistController.countdg(1002, devnum, mac, devid);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同mac,修改失败！", null);
		} else {
			int a = yGDevService.updatedevmac(devid, mac);
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
	@Permissions("ygdev:insertdev:add")
	public ApiResult insertdevice(YGDev ygDev) {
		String keywords1 = ygDev.getDevnum();
		String keywords2 = ygDev.getMac();
		if (keywords1 == "" || keywords2 == "") {
			return ApiResult.resultInfo("0", "设备编号不能空！", null);
		} else {
			int dgcount = isxistController.countdg(1002, keywords1, keywords2, 0);
			if (dgcount > 0) {
				return ApiResult.resultInfo("-1", "已有相同设备名称或编号,添加失败！", null);
			} else {
				int a = yGDevService.insertdev(ygDev);
				if (a > 0) {
					return ApiResult.resultInfo("1", "增加成功！", null);
				} else {
					return ApiResult.resultInfo("0", "增加失败！", null);
				}
			}
		}

	}

	/**
	 * 更具项目ID查询项目下的设备
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@PostMapping("/selectDevForGroup")
	@Permissions("ygdev:selectDevForGroup:query")
	public ApiResult selectDevForGroup(Integer groupid, Integer userid, Integer moduleid) throws MyException {
		List<TDeviceYg> selectDevForGroup = yGDevService.selectDevForGroup(groupid, userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectDevForGroup);
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
	@Permissions("ygdev:updateown:update")
	public ApiResult updateownid(String devids, Integer own_id) {
		if (own_id == null) {
			throw new MyException("0", "请选择责任人！！！");
		}
		int a = yGDevService.updateownid(devids, own_id);
		if (a > 0) {
			return ApiResult.resultInfo("1", "修改成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

}
