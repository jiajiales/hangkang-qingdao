package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.common.params.UserPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.db.mysql.common.TUserMapper;
import com.hotcomm.prevention.db.mysql.manage.devicemanager.DeviceMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.MD5Util;
import com.hotcomm.prevention.utils.TextUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	TUserMapper userMapper;
	@Autowired
	DeviceMapper deviceMapper;

	/**
	 * 用户登陆
	 */
	@Override
	public TUser selectByUsername(String username) throws MyException {
		Example example = new Example(TUser.class);
		example.createCriteria().andEqualTo("loginname", username);
		List<TUser> users = userMapper.selectByExample(example);
		if (users.size() != 0) {
			return users.get(0);
		}
		return null;
	}

	/**
	 * 1、系统设置->用户管理->修改用户 
	 * 2、登陆接口->修改用户最后登陆时间和IP
	 */
	@Override
	public void updateById(TUser user) throws MyException {
		user.setUpdateTime(ConverUtil.dateForString(new Date()));
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 系统设置->用户管理->添加用户
	 */
	@Override
	public void insert(TUser user) throws MyException {
		if (TextUtils.isEmpty(user.getUsernum()) || TextUtils.isEmpty(user.getLoginname())) {
			throw new MyException("0", "用户编码或账号不能为空");
		}
		// 判断用户是否存在
		Example example = new Example(TUser.class);
		example.createCriteria().orEqualTo("usernum", user.getUsernum()).orEqualTo("loginname", user.getLoginname());
		example.and(example.createCriteria().andEqualTo("isenable", true));
		List<TUser> list = userMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此编号或账号已存在");
		}
		// 初始化密码
		String encodePassword = MD5Util.encodePassword("000000", MD5Util.SALT);
		user.setPassword(encodePassword);
		user.setAddtime(ConverUtil.dateForString(new Date()));
		// 添加时间
		userMapper.insertSelective(user);
	}

	/**
	 * 系统设置->用户管理->删除用户
	 */
	@Transactional
	@Override
	public void delete(Integer id) throws MyException {
		Example example = new Example(T_device_all.class);
		example.createCriteria().andEqualTo("own_id", id).andEqualTo("isenable", 1);
		int selectCountByExample = deviceMapper.selectCountByExample(example);
		if (selectCountByExample != 0) {
			throw new MyException("0", "该用户绑定有设备，无法删除");
		}
		TUser record = new TUser();
		record.setIsdelete(true);
		record.setIsenable(false);
		record.setId(id);
		userMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 系统设置->用户管理->用户分页列表
	 */
	@Override
	public PageInfo<TUserVo> selectPageUsers(UserPageParam param) throws MyException {
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

	/**
	 * 查询当前用户以及其子用户
	 */
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
	
	/**
	 * 查询指定用户信息
	 */
	@Override
	public TUser selectUserById(Integer userid) throws MyException {
		return userMapper.selectByPrimaryKey(userid);
	}
}
