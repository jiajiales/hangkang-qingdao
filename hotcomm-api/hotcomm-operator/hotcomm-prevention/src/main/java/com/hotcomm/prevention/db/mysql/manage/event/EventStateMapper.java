package com.hotcomm.prevention.db.mysql.manage.event;

import org.springframework.data.repository.query.Param;

import com.hotcomm.prevention.bean.mysql.manage.event.T_event_state;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface EventStateMapper extends Mapper<T_event_state> {

	/**
	 * 检查报警/事件状态是否被使用
	 * 
	 * @param stateid
	 *            状态id
	 * @return
	 * @throws MyException
	 */
	Integer checkState(@Param("stateid") Integer stateid) throws MyException;

}
