package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.system.TMessageLog;

import tk.mybatis.mapper.common.Mapper;

public interface TMessageLogMapper extends Mapper<TMessageLog> {

	Page<TMessageLog> selectByReceiverId(@Param("receiverid") Integer receiverid);

	Page<TMessageLog> selectByUserid(@Param("userid") Integer userid);

	List<TMessageLog> selectUnsent(@Param("receiverid") String receiverid);

	Integer update(TMessageLog message);

	Integer insertBacth(@Param("params") List<TMessageLog> params);
}
