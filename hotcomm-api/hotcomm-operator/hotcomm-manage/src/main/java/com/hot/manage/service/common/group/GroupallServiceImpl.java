package com.hot.manage.service.common.group;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hot.manage.db.group.IsxistMapper;
import com.hot.manage.db.item.TItemPicMapper;
import com.hot.manage.entity.group.Isexist;
import com.hot.manage.entity.item.TItemPic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.common.group.GroupallMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.AllDevByGroupID;
import com.hot.manage.entity.common.group.Groupdevcount;
import com.hot.manage.entity.common.group.Groupdevstate;
import com.hot.manage.entity.common.group.Grouplist;
import com.hot.manage.entity.common.group.Groupmap;
import com.hot.manage.entity.common.group.Groupmapimg;
import com.hot.manage.entity.common.group.Groupmaps;
import com.hot.manage.entity.common.group.Groupone;
import com.hot.manage.entity.common.group.Groups;
import com.hot.manage.entity.common.group.Groupsiteimgpath;
import com.hot.manage.entity.common.group.Map_group_xq;
import com.hot.manage.entity.common.group.Sitedev;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;


@Transactional
@Service
public class GroupallServiceImpl implements GroupallService {
	@Autowired
	private GroupallMapper groupallMapper;

	@Autowired
	private IsxistMapper isxistMapper;

	@Autowired
	private TItemPicMapper tItemPicMapper;

	// 项目列表
	@Override
	public PageInfo<Grouplist> selectgrouplist(Integer userid, Integer moduleid, String starttime, String endtime,
			String keywords, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Grouplist> page = groupallMapper.selectgrouplist(userid, moduleid, starttime, endtime, keywords, pageNum,
				pageSize);
		List<Grouplist> list = ConverUtil.converPage(page, Grouplist.class);
		if (list.size() == 0) {
			return null;
		} else {
			return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		}
	}

	// 项目设备状态count
	@Override
	public List<Groupdevstate> selectgroupdevstate(Integer userid, Integer moduleid) {
		List<Groupdevstate> selectgroupdevstate = groupallMapper.selectgroupdevstate(userid, moduleid);
		return selectgroupdevstate;
	}

	// 地图弹框信息
	@Override
	public Map_group_xq mapgroupdevstate(Integer groupid, Integer moduleid, Integer userid) {
		return groupallMapper.mapgroupdevstate(groupid, moduleid, userid);
	}

	// 项目下设备个数
	@Override
	public Groupdevcount selectgroupdevcount(Integer userid, Integer moduleid, Integer groupid) {
		return groupallMapper.selectgroupdevcount(userid, moduleid, groupid);
	}

	// 根据id查项目
	@Override
	public List<Groupone> selectgroupbyid(Integer groupid) {
		return groupallMapper.selectgroupbyid(groupid);
	}

	// 删除项目
	@Override
	public Integer delgroup(Integer groupid) {
		groupallMapper.delitem(groupid);
		return groupallMapper.delgroup(groupid);
	}

	// 增加项目
	@Override
	public Integer insertgroup(Groups groups) {
		int ii = groupallMapper.insertgroup(groups);
		if (ii > 0) {
			if (groups.getSitelist() != null) {
				int a = groups.getSitelist().size();
				for (int i = 0; i < a; i++) {
					groupallMapper.insertgroupimg(groups.getId(), groups.getSitelist().get(i).getPicnum(),
							groups.getSitelist().get(i).getPicpath(), groups.getSitelist().get(i).getSite());
				}
			}
			groupallMapper.insert_dev_group_user(groups.getId(), groups.getUserid());
		}
		return ii;
	}

	// 修改项目
	@Override
	public Integer updategroups(Groups groups) {
		int ii = groupallMapper.updategroups(groups);

		if (ii > 0) {
			delallgroupimg(groups.getId());// 删除楼层
			if (groups.getSitelist() != null) {
				for (Groupmaps g : groups.getSitelist()) {
					if (g.getId() == null) {
						groupallMapper.insertgroupimg(groups.getId(), g.getPicnum(), g.getPicpath(), g.getSite());
					} else {
						TItemPic tItemPic = new TItemPic();
						tItemPic.setId(g.getId());
						tItemPic.setIsdelete(false);
						tItemPic.setIsenable(true);
						int selective = tItemPicMapper.updateByPrimaryKeySelective(tItemPic);
						if (selective <= 0) {
							throw new MyException("0", "失败");
						}
					}
				}
			}
		}
		return ii;

	}

