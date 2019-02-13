package com.hotcomm.prevention.service.manage.patrol;

import java.util.List;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolParams;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolPersonVo;
import com.hotcomm.prevention.exception.MyException;

public interface PatrolListService {
	// 巡检人员列表分页
	PageInfo<PatrolPersonVo> selectPageInfo(PatrolParams params) throws MyException;

	// 添加巡检人员
	Integer insertPatrol(Integer userid) throws MyException;

	// 冻结巡检人员
	Integer updatePatrol(Integer id, Integer isenable) throws MyException;

	// 删除巡检人员
	Integer delPatrol(Integer id) throws MyException;

	// 查询当前用户下的巡检人员
	List<TUserVo> selectAllPatrol(Integer userid) throws MyException;

	// 更换巡检人员
	Integer replacePatrol(Integer patrolid, Integer id) throws MyException;

}
