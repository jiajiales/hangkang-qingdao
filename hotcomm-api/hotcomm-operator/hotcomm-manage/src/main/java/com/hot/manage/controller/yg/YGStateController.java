package com.hot.manage.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.TAlarmState;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.TAlarmStateService;
import com.hot.manage.utils.ApiResult;

@RestController
public class YGStateController {

	@Autowired
	private TAlarmStateService trAlarmStateService;

	@PostMapping("YGDevice/deleteAlarmState")
	@Permissions("item:yg:del:deleteState")
	// ("根据id删除状态")
	public ApiResult deleteAlarmState(Integer userid, Integer id) throws MyException {
		Integer i = trAlarmStateService.deleteOne(id);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除成功！！",null);
		}
		return resultInfo;

	}

	@PostMapping("YGDevice/updateAlarmState")
	@Permissions("item:yg:update:updateState")
	// ("修改状态")
	public ApiResult updateAlarmState(Integer userid, TAlarmState talarmstate) throws MyException {
		Integer i = trAlarmStateService.updateOne(talarmstate);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改成功！！",null);
		}
		return resultInfo;

	}

	@PostMapping("YGDevice/insertAlarmState")
	@Permissions("item:yg:add:insertState")
	// ("添加状态")
	public ApiResult insertAlarmState(Integer userid, TAlarmState talarmstate) throws MyException {
		talarmstate.setIsdelete(0);
		talarmstate.setModule_id(2);
		Integer i = trAlarmStateService.insertOne(talarmstate);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("YGDevice/selectAllForPage")
	@Permissions("item:yg:read:AllState")
	// ("查询所有处理状态")
	public ApiResult selectAllForPage(Integer userid, Integer pageNum, Integer pageSize)
			throws MyException {
		Page<TAlarmState> page = trAlarmStateService.findByPage(pageNum, pageSize);
		PageInfo<TAlarmState> pageinfo = new PageInfo<TAlarmState>(page.getPageNum(), page.getPageSize(),
				page.getTotal(), page);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,pageinfo);
		return resultInfo;
	}

	@PostMapping("YGDevice/selectById")
	@Permissions("item:yg:read:selectById")
	// ("根据id查询报警/事件状态")
	public ApiResult selectById(Integer userid, Integer id) throws MyException {
		TAlarmState tAlarmState = trAlarmStateService.selectById(id);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,tAlarmState);
		return resultInfo;

	}

	@PostMapping("YGDevice/selectAlarmStateAll")
	@Permissions("item:yg:read:AllState")
	public ApiResult selectAlarmStateAll(Integer userid) throws MyException {
		List<TAlarmState> list = trAlarmStateService.SelectAll();
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,list);
		return resultInfo;

	}

}
