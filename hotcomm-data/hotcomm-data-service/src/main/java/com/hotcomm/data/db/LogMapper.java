package com.hotcomm.data.db;

import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.sys.Log;

import tk.mybatis.mapper.common.Mapper;

public interface LogMapper extends Mapper<Log> {

	Page<Log> queryLogInPage(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("recordUser") String recordUser);

}
