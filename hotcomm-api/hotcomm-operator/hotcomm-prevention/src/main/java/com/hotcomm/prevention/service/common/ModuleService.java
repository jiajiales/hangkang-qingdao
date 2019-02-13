package com.hotcomm.prevention.service.common;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hotcomm.prevention.bean.mysql.common.entity.TModule;
import com.hotcomm.prevention.exception.MyException;

public interface ModuleService {

	List<TModule> selectModuleAll() throws MyException;
	
	List<TModule> selectModuleByUserId(Integer userid) throws MyException;
	
	String batchImport(String fileName, MultipartFile file,Integer userid) throws Exception;

}
