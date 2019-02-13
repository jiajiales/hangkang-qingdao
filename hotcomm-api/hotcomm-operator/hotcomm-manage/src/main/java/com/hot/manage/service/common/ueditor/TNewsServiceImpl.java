package com.hot.manage.service.common.ueditor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.common.ueditor.TNewsMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.ueditor.SystemNoticeParams;
import com.hot.manage.entity.common.ueditor.TNews;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;
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
