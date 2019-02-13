package com.hot.manage.db.mc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.mc.ItemData;

import tk.mybatis.mapper.common.Mapper;

public interface McGroupMapper extends Mapper<TDeviceGroup> {

	List<TDeviceGroupVo> selectMcGroupInfo(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	Page<TDeviceGroupVo> selectMcGroupPageInfo(ItemParam params);

	Integer selectMcDevNum(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	Integer selectMcDevNumByGroupId(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid);
	
	List<ItemData> selectMcItemData(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);
}
