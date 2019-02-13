package com.hot.manage.service.item;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.db.item.TDeviceGroupMapper;
import com.hot.manage.db.item.TItemPicMapper;
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
public class TDeviceGroupServiceImpl implements TDeviceGroupService {

	@Autowired
	private TDeviceGroupMapper deviceGroupMapper;
	@Autowired
	private TUserDgroupRelationService userDgroupRelationService;
	@Autowired
	private TDeviceGroupRelationService deviceGroupRelationService;
	@Autowired
	private TItemPicMapper itemPicMapper;

	@Override
	public Integer insert(Integer userid, GroupParam params, String pics) throws MyException {
		// 判断是否存在相同的项目名
		Example example = new Example(TDeviceGroup.class);
		example.createCriteria().andEqualTo("groupname", params.getGroupname())
				.orEqualTo("itemnum", params.getItemnum()).andEqualTo("moduleid", params.getModuleid())
				.andEqualTo("isenable", true);
		List<TDeviceGroup> list = deviceGroupMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此项目名或项目编号已存在");
		}
		params.setAddtime(ConverUtil.timeForString(new Date()));
		TDeviceGroup group = new TDeviceGroup();
		BeanUtils.copyProperties(params, group);
		int add = deviceGroupMapper.insertSelective(group);
		if (add <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 绑定项目与用户
		TUserDgroupRelation record = new TUserDgroupRelation();
		record.setAddtime(new Date());
		record.setAdduserid(userid);
		record.setDevicegroupid(group.getId());
		record.setUserid(userid);
		int in = userDgroupRelationService.insertObject(record);
		if (in <= 0) {
			throw new MyException("0", "操作失败");
		}
		System.out.println(pics);
		List<TItemPic> picss = new Gson().fromJson(pics, new TypeToken<List<TItemPic>>() {
		}.getType());
		// 绑定项目与图片
		if (picss!= null) {
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
		List<TDeviceGroup> list = deviceGroupMapper.selectByExample(example);
		example.createCriteria().andEqualTo("itemnum", params.getItemnum()).andEqualTo("moduleid", params.getModuleid())
				.andEqualTo("isenable", true).andNotEqualTo("id", params.getId());
		List<TDeviceGroup> list2 = deviceGroupMapper.selectByExample(example);
		if (list.size() != 0 || list2.size() != 0) {
			throw new MyException("0", "此项目名或者项目编号已存在");
		}
		TDeviceGroup group = new TDeviceGroup();
		BeanUtils.copyProperties(params, group);
		int update = deviceGroupMapper.updateByPrimaryKeySelective(group);
		if (update <= 0) {
			throw new MyException("0", "操作失败");
		}
		System.out.println(pics);
		List<TItemPic> picss = new Gson().fromJson(pics, new TypeToken<List<TItemPic>>() {
		}.getType());
		// 删除、添加
		if (picss!= null) {
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
		List<TDeviceGroupRelation> list = deviceGroupRelationService.selectListByExample(groupid, moduleid);
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
		int upGroup = deviceGroupMapper.updateByPrimaryKeySelective(record);
		if (upGroup <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 删除项目与用户的绑定

		return 1;
	}

	@Override
	public TDeviceGroup selectByName(String name, Integer moduleid) throws MyException {
		TDeviceGroup record = new TDeviceGroup();
		record.setGroupname(name);
		record.setModuleid(moduleid);
		return deviceGroupMapper.selectOne(record);
	}

	@Override
	public PageInfo<TDeviceGroupVo> selectItems(ItemParam params) throws MyException {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		Page<TDeviceGroupVo> page = deviceGroupMapper.selectItems(params);
		List<TDeviceGroupVo> list = ConverUtil.converPage(page, TDeviceGroupVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer selectDevNum(Integer userid, Integer moduleid) throws MyException {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("userid", userid);
		params.put("moduleid", moduleid);
		return deviceGroupMapper.selectDevNum(params);
	}

	@Override
	public TDeviceGroupVo selectById(Integer groupid, Integer moduleid) throws MyException {
		Map<String, Integer> map = new HashMap<>();
		map.put("groupid", groupid);
		map.put("moduleid", moduleid);
		TDeviceGroupVo vo = deviceGroupMapper.selectItemByGroupId(map);
		Example example = new Example(TItemPic.class);
		example.createCriteria().andEqualTo("itemid", groupid).andEqualTo("isenable", true);
		List<TItemPic> lsit = itemPicMapper.selectByExample(example);
		vo.setPics(lsit);
		return vo;
	}

	// 查询当前用户下所有的项目组
	@Override
	public List<TDeviceGroup> selectItemByUserId(Integer userid, Integer moduleid) throws MyException {
		Map<String, Integer> params = new HashMap<>();
		params.put("userid", userid);
		params.put("moduleid", moduleid);
		return deviceGroupMapper.selectItemByExemple(params);
	}

	@Override
	public List<TDeviceGroupVo> selectAllItems(Integer userid, Integer moduleid) throws MyException {
		return deviceGroupMapper.selectAllItems(userid, moduleid);
	}

	// 我的项目数据
	@Override
	public PageInfo<ItemData> selectYgItemData(Integer pageNum,Integer pageSize,Integer userid, Integer moduleid) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<ItemData> page = deviceGroupMapper.selectYgItemData(userid, moduleid);
		List<ItemData> rows = ConverUtil.converPage(page, ItemData.class);
		return new PageInfo<>(pageNum, pageSize, page.getTotal(), rows);
	}

	@Override
	public Integer selectItemNum(Integer userid, Integer moduleid) throws MyException {
		return deviceGroupMapper.selectItemNum(userid, moduleid);
	}
}
