package com.hotcomm.data.service;

import java.util.List;
import java.util.Map;

import com.hotcomm.data.bean.entity.sys.Resource;
import com.hotcomm.data.bean.params.sys.ResourceParams;
import com.hotcomm.data.bean.vo.sys.ResourcMenuVO;
import com.hotcomm.data.bean.vo.sys.ResourceVO;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.framework.web.exception.HKException;

public interface ResourceService extends CommFunService<ResourceParams, ResourceVO, Integer> {

	List<ResourceVO> queryTree();

	List<Resource> queryListByMemberId(Integer memberId);
	
	Map<Object, Map<String, Boolean>> getResourcButtons(Integer memberId);
	
	List<ResourcMenuVO> getMenus() throws HKException;
	
	
}
