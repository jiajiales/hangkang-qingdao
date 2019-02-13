package com.hot.manage.service.system;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.manage.db.system.TModuleMapper;
import com.hot.manage.entity.system.TModule;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TModuleServiceImpl implements TModuleService {

	@Autowired
	private TModuleMapper moduleMapper;

	@Override
	public Integer insertObject(TModule params, Integer userid) throws MyException {
		return moduleMapper.insertSelective(params);
	}

	@Override
	public Integer insertModule(TModule params) throws MyException {
		params.setAddtime(ConverUtil.timeForString(new Date()));
		Example example = new Example(TModule.class);
		example.createCriteria().andEqualTo("name", params.getName()).andEqualTo("isdelete", 0).andEqualTo("isenable", 1);
		int selectCountByExample = moduleMapper.selectCountByExample(example);
		if(selectCountByExample>=1){
			throw new MyException("0", "该模块已存在，请勿重复添加");
		}
		return moduleMapper.insertSelective(params);
	}

	@Override
	public Integer updateObject(TModule params) throws MyException {
		Example example = new Example(TModule.class);
		example.createCriteria().andEqualTo("name", params.getName()).andEqualTo("isdelete", 0).andEqualTo("isenable", 1).andNotEqualTo("id", params.getId());
		int i = moduleMapper.selectCountByExample(example);
		if(i>=1){
			throw new MyException("0", "模块名已存在");
		}
		return moduleMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public Integer delObject(Integer id) throws MyException {
		TModule module = new TModule();
		module.setId(id);
		module.setIsdelete(true);
		module.setIsenable(false);
		return moduleMapper.updateByPrimaryKeySelective(module);
	}

	@Override
	public TModule selectObject(Integer id) throws MyException {
		return moduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TModule> selectListTModule() throws MyException {	
		return moduleMapper.selectTModule();
	}

	// 查询当前用户所有的模块
	@Override
	public List<TModule> selectModuleByUserId(Integer userid) throws MyException {
		return moduleMapper.selectTModuleByUserId(userid);
	}

}
