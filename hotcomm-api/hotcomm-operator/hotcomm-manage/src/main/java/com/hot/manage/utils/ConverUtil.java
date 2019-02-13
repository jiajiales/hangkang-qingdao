package com.hot.manage.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.github.pagehelper.Page;

public class ConverUtil {
	
	public static <V, B> List<V> converPage(Page<B> page,Class<V> v) {
		List<V> vlist = new ArrayList<>();
		if(page==null||page.size()<=0) 
			return vlist;
		try {
			for(B b:page) {
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
	
	public static String timeForString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);	
	}
}
