package com.hot.manage.service.item;

import java.util.List;

import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.exception.MyException;

public interface TDevItemPicService {

	Integer insert(TDevItemPic param) throws MyException;

	Integer insertBatch(List<TDevItemPic> param) throws MyException;

	Integer update(TDevItemPic param) throws MyException;

	List<TDevItemPic> selectList(Integer itemPicId) throws MyException;

	TDevItemPic selectOne(Integer id) throws MyException;

	TDevItemPic selectByExample(Integer deviceId, Integer moduleid) throws MyException;

}
