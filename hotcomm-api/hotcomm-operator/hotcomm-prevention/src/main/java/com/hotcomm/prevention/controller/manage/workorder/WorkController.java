package com.hotcomm.prevention.controller.manage.workorder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDevnum;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkListFather;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkMyproject;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkListcondition;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkMaintenanceAppVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkNewVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkUpdateAppVaule;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.workorder.WorkService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
@RequestMapping("workorder")
public class WorkController {

	@Autowired
	private WorkService WorkService;

	/**
	 * 工单地图设备总数
	 * 
	 * @param moduleid
	 * @param useridf
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/WorkDevnum")
	public ApiResult WorkDevnum(Integer moduleid, Integer userid) throws MyException {
		WorkDevnum WorkDevnum = WorkService.WorkDevnum(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", WorkDevnum);
	}

	/**
	 * 查询地图项目信息，工单数
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/WorkMyproject")
	public ApiResult WorkMyproject(Integer moduleid, Integer userid) throws MyException {
		List<WorkMyproject> WorkMyprojects = WorkService.WorkMyproject(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", WorkMyprojects);
	}

	/**
	 * 工单列表（可按项目组查）
	 * 
	 * @param WorkListcondition
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/WorkListFather")
	public ApiResult WorkListFather(WorkListcondition WorkListcondition) throws MyException {
		Integer moduleid = WorkListcondition.getModuleid();
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		PageHelper.startPage(WorkListcondition.getPageNum(), WorkListcondition.getPageSize());
		Page<WorkListFather> page = WorkService.WorkListFather(WorkListcondition);
		List<WorkListFather> list = ConverUtil.converPage(page, WorkListFather.class);
		PageInfo<WorkListFather> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
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
	@PostMapping("/WorkAllAlarm")
	public ApiResult WorkAllAlarm(Integer moduleid, Integer userid) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功", WorkService.WorkAllAlarm(moduleid, userid));
	}

	/**
	 * 故障设备列表查询-带出关联的报警和事件
	 * 
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/WorkDevList")
	public ApiResult WorkDevList(Integer moduleid, Integer userid) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功", WorkService.WorkDevList(moduleid, userid));
	}

	/**
	 * 创建工单,消息通知处理人处理工单
	 */
	@PostMapping("/WorkNew")
	public ApiResult WorkNew(WorkNewVaule WorkNewVaule) throws MyException {
		Integer moduleid = WorkNewVaule.getModuleid();
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		ApiResult resultInfo = null;
		Integer insertObject = WorkService.WorkNew(WorkNewVaule);
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
	@PostMapping("/WorkAllAlarmOnID")
	public ApiResult WorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type) throws MyException {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", WorkService.WorkAllAlarmOnID(moduleid, userid, id, type));
	}

	/**
	 * 工单内容查询
	 * 
	 * @param woid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("/WorkDetails")
	public ApiResult WorkDetails(Integer woid, Integer userid, Integer moduleid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 8 & moduleid != 9 & moduleid != 10
				& moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15 & moduleid != 16
				& moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", WorkService.WorkDetails(woid, userid, moduleid));
	}

	/**
	 * 查询已处理或挂起工单
	 * 
	 * @param moduleid
	 * @param woid
	 * @return
	 */
	@PostMapping("/WorkTure")
	public ApiResult WorkTure(Integer moduleid, Integer woid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		return ApiResult.resultInfo("1", "成功！！", WorkService.WorkTure(moduleid, woid));
	}

	/**
	 * 删除工单
	 * 
	 * @param moduleid
	 * @param userid
	 * @param woid
	 * @return
	 */
	@PostMapping("/WorkDelete")
	public ApiResult WorkDelete(Integer moduleid, Integer userid, Integer woid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		ApiResult resultInfo = null;
		Integer insertObject = WorkService.WorkDelete(moduleid, userid, woid);
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
	@PostMapping("/WorkExamine")
	public ApiResult WorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid,
			Integer assignid) {
		if (moduleid != 1 & moduleid != 2 & moduleid != 3 & moduleid != 4 & moduleid != 8 & moduleid != 9
				& moduleid != 10 & moduleid != 11 & moduleid != 12 & moduleid != 13 & moduleid != 14 & moduleid != 15
				& moduleid != 16 & moduleid != 17) {
			return ApiResult.resultInfo("0", "模块id输入有误", null);
		}
		if (remark == null || audit == null || woid == null || userid == null || assignid == null) {
			return ApiResult.resultInfo("0", "必填字段请勿留空", null);
		}
		return ApiResult.resultInfo("1", "成功！！",
				WorkService.WorkExamine(remark, audit, woid, moduleid, userid, assignid));
	}

	/**
	 * 催促工单
	 * 
	 * @param userid
	 * @param woid
	 * @param assignid
	 * @return
	 */
	@PostMapping("/Reminder")
	public ApiResult Reminder(Integer userid, Integer woid, Integer assignid, String code) {
		return ApiResult.resultInfo("1", "成功！！", WorkService.Reminder(userid, woid, assignid, code));
	}

	/**
	 * App待处理工单列表
	 * 
	 * @param userid
	 * @param readtype
	 * @param dvenumorcode
	 * @return
	 */
	@PostMapping("/WorkUntreatedApp")
	public ApiResult WorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode, Integer moduleid) {
		return ApiResult.resultInfo("1", "成功", WorkService.WorkUntreatedApp(userid, readtype, dvenumorcode, moduleid));
	}

	/**
	 * App待处理工单详情
	 * 
	 * @param moduleid
	 * @param woid
	 * @param userid
	 * @return
	 */
	@PostMapping("/WorkDetailedApp")
	public ApiResult WorkDetailedApp(Integer moduleid, Integer woid, Integer userid) {
		return ApiResult.resultInfo("1", "成功", WorkService.WorkDetailedApp(moduleid, woid, userid));
	}

	/**
	 * App工单处理-维修
	 * 
	 * @param WorkMaintenanceAppVaule
	 * @return
	 */
	@PostMapping("/WorkMaintenanceApp")
	public ApiResult WorkMaintenanceApp(WorkMaintenanceAppVaule WorkMaintenanceAppVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = WorkService.WorkMaintenanceApp(WorkMaintenanceAppVaule);
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
	@PostMapping("/WorkHangApp")
	public ApiResult WorkHangApp(String problemdesc, Integer woid, String pictureurl) {
		ApiResult resultInfo = null;
		Integer insertObject = WorkService.WorkHangApp(problemdesc, woid, pictureurl);
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
	 * @param WorkUpdateAppVaule
	 * @return
	 */
	@PostMapping("/WorkUpdateApp")
	public ApiResult WorkUpdateApp(WorkUpdateAppVaule WorkUpdateAppVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = WorkService.WorkUpdateApp(WorkUpdateAppVaule);
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
	@PostMapping("/WorkCompleteApp")
	public ApiResult WorkCompleteApp(Integer woid, Integer moduleid, Integer userid) {
		return ApiResult.resultInfo("1", "成功！！", WorkService.WorkCompleteApp(woid, moduleid, userid));
	}

	/**
	 * App已处理工单列表
	 * 
	 * @param userid
	 * @param readtype
	 * @param dvenumorcode
	 * @return
	 */
	@PostMapping("/WorkCompleteListApp")
	public ApiResult WorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode, Integer moduleid) {
		return ApiResult.resultInfo("1", "成功！！",
				WorkService.WorkCompleteListApp(userid, readtype, dvenumorcode, moduleid));
	}
}
