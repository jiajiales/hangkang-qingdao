package com.hot.manage.controller.ywj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.ywj.LKTYWJDevList;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJGroupListDev;
import com.hot.manage.entity.ywj.LKTYWJSelectOnId;
import com.hot.manage.entity.ywj.LKTYWJSeleteMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddDevVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.ywj.LKTYWJDevService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LKTYWJDevController implements PageController<LKTYWJDevListVaule, LKTYWJDevList> {

	@Autowired
	private LKTYWJDevService lktYWJDevService;

	@PostMapping("/ywj/changeOwn")
	@Permissions("ywj:changeOwn:update")
	public ApiResult changeOwn(String devId, Integer ownId) throws MyException {
		ApiResult resultInfo = null;
		Integer one = lktYWJDevService.changeOwn(devId, ownId);
		if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "液位计责任人修改失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "液位计责任人修改成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/addSignDevList")
	@Permissions("ywj:addSignDevList")
	public ApiResult LKTAddSignDevList(String devid, Integer moduleid, Integer patrolid) throws MyException {
		ApiResult resultInfo = null;
		Integer one = lktYWJDevService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "液位计加入签到列表失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "液位计加入签到列表成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJDevList")
	@Permissions("ywj:LKTYWJDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(LKTYWJDevListVaule p) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktYWJDevService.LKTYWJDevList(p));
	}

	@PostMapping("/ywj/LKTYWJAddSignDevList")
	@Permissions("ywj:LKTYWJAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTYWJAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktYWJDevService.LKTYWJAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJUpdateDev")
	@Permissions("ywj:LKTYWJUpdateDev:update")
	// vaule=修改设备数据
	public ApiResult LKTYWJUpdateDev(LKTYWJUpdateDevVaule lktywjUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktYWJDevService.LKTYWJUpdateDev(lktywjUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJGroupList")
	@Permissions("ywj:LKTYWJGroupList:query")
	// vaule=查询可更换的项目组
	public List<LKTYWJGroupList> LKTYWJGroupList(Integer userid, Integer moduleid) {
		return lktYWJDevService.LKTYWJGroupList(userid, moduleid);
	}

	@PostMapping("/ywj/LKTYWJDeleteDev")
	@Permissions("ywj:LKTYWJDeleteDev:del")
	// vaule=删除设备
	public ApiResult LKTYWJDeleteDev(Integer moduleid, Integer devid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktYWJDevService.LKTYWJDeleteDev(moduleid, devid);
		if (insertObject >= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJGroupListDev")
	@Permissions("ywj:LKTYWJGroupListDev:query")
	// vaule=项目组平面图设备查看
	public List<LKTYWJGroupListDev> LKTYWJGroupListDev(Integer moduleid, Integer groupid, String site) {
		return lktYWJDevService.LKTYWJGroupListDev(moduleid, groupid, site);
	}

	@PostMapping("/ywj/LKTYWJGroupListDevnum")
	@Permissions("ywj:LKTYWJGroupListDevnum:query")
	// vaule=液位计项目终端设备数查询
	public LKTYWJDevNum LKTYWJGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) {
		// TODO Auto-generated method stub
		return lktYWJDevService.LKTYWJGroupListDevnum(moduleid, userid, groupid);
	}

	@PostMapping("/ywj/LKTYWJSelectOnId")
	@Permissions("ywj:LKTYWJSelectOnId:query")
	// vaule=根据设备id查询设备信息
	public List<LKTYWJSelectOnId> LKTYWJSelectOnId(Integer moduleid, Integer userid, Integer devid) {
		return lktYWJDevService.LKTYWJSelectOnId(moduleid, userid, devid);
	}

	@PostMapping("/ywj/LKTYWJAddDev")
	@Permissions("ywj:LKTYWJAddDev:add")
	// vaule=添加液位计设备
	public ApiResult LKTYWJAddDev(LKTYWJAddDevVaule lktywjAddDevVaule) {
		// TODO Auto-generated method stub
		ApiResult resultInfo = null;
		Integer insertObject = lktYWJDevService.LKTYWJAddDev(lktywjAddDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJSeleteMap")
	@Permissions("ywj:LKTYWJSeleteMap:query")
	// vaule=项目组楼层查询
	public List<LKTYWJSeleteMap> LKTYWJSeleteMap(Integer groupid, Integer userid) {
		return lktYWJDevService.LKTYWJSeleteMap(groupid, userid);
	}
	
	@PostMapping("/ywj/AlarmingTrendForWhichHasBoundaryValue")
	@Permissions("ywj:select:AlarmingTrendForWhichHasBoundaryValue")
	//查询报警趋势图
	public ApiResult AlarmingTrendForWhichHasBoundaryValue(Integer queryType,Integer userid,Integer groupid) {
		return ApiResult.resultInfo("1", "成功", lktYWJDevService.AlarmingTrendForWhichHasBoundaryValue(queryType, userid, groupid));
	}

	// 更换mac地址
		@PostMapping("/ywj/changeMac")
		@Permissions("ywj:changeMac:update")
		public ApiResult changeMac(Integer devid, String mac) throws MyException {
			ApiResult resultInfo = null;
			Integer one = lktYWJDevService.changeMac(devid, mac);
			if (one <= 0) {
				resultInfo = ApiResult.resultInfo("0", "液位计MAC修改失败！", null);
			} else {
				resultInfo = ApiResult.resultInfo("1", "液位计MAC修改成功！", null);
			}
			if (one == 201) {
				resultInfo = ApiResult.resultInfo("0", "修改失败，Mac地址已存在！", null);
			} 
			return resultInfo;
		}

}
