package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TModule;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.ModuleService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class ModuleController {
	@Autowired
	ModuleService moduleService;
	
	/**
	 * 模块管理->所有模块
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/module/selectModules")
	public ApiResult selectModules()throws MyException{
		List<TModule> modules = moduleService.selectModuleAll();
		return ApiResult.resultInfo("1", "请求成功", modules);
	}

}
