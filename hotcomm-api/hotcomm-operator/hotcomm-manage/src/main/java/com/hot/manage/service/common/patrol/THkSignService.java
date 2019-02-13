package com.hot.manage.service.common.patrol;


import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeSignParam;
import com.hot.manage.entity.common.patrol.ShakeSignVo;
import com.hot.manage.entity.common.patrol.SignLogInfoVO;
import com.hot.manage.entity.common.patrol.SignLogPageInfoParam;
import com.hot.manage.entity.common.patrol.SignLogPageInfoVO;
import com.hot.manage.entity.common.patrol.SignPlaceOnid;
import com.hot.manage.entity.common.patrol.THKSignParam;
import com.hot.manage.entity.common.patrol.newSignPlaceParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoVO;
import com.hot.manage.exception.MyException;

public interface THkSignService {
	// 摇一摇签到设备分页
	PageInfo<ShakeSignVo> selectPageinfo(ShakeSignParam param) throws MyException;
	
	Integer insertDev(THKSignParam param)throws MyException;
	
	Integer update(THKSignParam param)throws MyException;
	
	Integer delete(Integer id)throws MyException;
	
	PageInfo<signPlacePageInfoVO> selectSignPlacePageInfo(signPlacePageInfoParam param) throws MyException;
	
	Integer addSignAddress(newSignPlaceParam param)throws MyException;
	
	Integer patUserRelationSign(String patUser,String signId);
	
	Integer deleteSignPlace(Integer signId);
	
	Integer updateSignPlace(newSignPlaceParam param);
	
	SignPlaceOnid selectSignPlaceOnid(Integer signId);
	
	PageInfo<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param) throws MyException;
	
	SignLogInfoVO selectSignLogInfo(SignLogPageInfoParam param);
}
