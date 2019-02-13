package com.hotcomm.prevention.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.prevention.bean.mysql.common.entity.TModule;
import com.hotcomm.prevention.bean.mysql.common.entity.TUserDgroupRelation;
import com.hotcomm.prevention.bean.mysql.common.params.UserItemParam;
import com.hotcomm.prevention.bean.mysql.common.vo.ModuleItemNode;
import com.hotcomm.prevention.bean.mysql.manage.group.entity.TDeviceGroup;
import com.hotcomm.prevention.db.mysql.common.TUserDgroupRelationMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.TextUtils;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TUserDgroupRelationServiceImpl implements TUserDgroupRelationService {

	@Autowired
	private TUserDgroupRelationMapper userDgroupRelationMapper;
	@Autowired
	private ModuleService moduleService;

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
				relation.setAddtime(ConverUtil.dateForString(new Date()));
				relation.setAdduserid(param.getAdduserid());
				relation.setDevicegroupid(Integer.parseInt(ss));
				relation.setUserid(param.getId());
				list.add(relation);
			}
			userDgroupRelationMapper.insertBatchData(list);
		}
	}
}
