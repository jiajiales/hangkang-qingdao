package com.hot.manage.service.common.appmap;

import java.util.List;

import com.hot.manage.entity.common.THKApp;
import com.hot.manage.entity.common.appmap.AppMapDevList;
import com.hot.manage.entity.common.appmap.AppMapDevnum;
import com.hot.manage.entity.common.appmap.AppMapGroupList;
import com.hot.manage.entity.common.appmap.SignInfo;
import com.hot.manage.entity.common.appmap.SignlogList;
import com.hot.manage.exception.MyException;

public interface AppMapService {

	List<AppMapGroupList> AppMapGroupList(Integer userid);

	List<AppMapDevList> AppMapDevList(Integer userid, Integer groupid, Integer moduleid, String devnumorcode);

	List<AppMapDevnum> AppMapDevnum(Integer userid, Integer moduleid, String devnum);
	
	THKApp queryApkUrl(Integer version)throws MyException;

	List<SignlogList> AppSignlog(Integer userid)throws MyException;

	Integer AppSigns(SignInfo signInfo)throws MyException;
}
