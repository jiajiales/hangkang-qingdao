package com.hot.manage.service.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.util.TextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.manage.db.item.TUserDgroupRelationMapper;
import com.hot.manage.entity.ModuleItemNode;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TUserDgroupRelation;
import com.hot.manage.entity.item.UserItemParam;
import com.hot.manage.entity.system.TModule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TModuleService;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TUserDgroupRelationServiceImpl implements TUserDgroupRelationService {

	@Autowired
	private TUserDgroupRelationMapper userDgroupRelationMapper;
	@Autowired
	private TModuleService moduleService;

	@Override
	public Integer insertObject(TUserDgroupRelation params) throws MyException {
		int insert = userDgroupRelationMapper.insertSelective(params);
		return insert;
	}

	@Override
	public Integer insertObject(TUserDgroupRelation params, Integer userid) throws MyException {
		params.setUserid(userid);
		return userDgroupRelationMapper.insertSelective(params);
	}

	@Override
	public Integer updateObject(TUserDgroupRelation params) throws MyException {
		Example example = new Example(TUserDgroupRelation.class);
		example.createCriteria().andEqualTo("userid", params.getUserid())
				.andEqualTo("devicegroupid", params.getDevicegroupid()).andEqualTo("isenable", true);
		int update = userDgroupRelationMapper.updateByExampleSelective(params, example);
		return update;
	}

	@Override
	public Integer delObject(Integer id) throws MyException {
		TUserDgroupRelation params = new TUserDgroupRelation();
		params.setId(id);
		params.setIsenable(false);
		params.setIsdelete(true);
		Integer update = updateObject(params);
		return update;
	}

	@Override
	public TUserDgroupRelation selectObject(Integer id) throws MyException {
		TUserDgroupRelation relation = userDgroupRelationMapper.selectByPrimaryKey(id);
		return relation;
	}

	@Override
	public TUserDgroupRelation selectObjectByParams(TUserDgroupRelation params) throws MyException {
		return userDgroupRelationMapper.selectOne(params);
	}
	
	// 批量修改
	@Override
	public Integer updateBatch(UserItemParam param) throws MyException {
		Map<String, Object> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		String groupids = param.getGroupids();
		String[] str = groupids.split(",");
		map.put("isenable", false);
		map.put("isdelete", true);
		map.put("userid", param.getId());
		for (String ss : str) {
			list.add(ss);
		}
		map.put("list", list);
		return userDgroupRelationMapper.updateBatch(map);
	}

	@Override
	public List<ModuleItemNode> ModuleItemNode(Integer userid) throws MyException {
		List<ModuleItemNode> nodes = new ArrayList<>();
		List<TModule> modules = moduleService.selectModuleByUserId(userid);
		for (TModule tModule : modules) {
			ModuleItemNode node = new ModuleItemNode();
			BeanUtils.copyProperties(tModule, node);
			node.setGroupname(tModule.getName());
			List<TDeviceGroup> groups = userDgroupRelationMapper.selectDevGroup(userid, tModule.getId());
			node.setChildren(groups);
			nodes.add(node);
		}
		return nodes;
	}
	
	
	 
	 /**
	  * 用户与项目绑定或解除
	  * @param param
	  * @throws MyException
	  */
	@Override
	public void userItemRelation(UserItemParam param) throws MyException {
		List<TUserDgroupRelation> list = new ArrayList<>();
		Example example = new Example(TUserDgroupRelation.class);
		example.createCriteria().andEqualTo("userid", param.getId())
		.andNotEqualTo("adduserid", param.getId())
		.andEqualTo("isenable", true);
		TUserDgroupRelation record=new TUserDgroupRelation();
		record.setIsdelete(true);
		record.setIsenable(false);
		userDgroupRelationMapper.updateByExample(record, example);	
		if (!TextUtils.isEmpty(param.getGroupids())) {
			String[] str = param.getGroupids().split(",");
			for (String ss : str) {
				TUserDgroupRelation relation = new TUserDgroupRelation();
				relation.setAddtime(new Date());
				relation.setAdduserid(param.getAdduserid());
				relation.setDevicegroupid(Integer.parseInt(ss));
				relation.setUserid(param.getId());
				list.add(relation);
			}
			userDgroupRelationMapper.insertBatchData(list);
		}
	}
}
