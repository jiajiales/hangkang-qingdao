package com.hotcomm.prevention.controller.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TNews;
import com.hotcomm.prevention.bean.mysql.common.params.SystemNoticeParams;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.TNewsService;
import com.hotcomm.prevention.ueditor.ActionEnter;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
public class UEditorController {
	@Autowired
	TNewsService NewsService;

	/**
	 * 百度编辑器初始化，同一处理接口
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ueditor/config")
	public void config(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增系统公告
	 * 
	 * @param news
	 * @return
	 */
	@PostMapping("/ueditor/addSystemNotice")
	public ApiResult addSystemNotice(TNews news) throws MyException {
		news.setPublishtime(ConverUtil.dateForString(new Date()));
		Integer add = NewsService.addSystemNotice(news);
		if (add <= 0) {
			return ApiResult.resultInfo("0", "添加失败", null);
		} else {
			return ApiResult.resultInfo("1", "添加成功", null);
		}
	}

	/**
	 * 修改系统公告
	 * 
	 * @param news
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/ueditor/updateSystemNotice")
	public ApiResult updateSystemNotice(TNews news) throws MyException {
		news.setAddtime(ConverUtil.dateForString(new Date()));
		Integer update = NewsService.updateSystemNotice(news);
		if (update <= 0) {
			return ApiResult.resultInfo("0", "修改失败", null);
		} else {
			return ApiResult.resultInfo("1", "修改成功", null);
		}
	}

	/**
	 * 删除公告信息
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/ueditor/delSystemNotice")
	public ApiResult delSystemNotice(Integer id) throws MyException {
		Integer del = NewsService.delSystemNotice(id);
		if (del <= 0) {
			return ApiResult.resultInfo("0", "删除失败", null);
		} else {
			return ApiResult.resultInfo("1", "删除成功", null);
		}
	}

	/**
	 * 系统公告列表
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/ueditor/selectPageNotice")
	public ApiResult selectPageNotice(SystemNoticeParams param) throws MyException {
		PageInfo<TNews> page = NewsService.selectPageByNotice(param);
		return ApiResult.resultInfo("1", "查询成功", page);
	}

	/**
	 * 查看详情
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/ueditor/selectById")
	public ApiResult selectById(Integer id) throws MyException {
		TNews news = NewsService.selectById(id);
		return ApiResult.resultInfo("1", "查询成功", news);
	}

	/**
	 * 查询最新的一条系统公告
	 * 
	 * @return
	 * @throws MyException
	 */
	@GetMapping("/ueditor/selectLastOne")
	public ApiResult selectLastOne() throws MyException {
		TNews news = NewsService.selectLastOne();
		return ApiResult.resultInfo("1", "查询成功", news);
	}
}
