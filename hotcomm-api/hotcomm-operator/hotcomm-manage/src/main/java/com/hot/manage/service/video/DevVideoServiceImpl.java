package com.hot.manage.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.video.TDevVideoRelationMapper;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class DevVideoServiceImpl implements DevVideoService {
	
	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;

	//查询设备是否已关联摄像头
	@Override
	public boolean checkDevVideoRelation(Integer moduleid,Integer deviceId) throws MyException {
		Example example = new Example(TDevVideoRelation.class);
		example.createCriteria().andEqualTo("deviceid", deviceId).andEqualTo("moduleid", moduleid).andEqualTo("isdelete",
				false);
		List<TDevVideoRelation> list = tDevVideoRelationMapper.selectByExample(example);
		if (list.size() != 0) {
			return true;
		}
		return false;
	}

	//新建设备，分配到摄像头
	@Override
	public Integer insertDevVideoRelation(TDevVideoRelation param) throws MyException {
		param.setIsdelete(0);
		return tDevVideoRelationMapper.insertSelective(param);
	}

	//设备与摄像头重绑
	@Override
	public Integer updateDevVideoRelation(Integer moduleid,Integer devid,Integer videoid) throws MyException {
		return tDevVideoRelationMapper.updateDevVideoRelation(moduleid, devid, videoid);
	}

	//设备与摄像头解绑
	@Override
	public Integer cutDevVideoRelation(Integer moduleid, Integer devid) throws MyException {
		return tDevVideoRelationMapper.cutDevVideoRelation(moduleid, devid);
	}


}
