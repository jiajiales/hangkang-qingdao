package com.hot.manage.service.system;

import java.util.List;
import com.hot.manage.entity.system.TModule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;

public interface TModuleService extends CommonCrudService<TModule, TModule, Integer>{

	List<TModule> selectModuleByUserId(Integer userid) throws MyException;
	
	Integer insertModule(TModule params) throws MyException;
	
	List<TModule> selectListTModule() throws MyException;
}
