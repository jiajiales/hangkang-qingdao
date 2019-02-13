package com.hot.manage.controller.common.patrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.entity.common.patrol.PatrolPersonVo;
import com.hot.manage.entity.system.TUserVo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.patrol.PatrolListService;
import com.hot.manage.utils.ApiResult;

@RestController
public class PatrolController {
	@Autowired
	private PatrolListService patrolListService;

	/**
	 * 添加巡检人员
	 * @param id 添加用户ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/insertPatrol")
	@Permissions("patrol:insertPatrol:add")
	public ApiResult insertPatrol(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer insertPatrol = patrolListService.insertPatrol(id);
		if (insertPatrol <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加失败", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加成功", null);
		}
		return resultInfo;
	}

	/**
	 * 
	 * @param id 巡检人员ID
	 * @param isenable 0：解冻；1：冻结
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/updatePatrol")
	@Permissions("patrol:updatePatrol:update")
	public ApiResult updatePatrol(Integer id,Integer isenable) throws MyException {
		ApiResult resultInfo = null;
		Integer updatePatrol = patrolListService.updatePatrol(id,isenable);
		if (updatePatrol <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功", null);
		}
		return resultInfo;
	}

	/**
	 * 删除巡检人员
	 * @param id 用户与巡检人员关联表ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/delPatrol")
	@Permissions("patrol:delPatrol:del")
	public ApiResult delPatrol(Integer patrolsid) throws MyException {
		ApiResult resultInfo = null;
		Integer delPatrol = patrolListService.delPatrol(patrolsid);
		if (delPatrol <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除失败", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除成功", null);
		}
		return resultInfo;
	}
	
	/**
	 * 更换巡检人员
	 * @param patrolid 原巡检人员ID
	 * @param id 要添加的人员ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/replacePatrol")
	@Permissions("patrol:replacePatrol:update")
	public ApiResult replacePatrol(Integer id,Integer patrolid)throws MyException{
		Integer replace = patrolListService.replacePatrol(patrolid, id);
		if (replace<=0) {
			return ApiResult.resultInfo("0", "更换失败", null);
		}
		return ApiResult.resultInfo("1", "更换成功", null);	
	}

	/**
	 * 巡检人员列表
	 * @param params 分页查询参数对象
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectPatrolPageInfo")
	@Permissions("patrol:selectPatrolPageInfo:query")
	public ApiResult selectPatrolPageInfo(PatrolParams params) throws MyException {
		PageInfo<PatrolPersonVo> selectPageInfo = patrolListService.selectPageInfo(params);
		return ApiResult.resultInfo("1", "成功", selectPageInfo);
	}
	
	/**
	 * 查询当前用户下的巡检人员
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectAllPatrol")
	@Permissions("patrol:selectAllPatrol:query")
	public ApiResult selectAllPatrol(Integer userid) throws MyException {
		List<TUserVo> selectAllPatrol = patrolListService.selectAllPatrol(userid);
		return ApiResult.resultInfo("1", "成功", selectAllPatrol);
	}
}
