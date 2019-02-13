package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.params.sys.MemberVhostParams;

import tk.mybatis.mapper.common.Mapper;

public interface MemberVhostMapper extends Mapper<MemberVhost> {

	List<MemberVhost> listActive();

	void batchAddMemberVhost(@Param("memberId") Integer memberId, @Param("vhostStatus") Integer vhostStatus);

	void updateVhost(@Param("params") MemberVhostParams params);

	MemberVhost getVhostByMemberId(@Param("memberId") Integer memberId);

	String getVhostCodeByMemberId(@Param("memberId") Integer memberId);

}
