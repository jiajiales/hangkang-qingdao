package com.hot.manage.controller.common.workorder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.workorder.LKTWorkDevnum;
import com.hot.manage.entity.common.workorder.LKTWorkListFather;
import com.hot.manage.entity.common.workorder.LKTWorkMyproject;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkListcondition;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkMaintenanceAppVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkNewVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkUpdateAppVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.workorder.LKTWorkService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
@RequestMapping("workorder/LKTWorkController")
public class LKTWorkController {

	@Autowired
	private LKTWorkService lktWorkService;

	/**
	 * 工单地图设备总数
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkDevnum")
	@Permissions("workorder:LKTWorkDevnum:query")
	@ResponseBody
	public ApiResult LKTWorkDevnum(Integer moduleid, Integer userid) throws MyException {
		LKTWorkDevnum lktWorkDevnum = lktWorkService.LKTWorkDevnum(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", lktWorkDevnum);
	}

	/**
	 * 查询地图项目信息，工单数
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkMyproject")
	@Permissions("workorder:LKTWorkMyproject:query")
	@ResponseBody
	public ApiResult LKTWorkMyproject(Integer moduleid, Integer userid) throws MyException {
		List<LKTWorkMyproject> lktWorkMyprojects = lktWorkService.LKTWorkMyproject(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", lktWorkMyprojects);
	}

	/**
	 * 工单列表（可按项目组查）
	 * 
	 * @param lktWorkListcondition
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkListFather")
	@Permissions("workorder:LKTWorkListFather:query")
	@ResponseBody
	public ApiResult LKTWorkListFather(LKTWorkListcondition lktWorkListcondition) throws MyException {
		Integer moduleid = lktWorkListcondition.getModuleid();
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		PageHelper.startPage(lktWorkListcondition.getPageNum(), lktWorkListcondition.getPageSize());
		Page<LKTWorkListFather> page = lktWorkService.LKTWorkListFather(lktWorkListcondition);
		List<LKTWorkListFather> list = ConverUtil.converPage(page, LKTWorkListFather.class);
		PageInfo<LKTWorkListFather> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/**
	 * 查询未处理报警与未处理事件自动带入设备
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkAllAlarm")
	@Permissions("workorder:LKTWorkAllAlarm:query")
	@ResponseBody
	public ApiResult LKTWorkAllAlarm(Integer moduleid, Integer userid) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功", lktWorkService.LKTWorkAllAlarm(moduleid, userid));
	}

	/**
	 * 故障设备列表查询-带出关联的报警和事件
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkDevList")
	@Permissions("workorder:LKTWorkDevList:query")
	@ResponseBody
	public ApiResult LKTWorkDevList(Integer moduleid, Integer userid) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功", lktWorkService.LKTWorkDevList(moduleid, userid));
	}

	/**
	 * 创建工单,消息通知处理人处理工单
	 */
	@PostMapping("/LKTWorkNew")
	@Permissions("workorder:LKTWorkNew:add")
	@ResponseBody
	public ApiResult LKTWorkNew(LKTWorkNewVaule lktWorkNewVaule) throws MyException {
		Integer moduleid = lktWorkNewVaule.getModuleid();
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktWorkService.LKTWorkNew(lktWorkNewVaule);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * 根据id查询未处理报警与未处理事件自动带入设备
	 * 
	 * @param moduleid
	 * @param userid
	 * @param id
	 * @param type
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/LKTWorkAllAlarmOnID")
	@Permissions("workorder:LKTWorkAllAlarmOnID:query")
	@ResponseBody
	public ApiResult LKTWorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTWorkAllAlarmOnID(moduleid, userid, id, type));
	}

	/**
	 * 工单内容查询
	 * 
	 * @param woid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("/LKTWorkDetails")
	@Permissions("workorder:LKTWorkDetails:query")
	@ResponseBody
	public ApiResult LKTWorkDetails(Integer woid, Integer userid, Integer moduleid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTWorkDetails(woid, userid, moduleid));
	}

	/**
	 * 查询已处理或挂起工单
	 * 
	 * @param moduleid
	 * @param woid
	 * @return
	 */
	@PostMapping("/LKTWorkTure")
	@Permissions("workorder:LKTWorkTure:query")
	@ResponseBody
	public ApiResult LKTWorkTure(Integer moduleid, Integer woid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTWorkTure(moduleid, woid));
	}

	/**
	 * 删除工单
	 * 
	 * @param moduleid
	 * @param userid
	 * @param woid
	 * @return
	 */
	@PostMapping("/LKTWorkDelete")
	@Permissions("workorder:LKTWorkDelete:del")
	@ResponseBody
	public ApiResult LKTWorkDelete(Integer moduleid, Integer userid, Integer woid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktWorkService.LKTWorkDelete(moduleid, userid, woid);
		if (insertObject == 203) {
			resultInfo = ApiResult.resultInfo("0", "该父工单存在未删除设备！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * 审核工单
	 * 
	 * @param remark
	 * @param audit
	 * @param woid
	 * @param moduleid
	 * @param userid
	 * @param assignid
	 * @return
	 */
	@PostMapping("/LKTWorkExamine")
	@Permissions("workorder:LKTWorkExamine:query")
	@ResponseBody
	public ApiResult LKTWorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid,
			Integer assignid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		if (remark == null || audit == null || woid == null || userid == null || assignid == null) {
			return ApiResult.resultInfo("0", "必填字段请勿留空", null);
		}
		return ApiResult.resultInfo("1", "成功！！",
				lktWorkService.LKTWorkExamine(remark, audit, woid, moduleid, userid, assignid));
	}

	/**
	 * 催促工单
	 * 
	 * @param userid
	 * @param woid
	 * @param assignid
	 * @return
	 */
	@PostMapping("/LKTReminder")
	@Permissions("workorder:LKTReminder:query")
	@ResponseBody
	public ApiResult LKTReminder(Integer userid, Integer woid, Integer assignid, String code) {
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTReminder(userid, woid, assignid, code));
	}

	/**
	 * App待处理工单列表
	 * 
	 * @param userid
	 * @param readtype
	 * @param dvenumorcode
	 * @return
	 */
	@PostMapping("/LKTWorkUntreatedApp")
	@Permissions("workorder:LKTWorkUntreatedApp:query")
	@ResponseBody
	public ApiResult LKTWorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode) {
		return ApiResult.resultInfo("1", "成功", lktWorkService.LKTWorkUntreatedApp(userid, readtype, dvenumorcode));
	}

	/**
	 * App待处理工单详情
	 * 
	 * @param moduleid
	 * @param woid
	 * @param userid
	 * @return
	 */
	@PostMapping("/LKTWorkDetailedApp")
	@Permissions("workorder:LKTWorkDetailedApp:query")
	@ResponseBody
	public ApiResult LKTWorkDetailedApp(Integer moduleid, Integer woid, Integer userid) {
		return ApiResult.resultInfo("1", "成功", lktWorkService.LKTWorkDetailedApp(moduleid, woid, userid));
	}

	/**
	 * App工单处理-维修
	 * 
	 * @param lktWorkMaintenanceAppVaule
	 * @return
	 */
	@PostMapping("/LKTWorkMaintenanceApp")
	@Permissions("workorder:LKTWorkMaintenanceApp:add")
	@ResponseBody
	public ApiResult LKTWorkMaintenanceApp(LKTWorkMaintenanceAppVaule lktWorkMaintenanceAppVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktWorkService.LKTWorkMaintenanceApp(lktWorkMaintenanceAppVaule);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * App工单处理-挂起
	 * 
	 * @param problemdesc
	 * @param woid
	 * @param pictureurl
	 * @return
	 */
	@PostMapping("/LKTWorkHangApp")
	@Permissions("workorder:LKTWorkHangApp:add")
	@ResponseBody
	public ApiResult LKTWorkHangApp(String problemdesc, Integer woid, String pictureurl) {
		ApiResult resultInfo = null;
		Integer insertObject = lktWorkService.LKTWorkHangApp(problemdesc, woid, pictureurl);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * App工单处理-更换
	 * 
	 * @param lktWorkUpdateAppVaule
	 * @return
	 */
	@PostMapping("/LKTWorkUpdateApp")
	@Permissions("workorder:LKTWorkUpdateApp:update")
	@ResponseBody
	public ApiResult LKTWorkUpdateApp(LKTWorkUpdateAppVaule lktWorkUpdateAppVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktWorkService.LKTWorkUpdateApp(lktWorkUpdateAppVaule);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * App工单详情-已处理或挂起
	 * 
	 * @param woid
	 * @param moduleid
	 * @param userid
	 * @return
	 */
	@PostMapping("/LKTWorkCompleteApp")
	@Permissions("workorder:LKTWorkCompleteApp:update")
	@ResponseBody
	public ApiResult LKTWorkCompleteApp(Integer woid, Integer moduleid, Integer userid) {
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTWorkCompleteApp(woid, moduleid, userid));
	}

	/**
	 * App已处理工单列表
	 * 
	 * @param userid
	 * @param readtype
	 * @param dvenumorcode
	 * @return
	 */
	@PostMapping("/LKTWorkCompleteListApp")
	@Permissions("workorder:LKTWorkCompleteListApp:query")
	@ResponseBody
	public ApiResult LKTWorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode) {
		return ApiResult.resultInfo("1", "成功！！", lktWorkService.LKTWorkCompleteListApp(userid, readtype, dvenumorcode));
	}
}
