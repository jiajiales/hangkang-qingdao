package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.CommonController;
import com.hot.manage.entity.MenuCategoryNode;
import com.hot.manage.entity.system.TPower;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TPowerService;
import com.hot.manage.utils.ApiResult;

@RestController
public class ResourceController implements CommonController<TPower, TPower, Integer> {
	@Autowired
	private TPowerService powerService;

	@PostMapping("/Resource/insert")
	@Permissions("resource:add")
	@Override
	public ApiResult insert(TPower power, Integer userid) throws MyException {
		ApiResult resultInfo = null;
		Integer insert = powerService.insertObject(power, userid);
		if (insert <= 0) {
			resultInfo = ApiResult.resultInfo("0", "资源添加失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "资源添加成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/Resource/update")
	@Permissions("resource:update")
	@Override
	public ApiResult update(TPower power) throws MyException {
		ApiResult resultInfo = null;
		Integer update = powerService.updateObject(power);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/Resource/delete")
	@Permissions("resource:del")
	@Override
	public ApiResult delete(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer del = powerService.delObject(id);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除成功！！", null);
		}
		return resultInfo;
	}

	@Override
	public ApiResult select(Integer r, Integer b) throws MyException {
		return null;
	}

	/**
	 * 页面资源节点
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/selectNode")
	@Permissions("resource:selectNode:query")
	public ApiResult selectNode(Integer userid) throws MyException {
		List<MenuCategoryNode> selectAll = powerService.selectAll(userid);
		return ApiResult.resultInfo("1", "成功", selectAll);
	}
	
	/**
	 * 查询某个用户的资源
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/selectPower")
	@Permissions("resource:selectPower:query")
	public ApiResult selectPower(Integer userid) throws MyException {
		List<TPower> selectAllPower = powerService.selectAllPower(userid);
		return ApiResult.resultInfo("1", "成功", selectAllPower);
	}

	/**
	 * 根据资源ID，查询资源信息(补)
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/Resource/selectInfo")
	@Permissions("resource:selectInfo:query")
	public ApiResult selectInfo(Integer id) {
		return ApiResult.resultInfo("1", "成功", powerService.selectInfo(id));
	}

	/**
	 * 根据fatherid查询资源
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/Resource/selectByFatherid")
	@Permissions("resource:selectByFatherid:query")
	public ApiResult selectByFatherid(Integer fatherid) throws MyException {
		return ApiResult.resultInfo("1", "成功", powerService.selectByFatherid(fatherid));
	}

}
