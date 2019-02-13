package com.hot.manage.service.yg;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.group.IsxistMapper;
import com.hot.manage.db.yg.YGDevMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.group.Isexist;
import com.hot.manage.entity.yg.TDeviceYg;
import com.hot.manage.entity.yg.YGDev;
import com.hot.manage.entity.yg.YGDevone;
import com.hot.manage.entity.yg.YGDevv;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class YGDevServiceImpl implements YGDevService {
	@Autowired
	private YGDevMapper yGDevMapper;

	@Autowired
	private IsxistMapper isxistMapper;

	@Override
	public PageInfo<YGDev> selectdevlist(YGDevv yGDevv) {
		PageHelper.startPage(yGDevv.getPageNum(), yGDevv.getPageSize());
		Page<YGDev> page = yGDevMapper.selectdevlist(yGDevv);
		List<YGDev> list = ConverUtil.converPage(page, YGDev.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public YGDevone selectdevbyid(Integer devid) {
		return yGDevMapper.selectdevbyid(devid);
	}

	@Override
	public Integer updatedev(Integer devid, String devnum, String mac, Integer groupid, String code, String lat,
			String lng, Double x, Double y, Integer mapimgid, Integer own_id, Integer moduleid) {

		Integer i = yGDevMapper.updatedev(devid, devnum, mac, groupid, code, lat, lng, x, y, mapimgid, own_id);
		if (mapimgid != null) {
			Isexist res = isxistMapper.selectsiterelation(devid, moduleid);
			Integer aa = res.getDgcount();
			if (aa > 0) {
				return yGDevMapper.updatesiterelation(devid, mapimgid);
			} else {
				return yGDevMapper.insertsiterelation(devid, mapimgid);
			}
		}
		return i;
	}

	@Override
	public Integer updatedevmac(Integer devid, String mac) {

		return yGDevMapper.updatedevmac(devid, mac);
	}

	@Override
	public Integer insertdev(YGDev ygDev) {
		yGDevMapper.insertdev(ygDev);
		if (ygDev.getVideoid() != null && ygDev.getVideoid().size() != 0) {
			for (Integer i : ygDev.getVideoid()) {
				yGDevMapper.insertvideoRe(ygDev.getId(), i, 2);
			}
		}
		if (ygDev.getMapimgid() != null) {
			yGDevMapper.insertgroup(ygDev);
			return yGDevMapper.insertimggroup(ygDev);
		} else {
			return yGDevMapper.insertgroup(ygDev);
		}

	}

	@Override
	public Integer updateownid(String devids, Integer own_id) {
		List<Object> list = new ArrayList<>();
		if (devids != null) {
			String[] split = devids.split(",");
			for (String s : split) {
				list.add(s);
			}
		}
		return yGDevMapper.updateownid(list, own_id);
	}

	/**
	 * 根据项目组查询组下所有设备
	 */
	@Override
	public List<TDeviceYg> selectDevForGroup(Integer groupid, Integer userid, Integer moduleid) throws MyException {
		return yGDevMapper.selectDevForGroup(groupid, userid, moduleid);
	}

}
