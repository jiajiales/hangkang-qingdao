package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.service.Data;
import com.hotcomm.data.bean.entity.service.DataPushReady;
import com.hotcomm.data.bean.params.service.data.DataPageParams;
import com.hotcomm.data.bean.params.service.data.DataUpdatePrams;
import com.hotcomm.data.bean.vo.data.DataVO;

import tk.mybatis.mapper.common.Mapper;

public interface DataMapper extends Mapper<Data> {

	String getData(@Param("dataId") Long dataId);

	void updateDataStatus(@Param("data") DataUpdatePrams params);

	List<DataVO> queryPage(@Param("params") DataPageParams params);

	Long queryPageCount(@Param("params") DataPageParams params);

	Long getDevUnsentQueueNum(@Param("devId") Integer devId);

	List<DataPushReady> queryLoadReadyMsg(String deviceCode);

}
