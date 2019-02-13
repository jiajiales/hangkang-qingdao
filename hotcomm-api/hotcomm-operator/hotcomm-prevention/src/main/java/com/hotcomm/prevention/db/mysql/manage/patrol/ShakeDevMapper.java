package com.hotcomm.prevention.db.mysql.manage.patrol;

import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.patrol.CheckSignDevnum;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignLogPageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlaceOnid;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlacePageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.NewSignPlaceParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignLogPageInfoParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignPlacePageInfoParam;

public interface ShakeDevMapper {

	Integer patUserRelationSign(@Param("patUser") Integer patUser, @Param("signId") Integer signId);

	Integer cutRelation(@Param("signId") Integer signId);

	Integer deleteSignPlace(@Param("signId") Integer signId);

	Integer clearSignLog(@Param("signId") Integer signId);

	Integer updateSignPlace(NewSignPlaceParam param);

	CheckSignDevnum checkSignPlaceDevnum(@Param("devnum") String devnum);

	Integer addSignAddress(NewSignPlaceParam param);

	SignPlaceOnid selectSignPlaceOnid(@Param("signId") Integer signId);

	Page<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param);

	Page<SignPlacePageInfoVO> selectSignPlacePageInfo(SignPlacePageInfoParam param);

	Integer AppSign(SignInfo signInfo);

	Integer selectSignid(@Param("devnum") String devnum);

	Integer selectPid(@Param("userid") Integer userid);

	Integer selectrelation(@Param("signid") Integer signid, @Param("Pid") Integer Pid);

	Integer selectjw(@Param("signid") Integer signid, @Param("lat") String lat, @Param("lng") String lng);

	void AppUpdateSign(@Param("addtime") String addtime, @Param("signid") Integer signid);

	void AppUpdateSignTime(@Param("addtime") String addtime, @Param("userid") Integer userid);
}
