package com.hot.manage.controller.Chat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.hot.manage.entity.Chat.Record.ChatRecord;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.Chat.ChatManagerService;
import com.hot.manage.utils.ApiResult;

@RestController
@RequestMapping("/Chat")
public class ChatManagerController {
	
	@Value("${image.location.path}")
	private String resourceDir;

	@Autowired
	ChatManagerService chatManagerService;
	
	/**
	 * 
	 * @param senduserid
	 * @param targetid
	 * @param txtcontent
	 * @param msgtype 0:单人聊天；1：群组
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/SendMessageByTxt")
	public ApiResult SendMessageByTxt(String senduserid,String targetid,String txtcontent,Integer msgtype)throws Exception{
		Integer result = chatManagerService.SendMessageByTxt(senduserid, targetid, txtcontent,msgtype);
		if (result!=1) {
			return ApiResult.resultInfo("0", "失败", null);
		}
		return ApiResult.resultInfo("1", "成功", null);
	}
	
	/**
	 * 
	 * @param senduserid
	 * @param targetid
	 * @param url
	 * @param msgtype 0:单人聊天；1：群组
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/SendMessageByImg")
	public ApiResult SendMessageByImg(String senduserid,String targetid,String url,Integer msgtype)throws Exception{
		 Integer result = chatManagerService.SendMessageByImg(senduserid, targetid, url,msgtype);
		if (result!=1) {
			return ApiResult.resultInfo("0", "失败", null);
		}
		return ApiResult.resultInfo("1", "成功", null);
	}
	
	/**
	 * 
	 * @param senduserid
	 * @param targetid
	 * @param time
	 * @param url
	 * @param msgtype 0:单人聊天；1：群组
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sendMessageByVideo")
	public ApiResult sendMessageByVideo(String senduserid,String targetid,long time,String url,Integer msgtype)throws Exception{
		Integer result = chatManagerService.sendMessageByVideo(senduserid, targetid, time, url,msgtype);
		if (result!=1) {
			return ApiResult.resultInfo("0", "失败", null);
		}
		return ApiResult.resultInfo("1", "成功", null);
	}
	
	@PostMapping("/upload")
	public ApiResult upload(MultipartFile file){
		String filename = file.getOriginalFilename();
		String fileSuffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		String uploadPath=resourceDir+"/chat/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/";
		// 上传文件名
		String newname = new Date().getTime() + new Random().nextInt(100) + "." + fileSuffix;
		File savefile = new File(uploadPath+newname);	
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
		return ApiResult.resultInfo("1", "上传成功", "/image/chat/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/"+newname);
	}
	
	@PostMapping("/selectChatRecords")
	public ApiResult selectChatRecords(String senderid,String targetid,Integer type,String msgtype)throws MyException{
		List<ChatRecord> chatRecords= chatManagerService.selectChatRecords(senderid, targetid, type);
		return ApiResult.resultInfo("1", "成功", chatRecords);
	}
}
