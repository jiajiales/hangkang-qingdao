package com.hotcomm.prevention.db.mysql.common;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.entity.TOperationLog;
import com.hotcomm.prevention.bean.mysql.common.vo.TOperationLogVo;

import tk.mybatis.mapper.common.Mapper;

public interface TOperationLogMapper extends Mapper<TOperationLog> {
	Page<TOperationLogVo> selectPageInfo(@Param("params") Map<String, Object> params);
}
