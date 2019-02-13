package com.hot.manage.service.common.patrol;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.entity.common.patrol.PatrolPersonVo;
import com.hot.manage.entity.system.TUserVo;
import com.hot.manage.exception.MyException;

public interface PatrolListService {
	//巡检人员列表分页
	PageInfo<PatrolPersonVo> selectPageInfo(PatrolParams params)throws MyException;
	
	//添加巡检人员
	Integer insertPatrol(Integer userid)throws MyException;
	
	//冻结巡检人员
	Integer updatePatrol(Integer id,Integer isenable)throws MyException;
	
	//删除巡检人员
	Integer delPatrol(Integer id)throws MyException;
	
	//查询当前用户下的巡检人员
	List<TUserVo> selectAllPatrol(Integer userid)throws MyException;
	
	//更换巡检人员
	Integer replacePatrol(Integer patrolid,Integer id)throws MyException;

}
