package com.hot.manage.controller.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.entity.Chat.TChatGroup;
import com.hot.manage.entity.Chat.TChatUserGroup;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.Chat.TChatGroupService;
import com.hot.manage.service.Chat.TChatUserGroupService;
import com.hot.manage.utils.ApiResult;

@RestController
public class ChatGroupController {
	
	static final String[] Type={"","","","",""};
	@Autowired
	TChatGroupService ChatGroupService;
	@Autowired
	TChatUserGroupService ChatUserGroupService;

	/**
	 * 创建群
	 * @param group
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/chatGroup/createGroup")
	public ApiResult createGroup(TChatGroup group) throws MyException {
		Integer createGroup = ChatGroupService.createGroup(group);
		if (createGroup <= 0) {
			return ApiResult.resultInfo("0", "创建群失败", null);
		} else {
			return ApiResult.resultInfo("1", "创建群成功", null);
		}
	}

	/**
	 * 修改群信息
	 * @param group
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/chatGroup/updateGroup")
	public ApiResult updateGroup(TChatGroup group) throws MyException {
		Integer updateGroup = ChatGroupService.updateGroup(group);
		if (updateGroup <= 0) {
			return ApiResult.resultInfo("0", "修改群失败", null);
		} else {
			return ApiResult.resultInfo("1", "修改群成功", null);
		}
	}
	
	/**
	 * 解散群组
	 * @param group
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/chatGroup/dismissGroup")
	public ApiResult dismissGroup(TChatGroup group) throws MyException {
		Integer dismissGroup = ChatGroupService.dismissGroup(group);
		if (dismissGroup <= 0) {
			return ApiResult.resultInfo("0", "解散群失败", null);
		} else {
			return ApiResult.resultInfo("1", "解散群成功", null);
		}
	}
	
	/**
	 * 加入群
	 * @param chatUserGroup
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/chatGroup/joinGroup")
	public ApiResult joinGroup(TChatUserGroup chatUserGroup) throws MyException {
		Integer joinGroup = ChatUserGroupService.joinGroup(chatUserGroup);
		if (joinGroup <= 0) {
			return ApiResult.resultInfo("0", "加入群失败", null);
		} else {
			return ApiResult.resultInfo("1", "加入群成功", null);
		}
	}
	
	/**
	 * 退出群
	 * @param chatUserGroup
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/chatGroup/quitGroup")
	public ApiResult quitGroup(TChatUserGroup chatUserGroup) throws MyException {
		Integer quitGroup = ChatUserGroupService.quitGroup(chatUserGroup);
		if (quitGroup <= 0) {
			return ApiResult.resultInfo("0", "退出群失败", null);
		} else {
			return ApiResult.resultInfo("1", "退出群成功", null);
		}
	}
}
