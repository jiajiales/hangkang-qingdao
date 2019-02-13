package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.CommonController;
import com.hot.manage.entity.system.TModule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TModuleService;
import com.hot.manage.utils.ApiResult;

@RestController
public class ModuleController implements CommonController<TModule, TModule, Integer>{

	@Autowired
	private TModuleService moduleService;
	
	@Override
	public ApiResult insert(TModule module,Integer userid) throws MyException {
		return null;
	}
	
	@PostMapping("/module/insert")
	@Permissions("module:insert:add")
	public ApiResult Insert(TModule module) throws MyException{
		ApiResult resultInfo = null;
		Integer add = moduleService.insertModule(module);
		if (add <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加模块失败",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "模块添加成功",null);
		}
		return resultInfo;
	}
	
	@PostMapping("/module/update")
	@Permissions("module:update")
	@Override
	public ApiResult update(TModule module) throws MyException {
		ApiResult resultInfo = null;
		Integer update = moduleService.updateObject(module);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改模块失败",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "模块修改成功",null);
		}
		return resultInfo;
	}
	
	@PostMapping("/module/delete")
	@Permissions("module:del")
	@Override
	public ApiResult delete(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer del = moduleService.delObject(id);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除模块失败",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "模块删除成功",null);
		}
		return resultInfo;
	}

	@Override
	public ApiResult select(Integer r, Integer b) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PostMapping("/module/page")
	@Permissions("module:page:query")
	public ApiResult page() throws MyException {
		List<TModule> selectListTModule = moduleService.selectListTModule();
		return ApiResult.resultInfo("1", "成功", selectListTModule);
	}
	
	@PostMapping("/module/selectModuleById")
	@Permissions("module:selectModuleById:query")
	public ApiResult selectModuleById(Integer id) throws MyException {
		TModule selectObject = moduleService.selectObject(id);
		return ApiResult.resultInfo("1", "成功", selectObject);
	}
	
	/**
	 * 当前用户拥有的模块
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/module/selectModuleByUserId")
	@Permissions("module:selectModuleByUserId:query")
	public ApiResult selectModuleByUserId(Integer userid) throws MyException {
		List<TModule> selectModuleByUserId = moduleService.selectModuleByUserId(userid);
		return ApiResult.resultInfo("1", "成功", selectModuleByUserId);
	}

}
