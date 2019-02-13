package com.hotcomm.prevention.controller.manage.patrol;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolParams;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolPersonVo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.patrol.PatrolListService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("patrol")
public class PatrolController {
	@Autowired
	private PatrolListService patrolListService;

	/**
	 * 添加巡检人员
	 * 
	 * @param id
	 *            添加用户ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/insertPatrol")
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
	 * @param id
	 *            巡检人员ID
	 * @param isenable
	 *            0：解冻；1：冻结
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/updatePatrol")
	public ApiResult updatePatrol(Integer id, Integer isenable) throws MyException {
		ApiResult resultInfo = null;
		Integer updatePatrol = patrolListService.updatePatrol(id, isenable);
		if (updatePatrol <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功", null);
		}
		return resultInfo;
	}

	/**
	 * 删除巡检人员
	 * 
	 * @param id
	 *            用户与巡检人员关联表ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/delPatrol")
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
	 * 
	 * @param patrolid
	 *            原巡检人员ID
	 * @param id
	 *            要添加的人员ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/replacePatrol")
	public ApiResult replacePatrol(Integer id, Integer patrolid) throws MyException {
		Integer replace = patrolListService.replacePatrol(patrolid, id);
		if (replace <= 0) {
			return ApiResult.resultInfo("0", "更换失败", null);
		}
		return ApiResult.resultInfo("1", "更换成功", null);
	}

	/**
	 * 巡检人员列表
	 * 
	 * @param params
	 *            分页查询参数对象
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectPatrolPageInfo")
	public ApiResult selectPatrolPageInfo(PatrolParams params) throws MyException {
		PageInfo<PatrolPersonVo> selectPageInfo = patrolListService.selectPageInfo(params);
		return ApiResult.resultInfo("1", "成功", selectPageInfo);
	}

	/**
	 * 查询当前用户下的巡检人员
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectAllPatrol")
	public ApiResult selectAllPatrol(Integer userid) throws MyException {
		List<TUserVo> selectAllPatrol = patrolListService.selectAllPatrol(userid);
		return ApiResult.resultInfo("1", "成功", selectAllPatrol);
	}
}
