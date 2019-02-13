package com.hotcomm.prevention.db.mysql.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TModule;
import com.hotcomm.prevention.service.common.ImportDevice;

import tk.mybatis.mapper.common.Mapper;

public interface ModuleMapper extends Mapper<TModule> {

	List<TModule> selectTModuleByUserId(@Param("userid") Integer userid);

	Integer checkMac(@Param("mac") String mac, @Param("moduleid") Integer moduleid);

	Integer batchInsertDevice(ImportDevice importDevice);

}
