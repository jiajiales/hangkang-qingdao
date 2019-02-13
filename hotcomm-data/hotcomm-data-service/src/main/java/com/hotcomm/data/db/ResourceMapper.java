package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.sys.Resource;
import com.hotcomm.data.bean.vo.sys.ResourcMenuVO;
import com.hotcomm.data.bean.vo.sys.ResourceVO;

import tk.mybatis.mapper.common.Mapper;

public interface ResourceMapper extends Mapper<Resource> {

	List<Resource> getResourcesByUserId(@Param("memberId") Integer memberId);

	List<ResourceVO> getResourdTree();

	List<ResourcMenuVO> getMenus();

}