	// 查询项目下的楼层图片,楼层id,楼层名称
	@Override
	public List<Groupmapimg> selectgroupmapimg(Integer groupid, Integer userid, Integer moduleid) {
		return groupallMapper.selectgroupmapimg(groupid, userid, moduleid);
	}

	// 设备楼层和地图
	@Override
	public List<Groupsiteimgpath> selectgroupsite_imgpath(Integer groupid, Integer userid, Integer moduleid) {
		return groupallMapper.selectgroupsite_imgpath(groupid, userid, moduleid);
	}

	// 查询楼层下的设备
	@Override
	public List<Sitedev> selectsitedev(Integer mapimgidid, Integer moduleid) {
		return groupallMapper.selectsitedev(mapimgidid, moduleid);
	}

	// 删除设备
	@Override
	public Integer deldev(Integer devid, Integer moduleid) {
		Isexist res = isxistMapper.selectsiterelation(devid, moduleid);
		Integer aa = res.getDgcount();
		if (aa > 0) {
			groupallMapper.deldev(devid, moduleid);
			return groupallMapper.deldev2(devid, moduleid);
		} else {
			return groupallMapper.deldev(devid, moduleid);
		}
	}

	// 删除设备（楼层关联表）
	@Override
	public Integer deldev2(Integer devid, Integer moduleid) {
		return groupallMapper.deldev2(devid, moduleid);
	}

	@Override
	public Integer insertdevmapimg(String picnum, String picpath, String site, Integer itemid) {
		return groupallMapper.insertdevmapimg(picnum, picpath, site, itemid);
	}

	// 删除楼层图片
	@Override
	public Integer deldevmapimg(Integer mapimgid, Integer moduleid) {

		Isexist res = isxistMapper.selectsiterelation2(mapimgid, moduleid);
		Integer aa = res.getDgcount();
		if (aa > 0) {
			return -1;
		} else {
			return groupallMapper.deldevmapimg(mapimgid);
		}
	}

	// 修改项目前的删除所有楼层图片
	@Override
	public Integer delallgroupimg(Integer groupid) {
		return groupallMapper.delallgroupimg(groupid);
	}

	// 项目下设备的数量
	@Override
	public Groupdevcount selectmapimgcount(Integer itemid, String site) {
		return groupallMapper.selectmapimgcount(itemid, site);
	}

	// 修改项目的xy
	@Override
	public Integer updategroupxy(Integer groupid, Double x, Double y, String groupcode) {
		return groupallMapper.updategroupxy(groupid, x, y, groupcode);
	}

	// 修改设备xy
	@Override
	public Integer updatedevxy(Integer id, Double x, Double y, String code, Integer moduleid) {
		return groupallMapper.updatedevxy(id, x, y, code, moduleid);
	}

	/**
	 * 查询当前用户指定模块下的项目
	 */
	@Override
	public List<Grouplist> selectAllItem(Integer userid, Integer moduleid) throws MyException {
		List<Grouplist> selectAllItem = groupallMapper.selectAllItem(userid, moduleid);
		return selectAllItem;
	}

	@Override
	public List<Groupmap> selectgroupmap(Integer userid, Integer moduleid) {
		return groupallMapper.selectgroupmap(userid, moduleid);
	}

	@Override
	public List<AllDevByGroupID> selectDevByGroupID(Integer groupid, Integer moduleid) {
		return groupallMapper.selectDevByGroupID(groupid, moduleid);
	}

	@Override
	public Integer updateItemPic(Integer itemid, String picurl) throws MyException {
		// TODO Auto-generated method stub
		return groupallMapper.updateItemPic(itemid,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), picurl);
	}

}
