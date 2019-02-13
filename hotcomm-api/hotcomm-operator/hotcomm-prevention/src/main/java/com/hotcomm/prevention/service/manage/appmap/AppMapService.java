package com.hotcomm.prevention.service.manage.appmap;

import java.util.List;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevnum;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapGroupList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.THKApp;
import com.hotcomm.prevention.exception.MyException;

public interface AppMapService {

	List<AppMapGroupList> AppMapGroupList(Integer userid);

	List<AppMapDevList> AppMapDevList(Integer userid, Integer groupid, Integer moduleid, String devnumorcode);

	List<AppMapDevnum> AppMapDevnum(Integer userid, Integer moduleid, String devnum);
	
	THKApp queryApkUrl(Integer version)throws MyException;
}
