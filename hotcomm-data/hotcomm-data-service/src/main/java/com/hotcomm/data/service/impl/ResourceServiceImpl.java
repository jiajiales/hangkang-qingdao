package com.hotcomm.data.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.sys.Resource;
import com.hotcomm.data.bean.enums.ResourceEnum.ResourceStatus;
import com.hotcomm.data.bean.enums.ResourceEnum.ResourceType;
import com.hotcomm.data.bean.params.sys.ResourceParams;
import com.hotcomm.data.bean.vo.sys.ResourcMenuVO;
import com.hotcomm.data.bean.vo.sys.ResourceVO;
import com.hotcomm.data.db.ResourceMapper;
import com.hotcomm.data.service.ResourceService;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceMapper resourceMapper;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;

	@Override
	@Transactional
	public Integer addBean(ResourceParams params) throws HKException {
		Integer id = 0;
		Resource resource = new Resource();
		Integer pid = params.getPid();
		if(pid==null) {
			pid = 0;
		}
		params.setPid(pid);
		BeanUtils.copyProperties(params, resource);
		resourceMapper.insertSelective(resource);
		id = resource.getId();
		return id;
	}

	@Override
	@Transactional
	public void delBean(Integer id) throws HKException {
		resourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void updateBean(ResourceParams params) throws HKException {
		Resource resource = new Resource();
//		String key = params.getKey();
//		String validatePath = params.getPath();
//		String truePath = SystemCache.FUN_URLS.get(key);

//		if (!validatePath.equals(truePath)) {
//			throw manager.create("ROLE000001");
//		}

		BeanUtils.copyProperties(params, resource);

		if (resource.getStatus() == null) {
			resource.setStatus(ResourceStatus.DISABLE.getValue());
		}

		resourceMapper.updateByPrimaryKeySelective(resource);
	}

	@Override
	public ResourceVO getBean(Integer id) throws HKException {
		Resource resource = resourceMapper.selectByPrimaryKey(id);
		ResourceVO vo = new ResourceVO();
		BeanUtils.copyProperties(resource, vo);
		return vo;
	}

	@Override
	public List<ResourceVO> queryTree() {
		return resourceMapper.getResourdTree();
	}

	@Override
	public List<Resource> queryListByMemberId(Integer memberId) {
		return resourceMapper.getResourcesByUserId(memberId);
	}

	@Override
	public List<ResourcMenuVO> getMenus() throws HKException {
		return resourceMapper.getMenus();
	}

	@Override
	public Map<Object, Map<String, Boolean>> getResourcButtons(Integer memberId) {
		Map<Object, Map<String, Boolean>> result = new HashMap<>();
		List<Resource> memberResource = this.queryListByMemberId(memberId);
		List<ResourceVO>  allResource = this.queryTree();
		for(ResourceVO resource:allResource) {
			ResourceType type = ResourceType.getByValue(resource.getType());
			if(type==ResourceType.API_BUTON||type==ResourceType.BUTTON) {
				String key = resource.getKey();
				Integer pid = resource.getPid();
				Map<String, Boolean> v = result.get(pid);
				if(v==null) 
					v = new HashMap<String, Boolean>();
				v.put(key, exists(memberResource, key));
				result.put(pid, v);
			}
		}
		for(ResourceVO resource:allResource) {
			ResourceType type = ResourceType.getByValue(resource.getType());
			if(type==ResourceType.MENU) {
				Map<String, Boolean> buttons = result.get(resource.getId());
				if(buttons!=null) {
					result.put(resource.getKey(), buttons);
					result.remove(resource.getId());
				}
			}
		}
		return result;
	}
	
	private boolean exists(List<Resource> resources,String key) {
		for(Resource rs:resources) {
			if(rs.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
	
}
