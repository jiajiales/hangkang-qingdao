package com.hot.analysis.service.sj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.analysis.db.sj.LKTSJMapper;
import com.hot.analysis.bean.sj.LKTSJDevState;
import com.hot.analysis.bean.sj.LKTSJAlarmList;

@Transactional
@Service
public class LKTSJServiceImpl implements LKTSJService {

	@Autowired
	private LKTSJMapper lktsjmapper;

	// 设备状态图
	@Override
	public List<LKTSJDevState> LKTSJDevState(Integer Time, Integer moduleID, Integer userID) {
		List<LKTSJDevState> sonlist = lktsjmapper.LKTSJDevState(Time, moduleID, userID);
		List<LKTSJDevState> father = new ArrayList<>();
		int[] i = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		for (int j = 0; j < i.length; j++) {
			LKTSJDevState son = new LKTSJDevState();
			son.setYaer(Time);
			son.setMth(i[j]);
			son.setAdd_devcount(0);
			son.setAlarmcount(0);
			son.setFalseCount(0);
			son.setModuleID(10);
			father.add(j, son);
			if (sonlist != null && sonlist.size() > 0) {
				for (int k = 0; k < sonlist.size(); k++) {
					if (sonlist.get(k).getMth() == i[j]) {
						father.set(j, sonlist.get(k));
					}
				}
			}
		}
		return father;
	}

	// 报警类型统计
	@Override
	public List<LKTSJAlarmList> LKTSJAlarmList(Integer Time, Integer moduleID, Integer userID) {
		return lktsjmapper.LKTSJAlarmList(Time, moduleID, userID);
	}
}
