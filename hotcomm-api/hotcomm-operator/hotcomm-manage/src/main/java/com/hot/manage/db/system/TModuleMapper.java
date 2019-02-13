package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hot.manage.entity.system.TModule;

import tk.mybatis.mapper.common.Mapper;

public interface TModuleMapper extends Mapper<TModule> {

	List<TModule> selectTModule();

	List<TModule> selectTModuleByUserId(@Param("userid") Integer userid);
}
