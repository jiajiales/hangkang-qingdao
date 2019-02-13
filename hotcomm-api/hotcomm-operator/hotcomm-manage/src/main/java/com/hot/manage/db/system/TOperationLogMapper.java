package com.hot.manage.db.system;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.system.OperationLogPageParam;
import com.hot.manage.entity.system.TOperationLog;
import com.hot.manage.entity.system.TOperationLogVo;

import tk.mybatis.mapper.common.Mapper;

public interface TOperationLogMapper extends Mapper<TOperationLog> {
	
	Page<TOperationLogVo> selectPageInfo(@Param("params") Map<String, Object> params);
	
	Page<TOperationLogVo> selectByUserId(OperationLogPageParam param);
}
