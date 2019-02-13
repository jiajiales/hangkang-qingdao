package com.hotcomm.prevention.db.mysql.manage.message;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;

import tk.mybatis.mapper.common.Mapper;

public interface TMessageLogMapper extends Mapper<TMessageLog> {

	Page<TMessageLog> selectByReceiverId(@Param("receiverid") Integer receiverid);

	Page<TMessageLog> selectByUserid(@Param("userid") Integer userid);

	List<TMessageLog> selectUnsent(@Param("receiverid") String receiverid);

	Integer update(TMessageLog message);

	Integer insertBacth(@Param("params") List<TMessageLog> params);
}
