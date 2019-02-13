package com.hotcomm.data.service.common;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.github.pagehelper.Page;

public class AbstractFunServiceImpl<V, B> {

	protected List<V> converPage(Page<B> page, Class<V> v) {
		List<V> vlist = new Page<>();
		if (page == null || page.size() <= 0)
			return vlist;
		try {
			for (B b : page) {
				V newInstance = v.newInstance();
				BeanUtils.copyProperties(b, newInstance);
				vlist.add(newInstance);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return vlist;
	}

}
