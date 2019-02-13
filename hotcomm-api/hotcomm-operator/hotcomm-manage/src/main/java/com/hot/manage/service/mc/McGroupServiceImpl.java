package com.hot.manage.service.mc;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.db.item.TDeviceGroupRelationMapper;
import com.hot.manage.db.item.TItemPicMapper;
import com.hot.manage.db.item.TUserDgroupRelationMapper;
import com.hot.manage.db.mc.McGroupMapper;
import com.hot.manage.entity.GroupParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.entity.item.TUserDgroupRelation;
import com.hot.manage.entity.mc.ItemData;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class McGroupServiceImpl implements McGroupService {
	@Autowired
	private McGroupMapper mcGroupMapper;
	@Autowired
	private TItemPicMapper itemPicMapper;
	@Autowired
	private TDeviceGroupRelationMapper deviceGroupRelationMapper;
	@Autowired
	private TUserDgroupRelationMapper userDgroupRelationMapper;

	@Override
	public Integer insert(Integer userid, GroupParam params, String pics) throws MyException {
		// 判断是否存在相同的项目名
		Example example = new Example(TDeviceGroup.class);
		example.createCriteria().andEqualTo("groupname", params.getGroupname())
				.orEqualTo("itemnum", params.getItemnum()).andEqualTo("moduleid", params.getModuleid())
				.andEqualTo("isenable", true);
		List<TDeviceGroup> list = mcGroupMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此项目名或项目编号已存在");
		}
		params.setAddtime(ConverUtil.timeForString(new Date()));
		TDeviceGroup group = new TDeviceGroup();
		BeanUtils.copyProperties(params, group);
		int add = mcGroupMapper.insertSelective(group);
		if (add <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 绑定项目与用户
		TUserDgroupRelation record = new TUserDgroupRelation();
		record.setAddtime(new Date());
		record.setAdduserid(userid);
		record.setDevicegroupid(group.getId());
		record.setUserid(userid);
		int in = userDgroupRelationMapper.insertSelective(record);
		if (in <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 绑定项目与图片
		List<TItemPic> picss = new Gson().fromJson(pics, new TypeToken<List<TItemPic>>() {
		}.getType());
		if (picss.size() != 0) {
			for (TItemPic pic : picss) {
				pic.setAddtime(params.getAddtime());
				pic.setItemid(group.getId());
				itemPicMapper.insertSelective(pic);
			}
		}
		return 1;
	}

	@Override
	public Integer update(GroupParam params, String pics) throws MyException {
		// 判断是否存在相同的项目名
		Example example = new Example(TDeviceGroup.class);
		example.createCriteria().andEqualTo("groupname", params.getGroupname())
				.andEqualTo("moduleid", params.getModuleid()).andEqualTo("isenable", true)
				.andNotEqualTo("id", params.getId());
		List<TDeviceGroup> list = mcGroupMapper.selectByExample(example);
		Example ex = new Example(TDeviceGroup.class);
		ex.createCriteria().andEqualTo("itemnum", params.getItemnum()).andEqualTo("moduleid", params.getModuleid())
				.andEqualTo("isenable", true).andNotEqualTo("id", params.getId());
		List<TDeviceGroup> list2 = mcGroupMapper.selectByExample(ex);
		if (list.size() != 0 || list2.size() != 0) {
			throw new MyException("0", "此项目名或者项目编号已存在");
		}
		TDeviceGroup group = new TDeviceGroup();
		BeanUtils.copyProperties(params, group);
		int update = mcGroupMapper.updateByPrimaryKeySelective(group);
		if (update <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 删除、添加
		List<TItemPic> picss = new Gson().fromJson(pics, new TypeToken<List<TItemPic>>() {
		}.getType());
		if (picss != null) {
			List<TItemPic> opics = itemPicMapper.selectList(params.getId());
			for (TItemPic tItemPic : picss) {
				for (int i = 0; i < opics.size(); i++) {
					if (tItemPic.getPicpath().equals(opics.get(i).getPicpath())) {
						opics.remove(i);
						continue;
					} else {
						int add = itemPicMapper.insertSelective(tItemPic);
						if (add <= 0) {
							throw new MyException("0", "操作失败");
						}
					}
				}
			}
			// 当集合里面还存在数据，删除集合里面的数据
			if (opics.size() != 0) {
				for (TItemPic tItemPic : opics) {
					tItemPic.setIsdelete(true);
					tItemPic.setIsenable(false);
					tItemPic.setUpdatetime(ConverUtil.timeForString(new Date()));
					int up = itemPicMapper.updateByPrimaryKeySelective(tItemPic);
					if (up <= 0) {
						throw new MyException("0", "操作失败");
					}
				}
			}

		}
		return 1;
	}

	@Override
	public Integer del(Integer groupid, Integer moduleid, Integer userid) throws MyException {
		Example example = new Example(TDeviceGroupRelation.class);
		example.createCriteria().andEqualTo("groupid", groupid).andEqualTo("moduleid", moduleid).andEqualTo("isenable",
				true);
		List<TDeviceGroupRelation> list = deviceGroupRelationMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此项目绑定有设备，操作失败");
		}
		// 删除项目与图片的绑定
		Example ex = new Example(TItemPic.class);
		ex.createCriteria().andEqualTo("itemid", groupid).andEqualTo("isenable", true);
		List<TItemPic> piclist = itemPicMapper.selectByExample(ex);
		if (piclist.size() != 0) {
			for (TItemPic tItemPic : piclist) {
				tItemPic.setUpdatetime(ConverUtil.timeForString(new Date()));
				tItemPic.setIsdelete(true);
				tItemPic.setIsenable(false);
				int up = itemPicMapper.updateByPrimaryKeySelective(tItemPic);
				if (up <= 0) {
					throw new MyException("0", "操作失败");
				}
			}
		}
		// 删除项目
		TDeviceGroup record = new TDeviceGroup();
		record.setId(groupid);
		record.setIsdelete(true);
		record.setIsenable(false);
		int upGroup = mcGroupMapper.updateByPrimaryKeySelective(record);
		if (upGroup <= 0) {
			throw new MyException("0", "操作失败");
		}
		return 1;
	}

	@Override
	public List<TDeviceGroupVo> selectMcGroupInfo(Integer userid, Integer moduleid) throws MyException {
		return mcGroupMapper.selectMcGroupInfo(userid, moduleid);
	}

	@Override
	public PageInfo<TDeviceGroupVo> selectMcGroupPage(ItemParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TDeviceGroupVo> page = mcGroupMapper.selectMcGroupPageInfo(param);
		List<TDeviceGroupVo> list = ConverUtil.converPage(page, TDeviceGroupVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	// 当前用户mc模块的总设备数量
	@Override
	public Integer selectMcDevNum(Integer userid, Integer moduleid) throws MyException {
		return mcGroupMapper.selectMcDevNum(userid, moduleid);
	}

	// 指定项目的终端设备数量
	@Override
	public Integer selectMcDevNumByGroupId(Integer groupid, Integer moduleid) throws MyException {

		return mcGroupMapper.selectMcDevNumByGroupId(groupid, moduleid);
	}

	// 我的项目数据
	@Override
	public List<ItemData> selectMcItemData(Integer userid, Integer moduleid) throws MyException {
		return mcGroupMapper.selectMcItemData(userid, moduleid);
	}

	// 单个项目查询
	@Override
	public TDeviceGroupVo selectMcGroup(Integer groupid, Integer moduleid) throws MyException {
		TDeviceGroupVo tDeviceGroupVo = new TDeviceGroupVo();
		Example example = new Example(TDeviceGroup.class);
		example.createCriteria().andEqualTo("id", groupid).andEqualTo("moduleid", moduleid).andEqualTo("isenable",
				true);
		TDeviceGroup tDeviceGroup = mcGroupMapper.selectByExample(example).get(0);
		BeanUtils.copyProperties(tDeviceGroup, tDeviceGroupVo);
		Example ex = new Example(TItemPic.class);
		ex.createCriteria().andEqualTo("itemid", groupid).andEqualTo("isenable", true);
		List<TItemPic> ss = itemPicMapper.selectByExample(ex);
		tDeviceGroupVo.setPics(ss);
		return tDeviceGroupVo;
	}

}
