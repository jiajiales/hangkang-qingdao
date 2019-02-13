package com.hotcomm.data.service;

import com.hotcomm.data.bean.params.sys.CustomerMemberParams;
import com.hotcomm.data.bean.params.sys.MemberPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberPwdParams;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.framework.web.exception.HKException;

public interface UserService extends CommFunService<MemberParams, MemberVO, Integer> {

	void resetPwd(Integer memberId) throws HKException;

	MemberVO checkMember(MemberParams params) throws HKException;

	void setPwd(MemberPwdParams params) throws HKException;

	void addCustomerMember(CustomerMemberParams params) throws HKException;

	PageInfo<MemberVO> queryPage(MemberPageParams params);

}
