package com.hotcomm.prevention.db.mysql.common;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.entity.TNews;
import com.hotcomm.prevention.bean.mysql.common.params.SystemNoticeParams;

import tk.mybatis.mapper.common.Mapper;

public interface TNewsMapper extends Mapper<TNews> {
	
	Page<TNews> queryPageNotices(SystemNoticeParams param);
	
	TNews selectLastOne();

}
