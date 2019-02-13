package com.hotcomm.prevention.service.common;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.entity.TPower;
import com.hotcomm.prevention.exception.MyException;

public interface PowersService {

	List<TPower> selectPowersByUserid(Integer fatherid, Integer userid) throws MyException;
	
	List<TPower> selectPowers(Integer userid)throws MyException;
	
	void update(TPower power)throws MyException;

}
