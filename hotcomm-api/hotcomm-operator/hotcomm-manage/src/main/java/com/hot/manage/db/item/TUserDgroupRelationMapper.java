package com.hot.manage.db.item;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TUserDgroupRelation;
import com.hot.manage.entity.item.UserItemParam;

import tk.mybatis.mapper.common.Mapper;

public interface TUserDgroupRelationMapper extends Mapper<TUserDgroupRelation> {

	public Integer insertBatchData(@Param("params") List<TUserDgroupRelation> params);

	public Integer updateBatch(@Param("params") Map<String, Object> params);

	TUserDgroupRelation selectOneByParam(UserItemParam params);

	List<TDeviceGroup> selectDevGroup(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);
}