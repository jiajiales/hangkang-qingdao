package com.hot.manage.service.common.ueditor;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.ueditor.SystemNoticeParams;
import com.hot.manage.entity.common.ueditor.TNews;
import com.hot.manage.exception.MyException;

public interface TNewsService {
	
	Integer addSystemNotice(TNews news)throws MyException;
	
	Integer updateSystemNotice(TNews news)throws MyException;
	
	Integer delSystemNotice(Integer id)throws MyException;
	
	PageInfo<TNews> selectPageByNotice(SystemNoticeParams param)throws MyException;
	
	TNews selectLastOne()throws MyException;
	
	TNews selectById(Integer id)throws MyException;
}
