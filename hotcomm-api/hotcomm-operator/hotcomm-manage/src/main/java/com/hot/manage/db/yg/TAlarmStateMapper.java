package com.hot.manage.db.yg;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.hot.manage.entity.yg.TAlarmState;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface TAlarmStateMapper extends Mapper<TAlarmState>{

	/**
	 * 分页
	 * @return
	 * @throws MyException
	 */
	List<TAlarmState> findByPage() throws MyException;
	
	/**
	 * 检查报警/事件状态是否被使用
	 * @param stateid 状态id
	 * @return
	 * @throws MyException
	 */
	Integer checkState(@Param("stateid") Integer stateid)throws MyException;
	
}
