package com.hot.manage.service.imp.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.yg.TAlarmStateMapper;
import com.hot.manage.entity.yg.TAlarmState;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.TAlarmStateService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TAlarmStateServiceImpl implements TAlarmStateService {

	@Autowired
	private TAlarmStateMapper talarmStateMapper;

	@Override
	public List<TAlarmState> SelectAll() throws MyException {
		// TODO Auto-generated method stub
		Example example = new Example(TAlarmState.class);
		example.createCriteria().andEqualTo("isdelete", 0);
		example.orderBy("addtime").desc();
		List<TAlarmState> list = talarmStateMapper.selectByExample(example);
		return list;
	}

	@Override
	public Integer deleteOne(Integer id) throws MyException {
		// TODO Auto-generated method stub
		Integer sum = talarmStateMapper.checkState(id);
		if (sum > 0) {
			throw new MyException("0", "该状态有绑定设备,无法删除");
		}
		TAlarmState a = new TAlarmState();
		a.setId(id);
		a.setIsdelete(1);
		Integer i = talarmStateMapper.updateByPrimaryKeySelective(a);
		return i;
	}

	@Override
	public Integer updateOne(TAlarmState talarmState) throws MyException {
		// TODO Auto-generated method stub
		Example example = new Example(TAlarmState.class);
		example.createCriteria().andEqualTo("state_name", talarmState.getState_name()).andEqualTo("isdelete", 0).andNotEqualTo("id", talarmState.getId());
		Integer sum = talarmStateMapper.selectCountByExample(example);
		if (sum > 0) {
			throw new MyException("0", "输入的状态名已存在");
		}

		Integer i = talarmStateMapper.updateByPrimaryKeySelective(talarmState);
		return i;
	}

	@Override
	public Integer insertOne(TAlarmState talarmState) throws MyException {
		// TODO Auto-generated method stub
		Example example = new Example(TAlarmState.class);
		example.createCriteria().andEqualTo("state_name", talarmState.getState_name()).andEqualTo("isdelete", 0);
		Integer sum = talarmStateMapper.selectCountByExample(example);
		if (sum > 0) {
			throw new MyException("0", "输入的状态名已存在");
		}
		Integer i = talarmStateMapper.insertSelective(talarmState);
		return i;
	}

	@Override
	public Page<TAlarmState> findByPage(int pageNo, int pageSize) throws MyException {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNo, pageSize);
		Page<TAlarmState> list = (Page<TAlarmState>) SelectAll();
		System.out.println(list);
		return list;
	}

	@Override
	public TAlarmState selectById(Integer id) throws MyException {
		// TODO Auto-generated method stub
		TAlarmState tAlarmState = talarmStateMapper.selectByPrimaryKey(id);
		return tAlarmState;
	}

}
