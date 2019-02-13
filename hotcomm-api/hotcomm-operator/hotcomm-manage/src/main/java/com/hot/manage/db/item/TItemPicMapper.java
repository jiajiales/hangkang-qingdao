package com.hot.manage.db.item;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.item.TItemPic;

import tk.mybatis.mapper.common.Mapper;

public interface TItemPicMapper extends Mapper<TItemPic> {

	List<TItemPic> selectList(@Param("groupid") Integer groupid);

	TItemPic selectTItemPic(@Param("site") String site, @Param("groupid") Integer groupid);
}
