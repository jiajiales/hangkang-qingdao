package com.hot.manage.utils.chat;

import static org.junit.Assert.assertEquals;
import io.rong.RongCloud;
import io.rong.methods.group.Group;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.group.UserGroup;
import io.rong.models.response.GroupUserQueryResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;

public class ChatUtil {

	private static final String appSecret = "mFMmN6qPUY";
	private static final String appKey = "qd46yzrfqiz8f";

	/**
	 * 添加聊天用户
	 * @param userid
	 * @param username
	 * @param portrait
	 * @return
	 * @throws Exception
	 */
	public static TokenResult AddChatUser(String userid, String username, String portrait) throws Exception {
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		User User = rongCloud.user;
		UserModel user = new UserModel();
		user.setId(userid);
		user.setName(username);
		user.setPortrait(portrait);
		TokenResult result = User.register(user);
		return result;
	}

	/**
	 * 修改用户
	 * @param userid
	 * @param username
	 * @param portrait
	 * @return
	 * @throws Exception
	 */
	public static Result updateChatUser(String userid, String username, String portrait) throws Exception {
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		User User = rongCloud.user;
		UserModel user = new UserModel();
		user.setId(userid);
		user.setName(username);
		Result result = User.update(user);
		return result;
	}

	/**
	 * 创建群组
	 * @param groupid
	 * @param members
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public static Result createGroup(String groupid, GroupMember[] members, String groupName) throws Exception {
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		GroupModel group = new GroupModel().setId(groupid).setMembers(members).setName(groupName);
		Result groupCreateResult = (Result) Group.create(group);
		System.out.println("group create result:  " + groupCreateResult.toString());
		return groupCreateResult;
	}

	/**
	 * 更新群组
	 * @param chatuserid
	 * @param groups
	 * @return
	 * @throws Exception
	 */
	public static Result GroupSync(String chatuserid,GroupModel[] groups) throws Exception {
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		UserGroup user = new UserGroup().setId(chatuserid).setGroups(groups);
		Result syncResult = (Result) Group.sync(user);
		System.out.println("group sync:  " + syncResult.toString());
		return syncResult;
	}
	
	/**
	 * 获取群消息
	 * @param groupid 群id
	 * @return
	 * @throws Exception
	 */
	public static GroupUserQueryResult GetGroupInfo(String groupid)throws Exception{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		GroupModel  group = new GroupModel().setId(groupid);
		GroupUserQueryResult getMemberesult = Group.get(group);
		System.out.println("group getMember:  " + getMemberesult.toString());
		return getMemberesult;
	}
	
	/**
	 * 修改群信息
	 * @param groupid 群id
	 * @param groupname 群名称
	 * @return
	 * @throws Exception
	 */
	public static Result GroupUpdate(String groupid,String groupname)throws Exception{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
//		GroupMember[] members = {new GroupMember().setId("ghJiu7H1"),new GroupMember().setId("ghJiu7H2")};
		GroupModel group = new GroupModel()
				.setId(groupid)
				.setName(groupname);
		Result result = (Result)rongCloud.group.update(group);
		System.out.println("refresh:  " + result.toString());

		assertEquals("200",result.getCode().toString());
		return result;
	}
	
	/**
	 * 加入群组
	 * @param groupid
	 * @param members
	 * @return
	 * @throws Exception
	 */
	public static Result JoinGroup(String groupid,GroupMember[] members,String groupName)throws Exception{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		GroupModel group = new GroupModel()
				.setId(groupid)
				.setMembers(members)
				.setName(groupName);
		Result result = (Result)rongCloud.group.join(group);
		System.out.println("join:  " + result.toString());

		assertEquals("200",result.getCode().toString());
		return  result;
	}
	
	/**
	 * 退出群组
	 * @param groupid 群id
	 * @param members	群成员
	 * @param groupName	群名称
	 * @return
	 * @throws Exception
	 */
	public static Result QuitGroup(String groupid,GroupMember[] members,String groupName)throws Exception{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		GroupModel group = new GroupModel()
				.setId(groupid)
				.setMembers(members)
				.setName(groupName);
		Result result = (Result)rongCloud.group.quit(group);
		System.out.println("quit:  " + result.toString());

		assertEquals("200",result.getCode().toString());
		return result;
	}
	
	/**
	 * 解散群组
	 * @param groupid  群id
	 * @param members	群成员（群主）
	 * @return
	 * @throws Exception
	 */
	public static Result DisMissGroup(String groupid,GroupMember[] members)throws Exception{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		Group Group = rongCloud.group;
		GroupModel group = new GroupModel()
				.setId(groupid)
				.setMembers(members);
		Result result = (Result)rongCloud.group.dismiss(group);
		System.out.println("groupDismissResult:  " + result.toString());

		assertEquals("200",result.getCode().toString());
		return result;
	}
}
