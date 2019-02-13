package com.hot.manage.service.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.Chat.TChatGroupMapper;
import com.hot.manage.db.Chat.TChatUserGroupMapper;
import com.hot.manage.entity.Chat.TChatGroup;
import com.hot.manage.entity.Chat.TChatUserGroup;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.chat.RongCloudUtil;

import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;

@Service
public class TChatUserGroupServiceImpl implements TChatUserGroupService {
	@Autowired
	TChatUserGroupMapper ChatUserGroupMapper;
	@Autowired
	TChatGroupMapper ChatGroupMapper;
	/**
	 * 加入群
	 */
	@Transactional
	@Override
	public Integer joinGroup(TChatUserGroup chatUserGroup) throws MyException {
		TChatGroup chatGroup = ChatGroupMapper.selectByPrimaryKey(chatUserGroup.getGroupid());
		if (chatGroup==null||chatGroup.getIsdelete()==true) {
			throw new MyException("0","此群不存在");
		}
		int insert = ChatUserGroupMapper.insertSelective(chatUserGroup);
		if (insert<=0) {
			return 0;
		}
		//同步融云服务器数据
		GroupMember[] members={new GroupMember().setId(chatUserGroup.getUserid().toString())};
		GroupModel model=new GroupModel().setId(chatUserGroup.getGroupid().toString()).setMembers(members);
		Result groupJoinResult=null;
		try {
			groupJoinResult = (Result)RongCloudUtil.getRongCloud().group.join(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (groupJoinResult.code==200) {
			return 1;
		}else {
			throw new MyException("0","加入群组失败");
		}
	}

	/**
	 * 退出群
	 */
	@Transactional
	@Override
	public Integer quitGroup(TChatUserGroup chatUserGroup) throws MyException {
		TChatUserGroup userGroup = ChatUserGroupMapper.selectByPrimaryKey(chatUserGroup.getId());
		if (userGroup==null) {
			throw new MyException("0","此用户不在群组内");
		}
		chatUserGroup.setIsdelete(true);
		int update = ChatUserGroupMapper.updateByPrimaryKeySelective(chatUserGroup);
		if (update<=0) {
			return 0;
		}
		//同步融云服务器数据
		GroupMember[] members={new GroupMember().setId(chatUserGroup.getUserid().toString())};
		GroupModel model=new GroupModel().setId(chatUserGroup.getGroupid().toString()).setMembers(members);
		Result groupQuitResult =null;
		try {
			groupQuitResult = (Result)RongCloudUtil.getRongCloud().group.quit(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (groupQuitResult.code==200) {
			return 1;
		}else {
			throw new MyException("0","退出群组失败");
		}
	}

}
