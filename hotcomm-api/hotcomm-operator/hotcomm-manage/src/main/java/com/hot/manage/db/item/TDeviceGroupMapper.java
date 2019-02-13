package com.hot.manage.db.item;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.mc.ItemData;

import tk.mybatis.mapper.common.Mapper;

public interface TDeviceGroupMapper extends Mapper<TDeviceGroup> {
	/**
	 * 当前用户下的设备终端总数
	 * 
	 * @param params
	 * @return
	 */
	Integer selectDevNum(@Param("params") Map<String, Integer> params);
	
	Integer selectItemNum(@Param("userid")Integer userid,@Param("moduleid")Integer moduleid);

	/**
	 * 当前用户所有项目、按时间段查询、按项目名称查询
	 * 
	 * @return
	 */
	Page<TDeviceGroupVo> selectItems(ItemParam params);

	List<TDeviceGroupVo> selectAllItems(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	TDeviceGroupVo selectItemByGroupId(@Param("params") Map<String, Integer> params);

	List<TDeviceGroup> selectItemByExemple(@Param("params") Map<String, Integer> params);

	// 我的项目数据
	Page<ItemData> selectYgItemData(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

}