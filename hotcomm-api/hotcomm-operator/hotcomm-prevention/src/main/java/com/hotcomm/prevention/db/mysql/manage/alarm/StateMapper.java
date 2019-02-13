package com.hotcomm.prevention.db.mysql.manage.alarm;

import org.springframework.data.repository.query.Param;

import com.hotcomm.prevention.bean.mysql.manage.alarm.AlarmState;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface StateMapper extends Mapper<AlarmState> {
	/**
	 * 检查报警/事件状态是否被使用
	 * 
	 * @param stateid
	 *            状态id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer checkState(@Param("stateid") Integer stateid) throws MyException;

}
