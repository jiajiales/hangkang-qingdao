package com.hot.manage.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ApiResult;

@Service
public class FileuploadService {

	@Value("${image.location.path}")
	private String resourceDir;
	
	@Value("${apk.location.path}")
	private String apkResourceDir;

	public String oneFileUpload(MultipartFile file) throws MyException {
		// 获取上传文件路径
		String uploadPath = file.getOriginalFilename();
		// 获取上传文件的后缀
		String fileSuffix = uploadPath.substring(uploadPath.lastIndexOf(".") + 1, uploadPath.length());
		if (fileSuffix.equals("apk")) {
			uploadPath = apkResourceDir;
		} else {
			// 上传目录地址
			// String uploadpath="E:/hot-manage/image/";//windows路径
			uploadPath = resourceDir;// liux路劲
		}
		// 上传文件名
		String fileName = new Date().getTime() + new Random().nextInt(100) + "." + fileSuffix;
		File savefile = new File(uploadPath + fileName);
		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
		}
		try {
			file.transferTo(savefile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (fileSuffix.equals("apk")) {
			return "/apk/"+fileName;
		} else {
			return "/image/" + fileName;
		}
	}

	// 批量上传
	public String bacthFileUpload(MultipartFile[] file) throws MyException {
		StringBuffer buffer = new StringBuffer();
		for (MultipartFile multipartFile : file) {
			String str = oneFileUpload(multipartFile);
			buffer.append(str);
			buffer.append(",");
		}
		String all = buffer.substring(0, buffer.length() - 1);
		return all;
	}

	public ApiResult delFile(String path) {
		ApiResult resultInfo = null;
		int lastIndexOf = path.lastIndexOf("/");
		String sb = path.substring(lastIndexOf+1, path.length());
		sb = resourceDir + sb;
		File file = new File(sb);
		if (file.exists()) {
			if (file.delete()) {
				resultInfo = ApiResult.resultInfo("1", "删除成功", null);
			} else {
				resultInfo = ApiResult.resultInfo("0", "删除失败", null);
			}
		} else {
			resultInfo = ApiResult.resultInfo("0", "文件不存在！", null);
		}

		return resultInfo;
	}

}
