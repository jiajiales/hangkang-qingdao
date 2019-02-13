package com.hotcomm.prevention.service.datashow.jg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.jg.AreaVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.FlipNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.GroupInfoVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTimeAlarmNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTypequery;
import com.hotcomm.prevention.bean.mysql.datashow.jg.ReasonNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverSpreadVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverType;
import com.hotcomm.prevention.db.mysql.datashow.jg.WellCoverMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class WellCoverServiceImpl implements WellCoverService {

	@Autowired
	private WellCoverMapper wellCoverMapper;

	@Override
	public List<FlipNum> selectFlipNum(String startTime, String endTime, Integer moduleid, Integer querytype,
			Integer userid) throws MyException {
		List<FlipNum> list = wellCoverMapper.selectFlipNum(startTime, endTime, moduleid, querytype, userid);
		return list;
	}

	@Override
	public List<JGTimeAlarmNum> selectJGTimeAlarmNum(String startTime, String endTime, Integer moduleid,
			Integer alarmtype, Integer userid) throws MyException {
		/*
		 * List<JGTimeAlarmNum> list =
		 * wellCoverMapper.selectJGTimeAlarmNum(startTime, endTime, moduleid,
		 * alarmtype, userid);
		 * 
		 * return list;
		 */

		List<JGTimeAlarmNum> sonlist = wellCoverMapper.selectJGTimeAlarmNum(startTime, endTime, moduleid, alarmtype,
				userid);
		List<JGTimeAlarmNum> father = new ArrayList<>();
		String[] i = { "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
				"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
				"23:00" };
		for (int j = 0; j < i.length; j++) {
			JGTimeAlarmNum son = new JGTimeAlarmNum();
			son.setAlarmtype(alarmtype);
			son.setCount(0);
			son.setHourpart(i[j]);
			son.setModuleid(moduleid);

			father.add(j, son);
			if (sonlist != null && sonlist.size() > 0) {
				for (int k = 0; k < sonlist.size(); k++) {
					if (sonlist.get(k).getHourpart().equals(i[j])) {
						father.set(j, sonlist.get(k));
					}
				}
			}
		}
		return father;
	}

	@Override
	public List<ReasonNum> selectReasonNum(String startTime, String endTime, Integer moduleid, Integer pepairtype,
			Integer userid) throws MyException {
		List<ReasonNum> list = wellCoverMapper.selectReasonNum(startTime, endTime, moduleid, pepairtype, userid);
		return list;
	}

	@Override
	public List<WellCoverSpreadVO> GroupListDev(JGTypequery jGTypequery) throws MyException {
		String[] purpose = null;
		String[] loadbear = null;

		String p = jGTypequery.getPurpose();
		String l = jGTypequery.getLoadbear();

		if (p != null && !"".equals(p)) {
			if (p.equals("0")) {
				p = null;
			} else {
				purpose = p.split(",");
			}
		}
		if (l != null && !l.equals("")) {
			if (l.equals("0")) {
				l = null;
			} else {
				loadbear = l.split(",");
			}
		}

		return wellCoverMapper.GroupListDev(jGTypequery.getModuleid(), jGTypequery.getGroupid(), jGTypequery.getSite(),
				purpose, loadbear, jGTypequery.getCode(), jGTypequery.getAreaid());
	}

	@Override
	public List<WellCoverType> selectJGType(Integer moduleid, Integer queryType, Integer userid) throws MyException {
		return wellCoverMapper.selectJgType(moduleid, queryType, userid);
	}

	@Override
	public List<WellCoverSpreadVO> wellCoverSpread(Integer moduleid, Integer userid, Integer groupid, String code)
			throws MyException {
		return wellCoverMapper.wellCoverSpread(moduleid, userid, groupid, code);
	}

	@Override
	public List<AreaVO> selectArea(Integer moduleid, Integer userid) throws MyException {
		return wellCoverMapper.selectArea(moduleid, userid);
	}

	@Override
	public List<GroupInfoVO> selectGroup(Integer moduleid, Integer areaid, Integer userid) throws MyException {
		return wellCoverMapper.selectGroup(moduleid, areaid, userid);
	}

}
