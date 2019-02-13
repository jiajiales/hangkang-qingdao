package com.hot.manage.service.common.appmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.common.appmap.APPMapMapper;
import com.hot.manage.entity.common.THKApp;
import com.hot.manage.entity.common.appmap.AppMapDevList;
import com.hot.manage.entity.common.appmap.AppMapDevnum;
import com.hot.manage.entity.common.appmap.AppMapGroupList;
import com.hot.manage.entity.common.appmap.SignInfo;
import com.hot.manage.entity.common.appmap.SignlogList;
import com.hot.manage.exception.MyException;

@Transactional
@Service
public class AppMapServiceImpl implements AppMapService {

	@Autowired
	private APPMapMapper appMapMapper;

	// app地图页面-用户下的项目
	@Override
	public List<AppMapGroupList> AppMapGroupList(Integer userid) {
		List<AppMapGroupList> list = new ArrayList<>();
		AppMapGroupList listson = new AppMapGroupList();
		listson.setGroupname("全部");
		listson.setName("全部");
		list.add(listson);
		list.addAll(appMapMapper.AppMapGroupList(userid));
		return list;
	}

	// app地图页面-根据项目组查询展示设备
	@Override
	public List<AppMapDevList> AppMapDevList(Integer userid, Integer groupid, Integer moduleid, String devnumorcode) {
		List<AppMapDevList> list = new ArrayList<>();// 储存全部数据
		if (groupid == null && moduleid == null) {
			// 搜索全部
			int[] moduleids = { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
			for (int i = 0; i < moduleids.length; i++) {
				list.addAll(appMapMapper.AppMapDevList(userid, null, moduleids[i], devnumorcode));
			}
		} else {
			// 根据项目搜索
			list.addAll(appMapMapper.AppMapDevList(userid, groupid, moduleid, devnumorcode));
		}
		return list;
	}

	// 模糊查询相关的设备编号
	@Override
	public List<AppMapDevnum> AppMapDevnum(Integer userid, Integer moduleid, String devnum) {
		if (moduleid != null) {
			return appMapMapper.AppMapDevnum(userid, moduleid, devnum);
		}
		List<AppMapDevnum> list = new ArrayList<>();
		int[] id = { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		for (int i = 0; i < id.length; i++) {
			list.addAll(appMapMapper.AppMapDevnum(userid, id[i], devnum));
		}
		return list;
	}

	/**
	 * 获取最新版安装包路径
	 */
	@Override
	public THKApp queryApkUrl(Integer version) throws MyException {
		return appMapMapper.queryApkUrl(version);
	}

	

	@Override
	public List<SignlogList> AppSignlog(Integer userid) throws MyException {
		return appMapMapper.AppSignLog(userid);
	}

	@Override
	public Integer AppSigns(SignInfo signInfo) throws MyException {
		Integer s=	appMapMapper.selectSignid(signInfo.getDevnum());
		  signInfo.setSignid(s);
		Integer y=	appMapMapper.selectrelation(signInfo.getSignid(),signInfo.getUserid());
		if(y==0){
			return 0;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		signInfo.setAddtime(df.format(day));
	
			if( signInfo.getLng()==null||signInfo.getLng().equals("")  || signInfo.getLat()==null ||signInfo.getLat().equals("")){
				signInfo.setSignstate(3);
				 appMapMapper.AppSign(signInfo);
				 appMapMapper.AppUpdateSign(signInfo.getAddtime(),signInfo.getSignid());
				return 3;
			}
		Integer dis=appMapMapper.selectjw(signInfo.getSignid(),signInfo.getLat(),signInfo.getLng());
			if(dis>100){
				signInfo.setSignstate(2);
				appMapMapper.AppSign(signInfo);
				appMapMapper.AppUpdateSign(signInfo.getAddtime(),signInfo.getSignid());
				return 2;
			}
			signInfo.setSignstate(1);
			appMapMapper.AppSign(signInfo);
			appMapMapper.AppUpdateSign(signInfo.getAddtime(),signInfo.getSignid());
		     return 1;
	}
}
