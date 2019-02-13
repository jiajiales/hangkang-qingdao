package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.sys.Customer;
import com.hotcomm.data.bean.entity.sys.Member;
import com.hotcomm.data.bean.params.sys.CustomerPageParams;
import com.hotcomm.data.bean.params.sys.MemberPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.vo.sys.MemberVO;

import tk.mybatis.mapper.common.Mapper;

public interface MemberMapper extends Mapper<Member> {

	Page<MemberVO> queryPageMember(@Param("params") MemberPageParams params);

	Long queryPageMemberCount(@Param("params") MemberPageParams params);

	Page<Customer> queryPageCustomer(@Param("params") CustomerPageParams params);

	List<MemberVO> queryCustomerList(@Param("params") MemberParams params);

	void batchAddMemberRole(@Param("memberId") Integer memberId, @Param("roles") String[] roles);

	void delMemberRoleByMemberId(@Param("memberId") Integer memberId);

	List<Integer> getRoles(@Param("memberId") Integer memberId);

	List<String> getRoleNames(@Param("memberId") Integer memberId);

	Integer checkDeviceGroupRelation(@Param("memberId") Integer memberId);

	void addCustomerMember(@Param("memberId") Integer memberId, @Param("customerIds") String[] customerIds);

	void delCustomerMemberByMemberId(@Param("memberId") Integer memberId);

	void delCustomerMemberByCustomerId(@Param("customerId") Integer customerId);

}
