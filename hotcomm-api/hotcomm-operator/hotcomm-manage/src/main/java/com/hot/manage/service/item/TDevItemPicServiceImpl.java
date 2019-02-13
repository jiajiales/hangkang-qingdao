package com.hot.manage.service.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.item.TDevItemPicMapper;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TDevItemPicServiceImpl implements TDevItemPicService {
	@Autowired
	private TDevItemPicMapper devItemPicMapper;

	@Override
	public Integer insert(TDevItemPic param) throws MyException {
		return devItemPicMapper.insertSelective(param);
	}

	@Override
	public Integer insertBatch(List<TDevItemPic> param) throws MyException {
		return devItemPicMapper.insertBatch(param);
	}

	@Override
	public Integer update(TDevItemPic param) throws MyException {

		Example example = new Example(TDevItemPic.class);
		example.createCriteria().andEqualTo("devId", param.getDevId()).andEqualTo("moduleid", param.getModuleid())
				.andEqualTo("isenable", true);
		int up = devItemPicMapper.updateByExampleSelective(param, example);
		if (up <= 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<TDevItemPic> selectList(Integer itemPicId) throws MyException {
		return devItemPicMapper.selectList(itemPicId);
	}

	@Override
	public TDevItemPic selectOne(Integer id) throws MyException {
		return devItemPicMapper.selectByPrimaryKey(id);
	}

	@Override
	public TDevItemPic selectByExample(Integer deviceId, Integer moduleid) throws MyException {
		Example example = new Example(TDevItemPic.class);
		example.createCriteria().andEqualTo("devId", deviceId).andEqualTo("moduleid", moduleid).andEqualTo("isenable",
				true);
		List<TDevItemPic> list = devItemPicMapper.selectByExample(example);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}
