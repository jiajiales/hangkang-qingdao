package com.hotcomm.prevention.db.mysql.manage.appmap;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevnum;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapGroupList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.THKApp;

public interface APPMapMapper {

	List<AppMapGroupList> AppMapGroupList(@Param("userid") Integer userid);

	List<AppMapDevList> AppMapDevList(@Param("userid") Integer userid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("devnumorcode") String devnumorcode);

	List<AppMapDevnum> AppMapDevnum(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("devnum") String devnum);

	THKApp queryApkUrl(Integer version);

}
