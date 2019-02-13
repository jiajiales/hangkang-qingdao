package com.hot.manage.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.system.TOperationLogMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.system.OperationLogPageParam;
import com.hot.manage.entity.system.TOperationLog;
import com.hot.manage.entity.system.TOperationLogVo;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Service
@Transactional
public class TOperationLogServiceImpl implements TOperationLogService {
	@Autowired
	private TOperationLogMapper operationLogMapper;
	@Autowired
	private TUserService userService;

	@Override
	public Integer insertObject(TOperationLog params, Integer r) throws MyException {
		return null;
	}

	@Override
	public Integer insertObject(TOperationLog params) throws MyException {
		return operationLogMapper.insertSelective(params);
	}

	@Override
	public Integer updateObject(TOperationLog params) throws MyException {
		return null;
	}

	@Override
	public Integer delObject(Integer id) throws MyException {
		return null;
	}

	@Override
	public TOperationLog selectObject(Integer id) throws MyException {
		return null;
	}

	// 根据当前用户ID查询，当前用户能看到的所有日志
	@Override
	public PageInfo<TOperationLogVo> selectPageInfo(OperationLogPageParam param) throws MyException {
		List<Object> array = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<TUser> rows = userService.selectListByUserId(param.getUserid());
		for (TUser user : rows) {
			array.add(user.getId());
		}
		map.put("list", array);
		map.put("starttime", param.getStarttime());
		map.put("endtime", param.getEndtime());
		map.put("loginname", param.getLoginname());
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TOperationLogVo> page = operationLogMapper.selectPageInfo(map);
		List<TOperationLogVo> converPage = ConverUtil.converPage(page, TOperationLogVo.class);
		return new PageInfo<>(param.getPageNum(), param.getPageSize(), page.getTotal(), converPage);
	}

	// 查看用户自己的操作日志
	@Override
	public PageInfo<TOperationLogVo> selectByUserId(OperationLogPageParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TOperationLogVo> page = operationLogMapper.selectByUserId(param);
		List<TOperationLogVo> list = ConverUtil.converPage(page, TOperationLogVo.class);
		return new PageInfo<>(param.getPageNum(), param.getPageSize(), page.getTotal(), list);
	}

}
