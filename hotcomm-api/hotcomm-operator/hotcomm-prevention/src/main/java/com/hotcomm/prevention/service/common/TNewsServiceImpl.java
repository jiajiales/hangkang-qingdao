package com.hotcomm.prevention.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.common.entity.TNews;
import com.hotcomm.prevention.bean.mysql.common.params.SystemNoticeParams;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.db.mysql.common.TNewsMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;
@Service
public class TNewsServiceImpl implements TNewsService {
	
	@Autowired
	TNewsMapper NewsMapper;
	
	@Transactional
	@Override
	public Integer addSystemNotice(TNews news) throws MyException {
		return NewsMapper.insertSelective(news);
	}
	
	@Transactional
	@Override
	public Integer updateSystemNotice(TNews news) throws MyException {
		return NewsMapper.updateByPrimaryKeySelective(news);
	}
	
	@Transactional
	@Override
	public Integer delSystemNotice(Integer id) throws MyException {
		return NewsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public PageInfo<TNews> selectPageByNotice(SystemNoticeParams param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TNews> page = NewsMapper.queryPageNotices(param);
		List<TNews> rows = ConverUtil.converPage(page, TNews.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}
	
	/**
	 * 查最后一条，轮询
	 */
	@Override
	public TNews selectLastOne() throws MyException {
		return NewsMapper.selectLastOne();
	}
	
	/**
	 * 查看详情
	 */
	@Override
	public TNews selectById(Integer id) throws MyException {
		return NewsMapper.selectByPrimaryKey(id);
	}
}
