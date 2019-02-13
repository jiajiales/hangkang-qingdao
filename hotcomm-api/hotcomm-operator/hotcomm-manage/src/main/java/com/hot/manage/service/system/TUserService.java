package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.entity.system.TUserVo;
import com.hot.manage.entity.system.UserPageParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;
import com.hot.manage.service.PageService;

public interface TUserService extends CommonCrudService<TUser, TUserVo, Integer>, PageService<PageParam, TUserVo> {

	public Integer insertUser(TUser user) throws Exception;

	public TUser selectUserById(Integer id) throws MyException;

	public List<TUser> selectListByUserId(Integer userid) throws MyException;

	List<TUserVo> selectChildren(Integer userid) throws MyException;
	
	PageInfo<TUserVo> selectPage(UserPageParam param) throws MyException;
}
