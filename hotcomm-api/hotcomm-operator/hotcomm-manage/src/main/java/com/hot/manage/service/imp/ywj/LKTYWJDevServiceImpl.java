package com.hot.manage.service.imp.ywj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.ywj.LKTYWJDevMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.ywj.AlarmingTrendVO;
import com.hot.manage.entity.ywj.LKTYWJDevList;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJGroupListDev;
import com.hot.manage.entity.ywj.LKTYWJSelectOnId;
import com.hot.manage.entity.ywj.LKTYWJSeleteMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddDevVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.video.DevVideoService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.service.video.VideoServiceImpl;
import com.hot.manage.service.ywj.LKTYWJDevService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTYWJDevServiceImpl implements LKTYWJDevService {

	@Autowired
	private LKTYWJDevMapper lktYWJDevMapper;

	@Autowired
	private VideoServiceImpl videoServiceImpl;

	@Autowired
	private DevVideoService devVideoService;

	@Autowired
	private VideoService videoService;

	@Override
	public PageInfo<LKTYWJDevList> LKTYWJDevList(LKTYWJDevListVaule lktywjDevListVaule) throws MyException {
		PageHelper.startPage(lktywjDevListVaule.getPageNum(), lktywjDevListVaule.getPageSize());
		Page<LKTYWJDevList> page = lktYWJDevMapper.LKTYWJDevList(lktywjDevListVaule);
		List<LKTYWJDevList> list = ConverUtil.converPage(page, LKTYWJDevList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer LKTYWJAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		return lktYWJDevMapper.LKTYWJAddSignDevList(moduleid, devid, patrolid);
	}

	@Override
	public Integer LKTYWJUpdateDev(LKTYWJUpdateDevVaule lktywjUpdateDevVaule) throws MyException {
		if (lktywjUpdateDevVaule.getDevnum() != null) {
			List<LKTYWJSelectOnId> devnum = lktYWJDevMapper.LKTYWJSelectOnId(lktywjUpdateDevVaule.getModuleid(),
					lktywjUpdateDevVaule.getUserid(), null, lktywjUpdateDevVaule.getDevnum(), null);
			if (devnum.size() != 0 && devnum.get(0).getDevnum() != null) {
				if (!devnum.get(0).getId().equals(lktywjUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
		Integer code = lktYWJDevMapper.LKTYWJUpdateDev(lktywjUpdateDevVaule);
		if (code >= 1) {
			// 设备重绑摄像头
			if (lktywjUpdateDevVaule.getVideos() != null) {
				Integer in = videoServiceImpl.reRelation(lktywjUpdateDevVaule.getModuleid(),
						lktywjUpdateDevVaule.getDevid(), lktywjUpdateDevVaule.getVideos());
				if (in <= 0) {
					throw new MyException("0", "操作失败");
				}
			}
		}
		if (code >= 1) {
			if (lktywjUpdateDevVaule.getItempicid() != null) {
				code = lktYWJDevMapper.LKTYWJUpdateDevAddPic(lktywjUpdateDevVaule);
			}
		}
		return code;
	}

	@Override
	public List<LKTYWJGroupList> LKTYWJGroupList(Integer userid, Integer moduleid) {
		return lktYWJDevMapper.LKTYWJGroupList(userid, null, moduleid, null, null);
	}

	@Override
	public Integer LKTYWJDeleteDev(Integer moduleid, Integer devid) throws MyException {
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(moduleid, devid);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(moduleid, devid);
		}
		return lktYWJDevMapper.LKTYWJDeleteDev(moduleid, devid);
	}

	@Override
	public List<LKTYWJGroupListDev> LKTYWJGroupListDev(Integer moduleid, Integer groupid, String site)
			throws MyException {
		return lktYWJDevMapper.LKTYWJGroupListDev(moduleid, groupid, site);
	}

	@Override
	public LKTYWJDevNum LKTYWJGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException {
		return lktYWJDevMapper.LKTYWJGroupListDevnum(moduleid, userid, groupid);
	}

	@Override
	public List<LKTYWJSelectOnId> LKTYWJSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return lktYWJDevMapper.LKTYWJSelectOnId(moduleid, userid, devid, null, null);
	}

	@Override
	public List<LKTYWJSeleteMap> LKTYWJSeleteMap(Integer groupid, Integer userid) {
		return lktYWJDevMapper.LKTYWJSeleteMap(groupid, userid);
	}

	@Override
	public Integer LKTYWJAddDev(LKTYWJAddDevVaule lktywjAddDevVaule) throws MyException {
		List<LKTYWJSelectOnId> devnum = lktYWJDevMapper.LKTYWJSelectOnId(lktywjAddDevVaule.getModuleid(),
				lktywjAddDevVaule.getUserid(), null, lktywjAddDevVaule.getDevnum(), null);
		if (devnum.size() != 0 && devnum.get(0) != null) {
			return 201;
		}
		List<LKTYWJSelectOnId> macAddr = lktYWJDevMapper.LKTYWJSelectOnId(lktywjAddDevVaule.getModuleid(),
				lktywjAddDevVaule.getUserid(), null, null, lktywjAddDevVaule.getMacAddr());
		if (macAddr.size() != 0 && macAddr.get(0) != null) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lktywjAddDevVaule.setAddtime(df.format(day));
		Integer code = lktYWJDevMapper.LKTYWJAddDev(lktywjAddDevVaule);
		if (code > 0) {
			code = lktYWJDevMapper.LKTYWJAddDevGroup(lktywjAddDevVaule);
		}
		if (code > 0 & lktywjAddDevVaule.getVideoid() != null) {
			// 判断设备与摄像头绑定
			boolean judge = devVideoService.checkDevVideoRelation(lktywjAddDevVaule.getModuleid(),
					lktywjAddDevVaule.getId());
			DevRelationVideoParam param = new DevRelationVideoParam();
			List<Integer> devId = new ArrayList<>();
			devId.add(lktywjAddDevVaule.getId());
			param.setDevId(devId);
			param.setVideoId(lktywjAddDevVaule.getVideoid());
			param.setModuleid(lktywjAddDevVaule.getModuleid());
			if (judge == false && param != null) {
				Integer in = videoService.devConectVideo(param);
				if (in <= 0) {
					throw new MyException("0", "操作失败");
				}
			}
		}
		if (code > 0 & lktywjAddDevVaule.getItempicid() != null) {
			code = lktYWJDevMapper.LKTYWJAddDevGroupPic(lktywjAddDevVaule);
		}
		return code;
	}

	@Override
	public Integer changeOwn(String devid, Integer ownId) throws MyException {
		return lktYWJDevMapper.LKTYWJChangeYwjOwner(ownId, devid);
	}

	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		return lktYWJDevMapper.LKTAddSignDevList(moduleid, devid, patrolid);
	}

	@Override
	public Integer changeMac(Integer devid, String mac) throws MyException {
		List<LKTYWJSelectOnId> macCount = lktYWJDevMapper.checkMac(mac);
		if (macCount.size() > 0 && macCount.get(0).getId() != devid) {
			return 201;
		}
		return lktYWJDevMapper.changeMac(devid, mac);
	}

	@Override
	public List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(Integer queryType, Integer userid,
			Integer groupid) {
		return lktYWJDevMapper.AlarmingTrendForWhichHasBoundaryValue(queryType, userid, groupid);
	}

}
