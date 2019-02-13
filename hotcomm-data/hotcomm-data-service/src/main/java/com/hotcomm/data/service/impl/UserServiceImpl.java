package com.hotcomm.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.sys.Member;
import com.hotcomm.data.bean.enums.MemberEnum;
import com.hotcomm.data.bean.enums.MemberEnum.MemberDeleteStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberType;
import com.hotcomm.data.bean.params.sys.CustomerMemberParams;
import com.hotcomm.data.bean.params.sys.MemberPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberPwdParams;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.MemberMapper;
import com.hotcomm.data.db.MemberVhostMapper;
import com.hotcomm.data.service.UserService;
import com.hotcomm.data.service.common.AbstractFunServiceImpl;
import com.hotcomm.framework.annotation.Master;
import com.hotcomm.framework.utils.CodeUtils;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class UserServiceImpl extends AbstractFunServiceImpl<MemberVO, Member> implements UserService {

	@Resource
	private MemberMapper memberMapper;

	@Resource
	private MemberVhostMapper vhostMapper;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;

	/*
	 * 用户 创建
	 */
	@Override
	public Integer addBean(MemberParams params) throws HKException {
		// 判断用户名是否已存在
		if (memberMapper.selectOne(new Member(params.getMemberName())) != null)
			throw manager.create("EC00009");

		Integer memberId = 0;
		Member member = new Member();

		params.setUserType(MemberType.SYSTEM.getValue());
		BeanUtils.copyProperties(params, member);
		member.setCreateTime(new Date());
		member.setUpdateTime(new Date());
		member.setPassword(CodeUtils.md5EncodeData(member.getPassword()));
		memberMapper.insertSelective(member);

		memberId = member.getId();
		String roles[] = params.getRoles();

		if (roles != null && roles.length > 0) {
			memberMapper.batchAddMemberRole(memberId, roles);
		}

		return memberId;
	}

	/*
	 * 用户 删除
	 */
	@Override
	public void delBean(Integer id) throws HKException {
		Member member = getMember(id);
		member.setMemberName(member.getMemberName() + "DEL@" + id);
		member.setIsDelete(MemberDeleteStatus.YES.getValue());
		member.setStatus(MemberStatus.DISABLE.getValue());
		member.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(member); // 用户记录软删除
		memberMapper.delMemberRoleByMemberId(id); // 将用户相关的角色中间记录删除[sys_member_role-物理删除]
	}

	/*
	 * 用户 更新
	 */
	@Override
	public void updateBean(MemberParams params) throws HKException {
		// 如果修改前和修改后的用户名一样,就无需判断用户名是否已存在
		if (!getMember(params.getId()).getMemberName().equals(params.getMemberName())) {
			// 判断用户名是否已存在
			if (memberMapper.selectOne(new Member(params.getMemberName())) != null)
				throw manager.create("EC00009");
		}

		Member result = new Member();

		if (params.getStatus() == null)
			params.setStatus(MemberStatus.DISABLE.getValue());

		params.setPassword(null);
		BeanUtils.copyProperties(params, result);
		result.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(result);
		String roles[] = params.getRoles();

		if (roles != null && roles.length > 0) {
			Integer memberId = params.getId();
			memberMapper.delMemberRoleByMemberId(memberId);
			memberMapper.batchAddMemberRole(memberId, roles);
		}
	}

	/*
	 * 用户 查询
	 */
	@Override
	public MemberVO getBean(Integer id) throws HKException {
		MemberVO result = new MemberVO();
		Member member = memberMapper.selectByPrimaryKey(id);

		if (member != null) {
			BeanUtils.copyProperties(member, result);
		}

		List<Integer> roles = memberMapper.getRoles(id);
		String rolesStr = "";

		if (roles != null && roles.size() > 0) {
			for (Integer r : roles) {
				rolesStr = rolesStr.concat(r.toString()).concat(",");
			}

			rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
			result.setRoles(rolesStr);
		}

		return result;
	}

	/*
	 * 用户 分页 查询
	 */
	@Override
	public PageInfo<MemberVO> queryPage(MemberPageParams params) throws HKException {
		int page = params.getPage();
		int rows = params.getRows();
		params.setStartIndex(((page - 1) * rows));
		params.setEndIndex(rows);
		List<MemberVO> volist = new ArrayList<MemberVO>();
		Long count = memberMapper.queryPageMemberCount(params);

		if (count > 0) {
			volist = memberMapper.queryPageMember(params);

			for (Iterator<MemberVO> iterator = volist.iterator(); iterator.hasNext();) {
				MemberVO vo = iterator.next();
				Integer memberId = vo.getId();
				List<String> roles = memberMapper.getRoleNames(memberId);
				String rolesStr = "";

				if (roles != null && roles.size() > 0) {
					for (String r : roles) {
						rolesStr = rolesStr.concat(r.toString()).concat(",");
					}

					rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
					vo.setRoles(rolesStr);
				}
			}
		}

		return new PageInfo<>(count, volist);
	}

	/*
	 * 客户 密码 重置
	 */
	@Master
	@Override
	@Transactional
	public void resetPwd(Integer memberId) throws HKException {
		Member member = memberMapper.selectByPrimaryKey(memberId);
		String password = CodeUtils.md5EncodeData("00000");
		member.setPassword(password);
		memberMapper.updateByPrimaryKeySelective(member);
	}

	/*
	 * 客户 密码 设置
	 */
	@Master
	@Override
	@Transactional
	public void setPwd(MemberPwdParams params) throws HKException {
		String encOldPassword = CodeUtils.md5EncodeData( params.getOldPassword());
		Member member = new Member(params.getId(), encOldPassword);

		if (memberMapper.selectOne(member) == null)
			throw manager.create("EC00003");

		String newPassword = params.getNewPassword();

		if (!newPassword.equals(params.getNewPassword2()))
			throw manager.create("EC00005");

		String encNewPassword = CodeUtils.md5EncodeData(newPassword);
		member.setPassword(encNewPassword);
		member.setUpdateTime(new Date());
		memberMapper.updateByPrimaryKeySelective(member);
	}

	@Override
	public MemberVO checkMember(MemberParams params) throws HKException {
		Member entity = new Member();
		entity.setMemberName(params.getMemberName());
		entity.setPassword(params.getPassword());
		entity.setStatus(MemberStatus.ENABLE.getValue());
		entity.setUserType(params.getUserType());
		entity.setIsDelete(MemberEnum.MemberDeleteStatus.NO.getValue());
		Member exists = memberMapper.selectOne(entity);

		if (exists == null)
			throw manager.create("EC00002");

		MemberVO vo = new MemberVO();
		BeanUtils.copyProperties(exists, vo);
		return vo;
	}

	public Member getMember(Integer memberId) throws HKException {
		Member member = null;

		if (memberId != null)
			member = memberMapper.selectOne(new Member(memberId));

		if (member == null)
			throw manager.create("EC00007");

		return member;
	}

	/*
	 * 分配客户数据权限
	 */
	@Override
	public void addCustomerMember(CustomerMemberParams params) throws HKException {
		Integer memberId = params.getMemberId();
		String customerIds = params.getCustomerIds();
		String[] customerIdsArr = customerIds.split(",");
		memberMapper.delCustomerMemberByMemberId(memberId);

		if (customerIdsArr == null || customerIdsArr.length == 0 || customerIds.length() == 0)
			return;

		memberMapper.addCustomerMember(memberId, customerIdsArr);
	}

}
