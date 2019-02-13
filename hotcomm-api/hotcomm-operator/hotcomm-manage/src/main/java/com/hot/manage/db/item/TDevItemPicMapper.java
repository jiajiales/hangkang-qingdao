package com.hot.manage.db.item;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.item.TDevItemPic;

import tk.mybatis.mapper.common.Mapper;

public interface TDevItemPicMapper extends Mapper<TDevItemPic> {

	List<TDevItemPic> selectList(@Param("itemPicId") Integer itemPicId);

	Integer insertBatch(@Param("param") List<TDevItemPic> param);
}
