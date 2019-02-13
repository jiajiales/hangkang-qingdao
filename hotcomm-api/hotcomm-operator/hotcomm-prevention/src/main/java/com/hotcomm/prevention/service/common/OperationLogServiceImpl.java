package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.common.entity.TOperationLog;
import com.hotcomm.prevention.bean.mysql.common.params.OperationLogPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TOperationLogVo;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.db.mysql.common.TOperationLogMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;

@Service
public class OperationLogServiceImpl implements OperationLogService {
	@Autowired
	TOperationLogMapper operationLogMapper;
	@Autowired
	UserService userService;
	
	/**
	 * 供aop切面调用记录用户操作日志
	 */
	@Override
	public void insert(TOperationLog log) throws MyException {
		operationLogMapper.insertSelective(log);
	}
	
	/**
	 * 系统设置->操作日志管理->分页列表查询
	 */
	@Override
	public PageInfo<TOperationLogVo> selectPageInfo(OperationLogPageParam param) throws MyException {
		List<Integer> array = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<TUserVo> users = userService.selectChildren(param.getUserid());
		for (TUserVo tUserVo : users) {
			array.add(tUserVo.getId());
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

}
