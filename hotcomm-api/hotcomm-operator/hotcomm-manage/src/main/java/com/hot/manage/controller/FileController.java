package com.hot.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hot.manage.entity.common.THKApp;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.FileuploadService;
import com.hot.manage.service.common.appmap.AppMapService;
import com.hot.manage.utils.ApiResult;

@RestController
@CrossOrigin
public class FileController {

	@Autowired
	private FileuploadService fileuploadService;

	@Autowired
	AppMapService appMapService;

	// 单个文件上传
	@PostMapping("/oneFileUpload")
	public ApiResult oneFileUpload(MultipartFile file) throws MyException {
		if (!file.isEmpty()) {
			String oneFileUpload = fileuploadService.oneFileUpload(file);
			return ApiResult.resultInfo("1", "成功", oneFileUpload);
		} else {
			return ApiResult.resultInfo("1", "成功", "文件不能为空");
		}
	}

	// 对个文件上传
	@PostMapping("/moreFileUpload")
	public ApiResult bacthFileUpload(MultipartFile[] file) throws MyException {
		if (file.length != 0) {
			return ApiResult.resultInfo("1", "成功", fileuploadService.bacthFileUpload(file));
		} else {
			return ApiResult.resultInfo("1", "成功", "文件不能为空");
		}
	}
	
	/**
	 * 获取最新版本app
	 * @param version
	 * @return
	 * @throws MyException
	 */
	@GetMapping("/getApkUrl")
	public ApiResult getApkUrl(Integer version) throws MyException {
		THKApp app = appMapService.queryApkUrl(version);
		if (app == null) {
			return ApiResult.resultInfo("1", null, null);
		} else {
			return ApiResult.resultInfo("1", "有更新版本", app);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	@PostMapping("/delFile")
	public ApiResult delFile(String path) {
		return fileuploadService.delFile(path);
	}

}
