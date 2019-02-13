package com.hot.manage.service.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.manage.db.Chat.TChatGroupMapper;
import com.hot.manage.entity.Chat.TChatGroup;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.chat.RongCloudUtil;
import io.rong.methods.group.Group;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import tk.mybatis.mapper.entity.Example;

@Service
public class TChatGroupServiceImpl implements TChatGroupService {
	@Autowired
	TChatGroupMapper ChatGroupMapper;

	@Transactional
	@Override
	public Integer createGroup(TChatGroup ChatGroup) throws MyException {
		int insertSelective = ChatGroupMapper.insertSelective(ChatGroup);
		if (insertSelective <= 0) {
			return 0;
		}
		// 融云创建组
		Group group = RongCloudUtil.getRongCloud().group;
		GroupModel model = new GroupModel();
		model.setId(ChatGroup.getId().toString());
		model.setName(ChatGroup.getGroupname());
		GroupMember[] members = { new GroupMember().setId(ChatGroup.getGroupUserid().toString()) };
		model.setMembers(members);
		Result groupCreateResult = null;
		try {
			groupCreateResult = (Result) group.create(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (groupCreateResult.code == 200) {
			return 1;
		} else {
			throw new MyException("0", "创建群组失败");
		}
	}

	@Transactional
	@Override
	public Integer updateGroup(TChatGroup chatGroup) throws MyException {
		int update = ChatGroupMapper.updateByPrimaryKeySelective(chatGroup);
		if (update <= 0) {
			return 0;
		}
		// 同步修改的信息到融云
		Group group = RongCloudUtil.getRongCloud().group;
		GroupModel model = new GroupModel();
		model.setId(chatGroup.getId().toString());
		model.setName(chatGroup.getGroupname());
		Result refreshResult = null;
		try {
			refreshResult = (Result) group.update(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (refreshResult.code == 200) {
			return 1;
		} else {
			throw new MyException("0", "修改群组失败");
		}
	}

	@Transactional
	@Override
	public Integer dismissGroup(TChatGroup chatGroup) throws MyException {
		TChatGroup group = ChatGroupMapper.selectByPrimaryKey(chatGroup.getId());
		if (group == null) {
			throw new MyException("0", "此群组不存在");
		}
		Example example = new Example(TChatGroup.class);
		example.createCriteria().andEqualTo("id", chatGroup.getId()).andEqualTo("isdelete", false);
		chatGroup.setIsdelete(true);
		int update = ChatGroupMapper.updateByExampleSelective(chatGroup, example);
		if (update <= 0) {
			throw new MyException("0", "此群组解散失败");
		}
		// 同步解散群组到融云
		Group groups = RongCloudUtil.getRongCloud().group;
		GroupMember[] members = { new GroupMember().setId(chatGroup.getGroupUserid().toString()) };
		GroupModel model = new GroupModel().setId(chatGroup.getId().toString()).setMembers(members);
		Result groupDismissResult = null;
		try {
			groupDismissResult = (Result) groups.dismiss(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (groupDismissResult.code == 200) {
			return 1;
		} else {
			throw new MyException("0", "此群组解散失败");
		}
	}

}
