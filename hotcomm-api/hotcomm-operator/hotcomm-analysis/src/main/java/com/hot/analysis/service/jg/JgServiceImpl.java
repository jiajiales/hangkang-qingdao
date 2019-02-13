package com.hot.analysis.service.jg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.bean.jg.FlipNum;
import com.hot.analysis.bean.jg.JGTimeAlarmNum;
import com.hot.analysis.bean.jg.JGType;
import com.hot.analysis.bean.jg.JGTypequery;
import com.hot.analysis.bean.jg.ReasonNum;
import com.hot.analysis.db.jg.JgMapper;

@Service
public class JgServiceImpl implements JgService {

	@Autowired
	private JgMapper jgMapper;

	public List<FlipNum> selectFlipNum(Date startTime, Date endTime, Integer moduleid, Integer querytype,
			Integer userid) {
		List<FlipNum> list = jgMapper.selectFlipNum(startTime, endTime, moduleid, querytype, userid);
		return list;

	}

	// 井盖分类统计
	@Override
	public Map<Integer, List<JGType>> selectJGType(Date startTime, Date endTime, Integer moduleid, Integer userid) {
		String use = null;
		String wight = null;
		Map<Integer, List<JGType>> map = new HashMap<Integer, List<JGType>>();
		List<JGType> useList = new ArrayList<>();
		List<JGType> wightList = new ArrayList<>();
		List<JGType> list = jgMapper.selectJgType(startTime, endTime, moduleid, userid);
		if (list.size() != 0) {
			for (JGType jgType : list) {
				if (jgType.getType() == 1) {
					useList.add(jgType);
				} else {
					wightList.add(jgType);
				}
			}
		}
		for (JGType jgType : useList) {
			use += jgType.getChildtype() + ",";
		}
		// System.out.println(use);
		use = use.substring(0, use.length() - 1);
		// System.out.println(use);
		if (!use.contains("1")) {
			JGType type = new JGType(useList.get(0).getModuleid(), useList.get(0).getType(), 1, 0);
			useList.add(type);
		} else if (!use.contains("2")) {
			JGType type = new JGType(useList.get(0).getModuleid(), useList.get(0).getType(), 2, 0);
			useList.add(type);
		} else if (!use.contains("3")) {
			JGType type = new JGType(useList.get(0).getModuleid(), useList.get(0).getType(), 3, 0);
			useList.add(type);
		} else if (!use.contains("4")) {
			JGType type = new JGType(useList.get(0).getModuleid(), useList.get(0).getType(), 4, 0);
			useList.add(type);
		} else if (!use.contains("5")) {
			JGType type = new JGType(useList.get(0).getModuleid(), useList.get(0).getType(), 5, 0);
			useList.add(type);
		}

		for (JGType jgType : wightList) {
			wight += jgType.getChildtype() + ",";
		}
		// System.out.println(wight);
		wight = wight.substring(0, wight.length() - 1);
		// System.out.println(wight);
		if (!wight.contains("1")) {
			JGType type = new JGType(wightList.get(0).getModuleid(), wightList.get(0).getType(), 1, 0);
			wightList.add(type);
		} else if (!wight.contains("2")) {
			JGType type = new JGType(wightList.get(0).getModuleid(), wightList.get(0).getType(), 2, 0);
			wightList.add(type);
		} else if (!wight.contains("3")) {
			JGType type = new JGType(wightList.get(0).getModuleid(), wightList.get(0).getType(), 3, 0);
			wightList.add(type);
		} else if (!wight.contains("4")) {
			JGType type = new JGType(wightList.get(0).getModuleid(), wightList.get(0).getType(), 4, 0);
			wightList.add(type);
		}
		map.put(1, useList);
		map.put(2, wightList);
		/*
		 * Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
		 * Map<Integer, Integer> usemap = new HashMap<>(); Map<Integer, Integer>
		 * wightmap = new HashMap<>(); usemap.put(1, 0); usemap.put(2, 0);
		 * usemap.put(3, 0); usemap.put(4, 0); usemap.put(5, 0); wightmap.put(1,
		 * 0); wightmap.put(2, 0); wightmap.put(3, 0); wightmap.put(4, 0);
		 * List<JGType> list = jgTypeMapper.selectJgType(startTime, endTime,
		 * moduleid, userid); if (list.size() != 0) { for (JGType jgType : list)
		 * { if (jgType.getType() == 1) { if (jgType.getChildtype() == 1) {
		 * usemap.put(1, jgType.getCount()); } else if (jgType.getChildtype() ==
		 * 2) { usemap.put(2, jgType.getCount()); } else if
		 * (jgType.getChildtype() == 3) { usemap.put(3, jgType.getCount()); }
		 * else if (jgType.getChildtype() == 4) { usemap.put(4,
		 * jgType.getCount()); } else if (jgType.getChildtype() == 5) {
		 * usemap.put(5, jgType.getCount()); } } else { if
		 * (jgType.getChildtype() == 1) { wightmap.put(1, jgType.getCount()); }
		 * else if (jgType.getChildtype() == 2) { wightmap.put(2,
		 * jgType.getCount()); } else if (jgType.getChildtype() == 3) {
		 * wightmap.put(3, jgType.getCount()); } else if (jgType.getChildtype()
		 * == 4) { wightmap.put(4, jgType.getCount()); } } } map.put(1, usemap);
		 * map.put(2, wightmap); }
		 */
		return map;
	}

	// 报警时段统计
	@Override
	public List<JGTimeAlarmNum> selectJGTimeAlarmNum(Date startTime, Date endTime, Integer moduleid, Integer alarmtype,
			Integer userid) {
		List<JGTimeAlarmNum> list = jgMapper.selectJGTimeAlarmNum(startTime, endTime, moduleid, alarmtype, userid);
		return list;
	}

	// 维修原因统计
	@Override
	public List<ReasonNum> selectReasonNum(Date startTime, Date endTime, Integer moduleid, Integer pepairtype,
			Integer userid) {
//		Map<String, Integer> map = new HashMap<>();
		List<ReasonNum> list = jgMapper.selectReasonNum(startTime, endTime, moduleid, pepairtype, userid);
//		if (list.size() != 0) {
//			for (ReasonNum reasonNum : list) {
//
//				map.put(reasonNum.getHname(), reasonNum.getNum());
//
//			}
//		}
		return list;
	}

	@Override
	public List<com.hot.analysis.bean.common.GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site,
			String purpose, String loadbear) {
		// TODO Auto-generated method stub
		return null;
		// jgMapper.GroupListDev(moduleid, groupid, site,purpose, loadbear);
	}

	@Override
	public List<GroupListDev> GroupListDev(JGTypequery jGTypequery) {
		String[] purpose = null;
		String[] loadbear = null;

		String p = jGTypequery.getPurpose();
		String l = jGTypequery.getLoadbear();

		if (p != null ) {
			if (p.equals("0")) { 
				p = null;
			}else{ purpose = p.split(",");
			}
	   	}
		 if (l != null ){
			 if (l.equals("0")) {
                 l = null; 
			 } else{ loadbear = l.split(",");
			 }
		 }
			

		return jgMapper.GroupListDev(jGTypequery.getModuleid(), jGTypequery.getGroupid(), jGTypequery.getSite(),
				purpose, loadbear);
	}

}
