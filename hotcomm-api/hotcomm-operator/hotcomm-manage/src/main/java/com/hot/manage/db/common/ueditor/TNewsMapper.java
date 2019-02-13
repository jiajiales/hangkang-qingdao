package com.hot.manage.db.common.ueditor;
import com.github.pagehelper.Page;
import com.hot.manage.entity.common.ueditor.SystemNoticeParams;
import com.hot.manage.entity.common.ueditor.TNews;

import tk.mybatis.mapper.common.Mapper;

public interface TNewsMapper extends Mapper<TNews> {
	
	Page<TNews> queryPageNotices(SystemNoticeParams param);
	
	TNews selectLastOne();

}
