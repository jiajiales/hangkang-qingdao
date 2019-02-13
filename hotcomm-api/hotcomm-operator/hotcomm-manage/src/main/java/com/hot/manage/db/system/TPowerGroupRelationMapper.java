package com.hot.manage.db.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.system.TPowerGroupRelation;

import tk.mybatis.mapper.common.Mapper;

public interface TPowerGroupRelationMapper extends Mapper<TPowerGroupRelation> {

	void insertBatch(@Param("params") List<TPowerGroupRelation> params);

	void delBatch(@Param("params") Map<String, Object> params);

	List<TPowerGroupRelation> selectByResouceId(@Param("id") Integer id);
	
	List<Integer> selectResByRole(@Param("roleid") Integer roleid);

	String selectResFatherId(@Param("roleid")  Integer roleid, @Param("fatherId") Integer fatherId);

}
