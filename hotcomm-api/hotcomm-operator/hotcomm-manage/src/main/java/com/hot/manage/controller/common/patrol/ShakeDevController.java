package com.hot.manage.controller.common.patrol;

import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeSignParam;
import com.hot.manage.entity.common.patrol.ShakeSignVo;
import com.hot.manage.entity.common.patrol.SignLogInfoVO;
import com.hot.manage.entity.common.patrol.SignLogPageInfoParam;
import com.hot.manage.entity.common.patrol.SignLogPageInfoVO;
import com.hot.manage.entity.common.patrol.THKSignParam;
import com.hot.manage.entity.common.patrol.newSignPlaceParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoVO;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.patrol.THkSignService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ExportExcelUtils;

@RestController
public class ShakeDevController {
	@Autowired
	private THkSignService HkSignService;
	
	/**
	 * 摇一摇签到设备分页，及各种条件查询
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectPageinfo")
	@Permissions("patrol:selectPageinfo:query")
	public ApiResult selectPageinfo(ShakeSignParam param)throws MyException{
		PageInfo<ShakeSignVo> pageinfo = HkSignService.selectPageinfo(param);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
	
	@PostMapping("/patrol/insertShakeDev")
	@Permissions("patrol:insertShakeDev:add")
	public ApiResult insertShakeDev(THKSignParam param)throws MyException{
		Integer in = HkSignService.insertDev(param);
		if (in<=0) {
			return ApiResult.resultInfo("0", "添加失败", null);
		}else {
			return ApiResult.resultInfo("1", "添加成功", null);
		}
	}
	
	@PostMapping("/patrol/updateShakeDev")
	@Permissions("patrol:updateShakeDev:update")
	public ApiResult updateShakeDev(THKSignParam param)throws MyException{
		Integer up = HkSignService.update(param);
		if (up<=0) {
			return ApiResult.resultInfo("0", "修改失败", null);
		}else {
			return ApiResult.resultInfo("1", "修改成功", null);
		}
	}
	
	/**
	 * 摇一摇设备删除
	 * @param id 摇一摇设备表ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/deleteShakeDev")
	@Permissions("patrol:deleteShakeDev:del")
	public ApiResult deleteShakeDev(Integer id)throws MyException{
		Integer del = HkSignService.delete(id);
		if (del<=0) {
			return ApiResult.resultInfo("0", "删除失败", null);
		}else {
			return ApiResult.resultInfo("1", "删除成功", null);
		}
	}
	
	@PostMapping("/sign/selectSignPlacePageInfo")
	@Permissions("sign:SignPlacePageInfo:query")
	public ApiResult selectSignPlacePageInfo(signPlacePageInfoParam param)throws MyException{
		PageInfo<signPlacePageInfoVO> pageinfo = HkSignService.selectSignPlacePageInfo(param);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
	
	@PostMapping("/sign/addSignAddress")
	@Permissions("sign:SignAddress:add")
	public ApiResult addSignAddress(newSignPlaceParam param)throws MyException{
		Integer in = HkSignService.addSignAddress(param);
		if (in<=0) {
			return ApiResult.resultInfo("0", "添加失败", null);
		}else {
			if (in==201) {
				return ApiResult.resultInfo("0", "添加失败，签到地点编号已存在", null);
			}else {
				return ApiResult.resultInfo("1", "添加成功", null);
			}
		}
	}
	
	@PostMapping("/sign/patUserRelationSign")
	@Permissions("sign:patUserRelationSign:add")
	public ApiResult patUserRelationSign(String patUser,String signId)throws MyException{
			return ApiResult.resultInfo("1", "分配成功", HkSignService.patUserRelationSign(patUser, signId));
	}
	
	@PostMapping("/sign/deleteSignPlace")
	@Permissions("sign:deleteSignPlace:del")
	public ApiResult deleteSignPlace(Integer signId)throws MyException{
			return ApiResult.resultInfo("1", "删除成功", HkSignService.deleteSignPlace(signId));
	}
	
	@PostMapping("/sign/updateSignPlace")
	@Permissions("sign:SignPlace:update")
	public ApiResult updateSignPlace(newSignPlaceParam param)throws MyException{
			int in = HkSignService.updateSignPlace(param);
			if (in==201) {
				return ApiResult.resultInfo("0", "修改失败，签到点编号已存在", null);
			}
			return ApiResult.resultInfo("1", "修改成功", HkSignService.updateSignPlace(param));
	}
	
	@PostMapping("/sign/selectSignPlaceOnid")
	@Permissions("sign:signPlaceOnid:select")
	public ApiResult selectSignPlaceOnid(Integer signId)throws MyException{
			return ApiResult.resultInfo("1", "查询成功", HkSignService.selectSignPlaceOnid(signId));
	}
	
	@PostMapping("/sign/selectSignLogPageInfo")
	@Permissions("sign:signLogPageInfo:query")
	public ApiResult selectSignLogPageInfo(SignLogPageInfoParam param)throws MyException{
		PageInfo<SignLogPageInfoVO> pageinfo = HkSignService.selectSignLogPageInfo(param);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
	
	@RequestMapping(value = "/student/exam-submit")
	public void exportSignLogFile(HttpServletResponse response, HttpServletRequest request, Integer devGroupId) throws Exception {
		SignLogInfoVO vo=new SignLogInfoVO();
		String fileName = "签到日志.xlsx";

		try {
			String agent = (String) request.getHeader("USER-AGENT");

			if (agent != null && agent.indexOf("Firefox") != -1)
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			else
				fileName = URLEncoder.encode(fileName, "UTF-8");

			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("content-Type", "application/vnd.ms-excel");
			ExportExcelUtils.exportExcel(vo, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
