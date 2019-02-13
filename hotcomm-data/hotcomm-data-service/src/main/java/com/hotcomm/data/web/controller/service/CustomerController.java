package com.hotcomm.data.web.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.enums.MemberEnum.MemberType;
import com.hotcomm.data.bean.params.sys.CustomerPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberVhostParams;
import com.hotcomm.data.bean.vo.sys.CustomerVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.CustomerService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class CustomerController extends BaseController implements CrudController<MemberParams, Integer, MemberVO>, 
                                                                    PageController<CustomerPageParams, CustomerVO> {

	@Autowired
	private CustomerService customerService;

	@Function(key = "customer-page")
	@RequestMapping("/customer/page")
	@LogEvent(code = "CUSTOMER_PAG")
	@ParamsValidate(validateParams = { @Param(key = "page", code = "QUEUE_F05"), 
			   						   @Param(key = "rows", code = "QUEUE_F06") })
	@Override
	public PageInfo<CustomerVO> page(CustomerPageParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setLoginMemberId(member.getId());
		params.setLoginUserType(member.getUserType());
		PageInfo<CustomerVO> page = customerService.queryPage(params);
		return page;
	}

	@Function(key = "customer-list")
	@RequestMapping("/customer/list")
	@LogEvent(code = "CUSTOMER_LIST")
	public ApiResult list(MemberParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setUserType(MemberType.CUSTOMER.getValue());
		params.setId(member.getId());
		return ApiResult.success(customerService.queryCustomerList(params));
	}

	@Function(key = "customer-get")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/customer/get")
	@LogEvent(code = "CUSTOMER_GET")
	@Override
	public ApiResult get(Integer id) throws HKException {
		return ApiResult.success(customerService.getBean(id));
	}

	@Function(key = "customer-update")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01"),
									   @Param(key = "memberName", code = "MEMBER_F02"), 
									   @Param(key = "realName", code = "MEMBER_F03") })
	@RequestMapping("/customer/update")
	@LogEvent(code = "CUSTOMER_UPD")
	@Override
	public ApiResult update(MemberParams params) throws HKException {
		customerService.updateBean(params);
		return ApiResult.success();
	}

	@Function(key = "customer-del")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/customer/del")
	@LogEvent(code = "CUSTOMER_DEL")
	@Override
	public ApiResult del(Integer id) throws HKException {
		customerService.delBean(id);
		return ApiResult.success();
	}

	@Function(key = "customer-force-del")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/customer/forceDel")
	@LogEvent(code = "CUSTOMER_FORCE_DEL")
	public ApiResult forceDel(Integer id) throws HKException {
		customerService.forceDelCustomer(id);
		return ApiResult.success();
	}

	@Function(key = "customer-add")
	@ParamsValidate(validateParams = { @Param(key = "memberName", code = "MEMBER_F02"),
									   @Param(key = "password", code = "MEMBER_F04"),
									   @Param(key = "realName", code = "MEMBER_F03") })
	@RequestMapping("/customer/add")
	@LogEvent(code = "CUSTOMER_ADD")
	@Override
	public ApiResult add(MemberParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setCreateUser(member.getMemberName());
		params.setUserType(MemberType.CUSTOMER.getValue());
		params.setCreateUserId(member.getId());
		customerService.addBean(params);
		return ApiResult.success();
	}

	@Function(key = "customer-update-status")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/customer/update/status")
	@LogEvent(code = "CUSTOMER_UPD_STATUS")
	public ApiResult updateCustomerStates(MemberParams params) throws HKException {
		customerService.updateCustomerStates(params);
		return ApiResult.success();
	}

	@Function(key = "customer-vhost-config")
	@ParamsValidate(validateParams = { @Param(key = "memberId", code = "VHOST_F01"),
									   @Param(key = "vhost", code = "VHOST_F02"),
									   @Param(key = "vhostAccount", code = "VHOST_F03"),
									   @Param(key = "vhostPassword", code = "VHOST_F04") })
	@RequestMapping("/customer/vhost/config")
	@LogEvent(code = "CUSTOMER_VHOST_CONFIG")
	public ApiResult configVhost(MemberVhostParams params) throws HKException {
		customerService.configVhost(params);
		return ApiResult.success();
	}

	@Function(key = "customer-vhost-config-update")
	@ParamsValidate(validateParams = { @Param(key = "memberId", code = "VHOST_F01"),
									   @Param(key = "vhost", code = "VHOST_F02"),
									   @Param(key = "vhostAccount", code = "VHOST_F03"),
									   @Param(key = "vhostPassword", code = "VHOST_F04") })
	@RequestMapping("/customer/vhost/config/update")
	@LogEvent(code = "CUSTOMER_VHOST_CONFIG_UPDATE")
	public ApiResult updateVhost(MemberVhostParams params) throws HKException {
		customerService.updateVhost(params);
		return ApiResult.success();
	}

	@Function(key = "customer-vhost-config-author")
	@ParamsValidate(validateParams = { @Param(key = "memberId", code = "VHOST_F01") })
	@RequestMapping("/customer/vhost/config/author")
	@LogEvent(code = "CUSTOMER_VHOST_CONFIG_AUTHOR")
	public ApiResult authorVhost(Integer memberId) throws HKException {
		customerService.authorVhost(memberId);
		return ApiResult.success();
	}

}
