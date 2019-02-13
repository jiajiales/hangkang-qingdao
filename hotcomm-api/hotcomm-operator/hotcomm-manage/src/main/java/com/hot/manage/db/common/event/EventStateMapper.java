package com.hot.manage.db.common.event;

import org.springframework.data.repository.query.Param;

import com.hot.manage.entity.common.event.T_event_state;
import com.hot.manage.exception.MyException;

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
