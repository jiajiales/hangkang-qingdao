package com.hot.manage.service.item;

import java.util.List;

import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.exception.MyException;

public interface TItemPicService {

	Integer insert(TItemPic param) throws MyException;

	Integer update(TItemPic param) throws MyException;

	List<TItemPic> selectList(Integer groupid) throws MyException;

}
