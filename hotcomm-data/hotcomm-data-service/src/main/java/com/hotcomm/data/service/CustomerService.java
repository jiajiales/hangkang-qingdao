package com.hotcomm.data.service;

import java.util.List;

import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.entity.sys.Member;
import com.hotcomm.data.bean.params.sys.CustomerPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberVhostParams;
import com.hotcomm.data.bean.vo.sys.CustomerVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.data.service.common.PageService;
import com.hotcomm.framework.web.exception.HKException;

public interface CustomerService extends CommFunService<MemberParams, MemberVO, Integer>, PageService<CustomerPageParams, CustomerVO> {

	void updateCustomerStates(MemberParams params) throws HKException;

	List<MemberVO> queryCustomerList(MemberParams params) throws HKException;

	public List<MemberVhost> listAuthors() throws HKException;

	void configVhost(MemberVhostParams params) throws HKException;

	void updateVhost(MemberVhostParams params) throws HKException;

	void authorVhost(Integer memberId) throws HKException;

	Member getMemberByid(Integer memebrId) throws HKException;

	MemberVhost getVhostByMemberId(Integer memberId) throws HKException;

	void forceDelCustomer(Integer id) throws HKException;

}
