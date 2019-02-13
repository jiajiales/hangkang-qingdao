package com.hotcomm.prevention.db.mysql.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroupRelation;

import tk.mybatis.mapper.common.Mapper;

public interface TPowerGroupRelationMapper extends Mapper<TPowerGroupRelation> {

	void insertBatch(@Param("params") List<TPowerGroupRelation> params);

	List<Integer> selectResByRole(@Param("roleid") Integer roleid);

}
