package com.hot.manage.db.common.appmap;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.THKApp;
import com.hot.manage.entity.common.appmap.AppMapDevList;
import com.hot.manage.entity.common.appmap.AppMapDevnum;
import com.hot.manage.entity.common.appmap.AppMapGroupList;
import com.hot.manage.entity.common.appmap.SignInfo;
import com.hot.manage.entity.common.appmap.SignlogList;

public interface APPMapMapper {

	List<AppMapGroupList> AppMapGroupList(@Param("userid") Integer userid);

	List<AppMapDevList> AppMapDevList(@Param("userid") Integer userid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("devnumorcode") String devnumorcode);

	List<AppMapDevnum> AppMapDevnum(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("devnum") String devnum);
	
	THKApp queryApkUrl(Integer version);

	Integer AppSign(SignInfo signInfo);

	List<SignlogList> AppSignLog(@Param("userid") Integer userid);

	Integer selectjw(@Param("signid")   Integer signid, @Param("lat") String lat, @Param("lng")  String lng);

	Integer selectrelation( @Param("signid") Integer signid, @Param("userid")  Integer userid);

	void AppUpdateSign(@Param("addtime") String addtime, @Param("signid") Integer signid);

	Integer selectSignid( @Param("devnum") String devnum);
}
