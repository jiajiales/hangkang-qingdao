package com.hotcomm.data.web.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.service.data.DataPageParams;
import com.hotcomm.data.bean.vo.data.DataVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.DataService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class DataController extends BaseController implements PageController<DataPageParams, DataVO> {

	@Autowired
	private DataService dataService;

	@Function(key = "data-page")
	@RequestMapping("/data/page")
	@LogEvent(code = "DATA_PAG")
	@ParamsValidate(validateParams = { @Param(key = "page", code = "DATA_F02"),
									   @Param(key = "rows", code = "DATA_F03") })
	@Override
	public PageInfo<DataVO> page(DataPageParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setMemberId(member.getId());
		params.setMemberType(member.getUserType());
		return dataService.queryPage(params);
	}

	@Function(key = "data-view")
	@RequestMapping("/data/view")
	@LogEvent(code = "DATA_VIEW")
	@ParamsValidate(validateParams = { @Param(key = "dataId", code = "DATA_F01") })
	public ApiResult getData(Long dataId) throws HKException {
		return ApiResult.success(dataService.getData(dataId));
	}

}
