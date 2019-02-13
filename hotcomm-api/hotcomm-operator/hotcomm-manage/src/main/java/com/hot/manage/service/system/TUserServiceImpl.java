package com.hot.manage.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.common.alarm.AlarmMapper;
import com.hot.manage.db.system.TUserMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.entity.system.TUserVo;
import com.hot.manage.entity.system.UserPageParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.Chat.ChatManagerService;
import com.hot.manage.utils.ConverUtil;
import com.hot.manage.utils.MD5Util;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TUserServiceImpl implements TUserService {

	@Autowired
	private TUserMapper userMapper;

	@Autowired
	private ChatManagerService chat;

	@Autowired
	private AlarmMapper alarmMapper;
	@Override
	public Integer insertObject(TUser params, Integer r) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertUser(TUser user) throws Exception {
		Example example=new Example(TUser.class);
		example.createCriteria()
		.orEqualTo("usernum", user.getUsernum())
		.orEqualTo("loginname", user.getLoginname());	
		example.and(example.createCriteria().andEqualTo("isenable", true));	
		List<TUser> list = userMapper.selectByExample(example);
		if (list.size()!=0) {
			throw new MyException("0","此编号或账号已存在");
		}
		if (user.getUserpicpath() == null) {
			user.setUserpicpath("/image/1531215082196.png");
		}
		user.setAddtime(ConverUtil.timeForString(new Date()));
		String encodePassword = MD5Util.encodePassword("000000", MD5Util.SALT);
		user.setPassword(encodePassword);
		int add = userMapper.insertSelective(user);
		if (add <= 0) {
			return 0;
		}
		/*TokenResult tokenResult = ChatUtil.AddChatUser(user.getId() + "", user.getContacts(), user.getUserpicpath());
		if (tokenResult.getCode() != 200) {
			throw new MyException("0", tokenResult.getMsg());
		}
		Integer chatUser = chat.AddChatUser(user.getId() + "", user.getContacts(), tokenResult.getToken());
		if (chatUser <= 0) {
			throw new MyException("0", "添加失败，请检查必填选项");
		}*/
		return 1;
	}

	@Override
	public Integer updateObject(TUser params) throws MyException {
		params.setUpdateTime(ConverUtil.timeForString(new Date()));
		int update = userMapper.updateByPrimaryKeySelective(params);
		if (update <= 0) {
			return 0;
		}
		/*TUser selectByPrimaryKey = userMapper.selectByPrimaryKey(params.getId());
		String countacts, userpicpath;
		if (params.getContacts() == null) {
			countacts = selectByPrimaryKey.getContacts();
		} else {
			countacts = params.getContacts();
		}
		if (params.getUserpicpath() == null) {
			userpicpath = selectByPrimaryKey.getUserpicpath();
		} else {
			userpicpath = selectByPrimaryKey.getUserpicpath();
		}
		try {
			Result chatUser = ChatUtil.updateChatUser(params.getId() + "", countacts, userpicpath);
			if (chatUser.getCode() != 200) {
				throw new MyException("0", chatUser.getMsg());
			}
			Integer updateChatUser = chat.UpdateChatUser(params.getId() + "", countacts);
			if (updateChatUser <= 0) {
				throw new MyException("0", "修改失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return 1;
	}

	@Override
	public Integer delObject(Integer id) throws MyException {
		alarmMapper.dropTemporaryTable();
		alarmMapper.createTemporaryTable();
		Integer selectDeviceOwn = userMapper.selectDeviceOwn(id);
		if(selectDeviceOwn!=0){
			throw new MyException("0", "该用户有绑定的设备，无法删除");
		}		
		TUser tUser = new TUser();
		tUser.setId(id);
		tUser.setIsdelete(true);
		tUser.setIsenable(false);
		int del = userMapper.updateByPrimaryKeySelective(tUser);
		if (del <= 0) {
			throw new MyException("0", "删除失败");
		}
		/*Integer integer = chat.DeleteChatUser(id + "");
		if (integer <= 0) {
			throw new MyException("0", "删除失败");

		}*/
		return 1;
	}

	@Override
	public TUserVo selectObject(Integer id) throws MyException {
		TUserVo userVo = new TUserVo();
		TUser user = userMapper.selectByPrimaryKey(id);
		BeanUtils.copyProperties(user, userVo);
		userVo.setAddtime(user.getAddtime());
		userVo.setLastlogintime(user.getLastlogintime());
		return userVo;
	}

	@Override
	public PageInfo<TUserVo> selectPage(UserPageParam param) throws MyException {
		List<TUser> info = new ArrayList<>();
		TUser user = userMapper.selectByPrimaryKey(param.getUserid());
		info.add(user);
		List<TUser> users = userMapper.selectPageInfo(user.getId(), param.getUsernum(), param.getTelephone(),
				param.getStatus());
		info.addAll(users);
		info = recursion(users, info, param);
		List<TUserVo> list = convert(info);
		PageInfo<TUserVo> pageInfo = getPageInfo(param.getPageNum(), param.getPageSize(), list);
		return pageInfo;
	}

	// 分页方法
	public PageInfo<TUserVo> getPageInfo(Integer pageNum, Integer pageSize, List<TUserVo> list) {
		List<TUserVo> ss = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if ((pageNum - 1) * pageSize <= i && i < ((pageNum - 1) * pageSize + pageSize)) {
				ss.add(list.get(i));
			}
		}
		return new PageInfo<>(pageNum, pageSize, list.size(), ss);
	}

	/// 递归查询
	public List<TUser> recursion(List<TUser> users, List<TUser> alls, UserPageParam param) {
		if (users.size() != 0) {
			for (TUser tUser : users) {
				List<TUser> nusers = userMapper.selectPageInfo(tUser.getId(), param.getUsernum(), param.getTelephone(),
						param.getStatus());
				if (nusers.size() != 0) {
					alls.addAll(nusers);
				}
				recursion(nusers, alls, param);
			}
		}
		return alls;
	}

	// 转换类型
	public static List<TUserVo> convert(List<TUser> list) {
		List<TUserVo> users = new ArrayList<>();
		if (list.size() != 0) {
			for (TUser tUser : list) {
				TUserVo vo = new TUserVo();
				BeanUtils.copyProperties(tUser, vo);
				vo.setAddtime(tUser.getAddtime());
				vo.setLastlogintime(tUser.getLastlogintime());
				users.add(vo);
			}
		}
		return users;
	}

	@Override
	public TUser selectUserById(Integer id) throws MyException {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TUser> selectListByUserId(Integer userid) throws MyException {
		List<TUser> info = new ArrayList<>();
		TUser user = userMapper.selectByPrimaryKey(userid);
		info.add(user);
		List<TUser> users = userMapper.selectByUserId(userid);
		info.addAll(users);
		info = recursion(users, info, new UserPageParam());
		return info;
	}

	// 查询子用户
	@Override
	public List<TUserVo> selectChildren(Integer userid) throws MyException {
		List<TUser> alls = new ArrayList<>();
		Example example = new Example(TUser.class);
		example.createCriteria().andEqualTo("id", userid).andEqualTo("isenable", true);
		List<TUser> list = userMapper.selectByExample(example);
		alls.addAll(list);
		Example ex = new Example(TUser.class);
		ex.createCriteria().andEqualTo("fatherid", userid).andEqualTo("isenable", true);
		List<TUser> users = userMapper.selectByExample(ex);
		alls.addAll(users);
		alls = recursion(users, alls, new UserPageParam());
		List<TUserVo> convert = convert(alls);
		return convert;
	}

	@Override
	public PageInfo<TUserVo> selectPageInfo(PageParam v) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}
}
