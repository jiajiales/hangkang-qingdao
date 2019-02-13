package com.hotcomm.prevention.service.manage.appmap;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapDevnum;
import com.hotcomm.prevention.bean.mysql.manage.appmap.AppMapGroupList;
import com.hotcomm.prevention.bean.mysql.manage.appmap.THKApp;
import com.hotcomm.prevention.db.mysql.manage.appmap.APPMapMapper;
import com.hotcomm.prevention.exception.MyException;

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
		/*List<AppMapDevnum> list = new ArrayList<>();
		int[] id = { 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		for (int i = 0; i < id.length; i++) {
			list.addAll(appMapMapper.AppMapDevnum(userid, id[i], devnum));
		}*/
		return appMapMapper.AppMapDevnum(userid, null, devnum);
	}

	/**
	 * 获取最新版安装包路径
	 */
	@Override
	public THKApp queryApkUrl(Integer version) throws MyException {
		return appMapMapper.queryApkUrl(version);
	}
}
