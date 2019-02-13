package com.hot.manage.db.common.patrol;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.patrol.ShakeSignParam;
import com.hot.manage.entity.common.patrol.ShakeSignVo;
import com.hot.manage.entity.common.patrol.SignLogPageInfoParam;
import com.hot.manage.entity.common.patrol.SignLogPageInfoVO;
import com.hot.manage.entity.common.patrol.SignPlaceOnid;
import com.hot.manage.entity.common.patrol.checkSignDevnum;
import com.hot.manage.entity.common.patrol.newSignPlaceParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoVO;
import com.hot.manage.entity.common.patrol.bean.THkSign;

import tk.mybatis.mapper.common.Mapper;

public interface THkSignMapper extends Mapper<THkSign>{
	
	//摇一摇签到列表
	Page<ShakeSignVo> selectPageinfo(ShakeSignParam param);
	
	//签到地点列表分页查询
	Page<signPlacePageInfoVO> selectSignPlacePageInfo(signPlacePageInfoParam param);
	
	Integer addSignAddress(newSignPlaceParam param);
	
	checkSignDevnum checkSignPlaceDevnum(@Param("devnum") String devnum);
	
	Integer patUserRelationSign(@Param("patUser") Integer patUser,@Param("signId") Integer signId);
	
	Integer cutRelation(@Param("signId") Integer signId);
	
	Integer clearSignLog(@Param("signId") Integer signId);
	
	Integer deleteSignPlace(@Param("signId") Integer signId);
	
	Integer updateSignPlace(newSignPlaceParam param);
	
	SignPlaceOnid selectSignPlaceOnid(@Param("signId") Integer signId);
	
	Page<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param);
	
	List<SignLogPageInfoVO> selectSignLogInfo(SignLogPageInfoParam param);
}
