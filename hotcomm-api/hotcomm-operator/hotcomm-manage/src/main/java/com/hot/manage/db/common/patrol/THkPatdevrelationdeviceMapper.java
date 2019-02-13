package com.hot.manage.db.common.patrol;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.patrol.THkPatdevrelationdeviceVo;
import com.hot.manage.entity.common.patrol.bean.THkPatdevrelationdevice;

import tk.mybatis.mapper.common.Mapper;

public interface THkPatdevrelationdeviceMapper extends Mapper<THkPatdevrelationdevice> {

	List<THkPatdevrelationdeviceVo> selectRelation(@Param("userid") Integer userid);

}
