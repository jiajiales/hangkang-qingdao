package com.hotcomm.prevention.service.manage.patrol;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignLogPageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlaceOnid;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlacePageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.NewSignPlaceParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignLogPageInfoParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignPlacePageInfoParam;
import com.hotcomm.prevention.exception.MyException;

public interface ShakeDevService {

	Integer patUserRelationSign(String patUser, String signId) throws MyException;

	Integer deleteSignPlace(Integer signId) throws MyException;

	Integer updateSignPlace(NewSignPlaceParam param) throws MyException;

	Integer addSignAddress(NewSignPlaceParam param) throws MyException;

	SignPlaceOnid selectSignPlaceOnid(Integer signId) throws MyException;

	PageInfo<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param) throws MyException;
	
	PageInfo<SignPlacePageInfoVO> selectSignPlacePageInfo(SignPlacePageInfoParam param) throws MyException;
	
	Integer AppSigns(SignInfo signInfo)throws MyException;
}
