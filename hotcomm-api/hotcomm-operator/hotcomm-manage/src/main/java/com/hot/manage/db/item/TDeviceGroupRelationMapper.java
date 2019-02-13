package com.hot.manage.db.item;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.item.TDeviceGroupRelation;

import tk.mybatis.mapper.common.Mapper;

public interface TDeviceGroupRelationMapper extends Mapper<TDeviceGroupRelation> {

	List<TDeviceGroupRelation> selectListById(@Param("groupid") Integer groupid);

	Integer insertDevBath(@Param("params") List<TDeviceGroupRelation> params);

	TDeviceGroupRelation selectByDevIdAndModuleid(@Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid);

	Integer LKTDCDevIdByMac(@Param("mac") String mac);

}
