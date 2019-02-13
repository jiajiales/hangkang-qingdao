package com.hot.manage.service.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hot.manage.db.item.TDeviceGroupRelationMapper;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
public class TDeviceGroupRelationServiceImpl implements TDeviceGroupRelationService {
	@Autowired
	private TDeviceGroupRelationMapper deviceGroupRelationMapper;

	@Override
	public Integer updateById(TDeviceGroupRelation relation) throws MyException {
		return deviceGroupRelationMapper.updateByPrimaryKeySelective(relation);
	}

	@Override
	public List<TDeviceGroupRelation> selectListById(Integer groupid) throws MyException {
		return deviceGroupRelationMapper.selectListById(groupid);
	}

	@Override
	public Integer insertDev(TDeviceGroupRelation relation) throws MyException {
		return deviceGroupRelationMapper.insertSelective(relation);
	}

	@Override
	public Integer insertDevBacht(List<TDeviceGroupRelation> relations) throws MyException {
		return deviceGroupRelationMapper.insertDevBath(relations);
	}

	@Override
	public TDeviceGroupRelation selectRelation(Integer deviceid, Integer moduleid) throws MyException {
		return deviceGroupRelationMapper.selectByDevIdAndModuleid(deviceid, moduleid);
	}

	@Override
	public Integer updateByDevIdAndModuleId(TDeviceGroupRelation relation) throws MyException {
		Example example = new Example(TDeviceGroupRelation.class);
		example.createCriteria().andEqualTo("deviceid", relation.getDeviceid()).andEqualTo("moduleid",
				relation.getModuleid());
		return deviceGroupRelationMapper.updateByExampleSelective(relation, example);
	}

	@Override
	public List<TDeviceGroupRelation> selectListByExample(Integer groupid, Integer moduleid) throws MyException {
		Example example = new Example(TDeviceGroupRelation.class);
		example.createCriteria().andEqualTo("groupid", groupid).andEqualTo("moduleid", moduleid).andEqualTo("isenable",
				true);
		List<TDeviceGroupRelation> list = deviceGroupRelationMapper.selectByExample(example);
		return list;
	}

}
