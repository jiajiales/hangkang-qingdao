package com.hotcomm.prevention.db.mysql.common;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserDgroupRelation;
import com.hotcomm.prevention.bean.mysql.manage.group.entity.TDeviceGroup;

import tk.mybatis.mapper.common.Mapper;

public interface TUserDgroupRelationMapper extends Mapper<TUserDgroupRelation> {

	public Integer insertBatchData(@Param("params") List<TUserDgroupRelation> params);

	List<TDeviceGroup> selectDevGroup(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);
}