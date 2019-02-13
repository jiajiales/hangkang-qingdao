package com.hot.manage.service.yg;

import java.util.List;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.TAlarmState;
import com.hot.manage.exception.MyException;

public interface TAlarmStateService {

	/**
	 * 查询所有状态
	 * @return
	 * @throws MyException
	 */
	List<TAlarmState> SelectAll() throws MyException;

	/**
	 * 修改一条状态
	 * @param talarmState
	 * @return
	 * @throws MyException
	 */
	Integer updateOne(TAlarmState talarmState) throws MyException;

	/**
	 * 删除一条状态
	 * @param id
	 * @return
	 * @throws MyException
	 */
	Integer deleteOne(Integer id) throws MyException;

	/**
	 * 添加一条状态 
	 * @param talarmState
	 * @return
	 * @throws MyException
	 */
	Integer insertOne(TAlarmState talarmState) throws MyException;
	
	/**
	 * 查询所有状态(分页)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws MyException
	 */
	Page<TAlarmState> findByPage(int pageNo,int pageSize) throws MyException;
	
	/**
	 * 根据id查询一条记录
	 * @param id
	 * @return
	 * @throws MyException
	 */
	TAlarmState selectById(Integer id)throws MyException;
	
}
