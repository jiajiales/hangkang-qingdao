package com.hotcomm.prevention.service.common;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.common.params.UserPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;

public interface UserService {

	TUser selectByUsername(String username) throws MyException;

	void updateById(TUser user) throws MyException;

	void insert(TUser user) throws MyException;

	void delete(Integer id) throws MyException;

	PageInfo<TUserVo> selectPageUsers(UserPageParam param) throws MyException;

	List<TUserVo> selectChildren(Integer userid) throws MyException;

	TUser selectUserById(Integer userid) throws MyException;
	
}
