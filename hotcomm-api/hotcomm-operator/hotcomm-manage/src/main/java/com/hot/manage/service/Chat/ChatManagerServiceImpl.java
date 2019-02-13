package com.hot.manage.service.Chat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.hot.manage.db.Chat.TChatFriendReMapper;
import com.hot.manage.db.Chat.TChatGroupLogMapper;
import com.hot.manage.db.Chat.TChatOneLogMapper;
import com.hot.manage.db.Chat.TChatUserMapper;
import com.hot.manage.entity.Chat.TChatGroupLog;
import com.hot.manage.entity.Chat.TChatOneLog;
import com.hot.manage.entity.Chat.TChatUesr;
import com.hot.manage.entity.Chat.TChatUserVo;
import com.hot.manage.entity.Chat.Constrants.MessageConstrants;
import com.hot.manage.entity.Chat.Record.ChatRecord;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.chat.PrivateUtil;
import com.hot.manage.utils.chat.RongCloudUtil;
import io.rong.messages.ImgMessage;
import io.rong.messages.TxtMessage;
import io.rong.messages.VoiceMessage;
import io.rong.models.message.GroupMessage;
import io.rong.models.message.PrivateMessage;
import io.rong.models.response.ResponseResult;
import tk.mybatis.mapper.entity.Example;

@Service
public class ChatManagerServiceImpl implements ChatManagerService {

	@Autowired
	private TChatFriendReMapper tChatFriendRe;
	@Autowired
	private TChatUserMapper tChatUser;
	@Autowired
	private TChatOneLogMapper chatonelog;
	@Autowired
	private TChatGroupLogMapper ChatGroupLogMapper;

	@Transactional
	@Override
	public Integer DeleteChatUser(String userid) throws MyException {
		Example example = new Example(TChatUesr.class);
		example.createCriteria().andEqualTo("chatuserid", userid);
		TChatUesr record = new TChatUesr();
		record.setChatuserid(userid);
		record.setIsdelete(1);
		int update = tChatUser.updateByExampleSelective(record, example);
		if (update <= 0) {
			return 0;
		}
		return 1;
	}

	private Integer getResult(Integer result) throws MyException {
		if (result <= 0) {
			throw new MyException("0", "添加失败");
		} else {
			return result;
		}
	}

	@Transactional
	@Override
	public Integer AddChatUser(String userid, String username, String token) throws Exception {
		// TODO Auto-generated method stub
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TChatUesr record = new TChatUesr();
		record.setAddtime(sdf.format(d));
		record.setChatuserid(userid);
		record.setUsername(username);
		record.setChattoken(token);
		tChatUser.insertSelective(record);
		return getResult(record.getId());
	}

	@Transactional
	@Override
	public Integer UpdateChatUser(String userid, String username) throws Exception {
		// TODO Auto-generated method stub
		Example example = new Example(TChatUesr.class);
		example.createCriteria().andEqualTo("chatuserid", userid);
		TChatUesr record = new TChatUesr();
		record.setChatuserid(userid);
		record.setUsername(username);
		int i = tChatUser.updateByExampleSelective(record, example);
		return getResult(i);
	}

	@Override
	public List<TChatUserVo> selectChatFriendRe(String chatuserid) throws MyException {
		// TODO Auto-generated method stub
		List<TChatUserVo> selectFriendsList = tChatFriendRe.selectFriendsList(chatuserid);
		return selectFriendsList;
	}

	@Transactional
	@Override
	public Integer SendMessageByTxt(String senduserid, String targetid, String txtcontent, Integer msgtype)
			throws Exception {
		TxtMessage txtMessage = new TxtMessage(txtcontent, null);
		String targetId[] = { targetid };
		if (msgtype == 0) {
			// 单人聊天
			TChatOneLog tChatOneLog = new TChatOneLog();
			tChatOneLog.setSenderid(senduserid);
			tChatOneLog.setTargetid(targetid);
			tChatOneLog.setTxtcontent(txtcontent);
			tChatOneLog.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			tChatOneLog.setType(MessageConstrants.TEXT);
			int insertSelective = chatonelog.insertSelective(tChatOneLog);
			if (insertSelective <= 0) {
				throw new MyException("0", "发送失败");
			}
			PrivateMessage privateMessage = new PrivateMessage();
			privateMessage.setSenderId(senduserid);
			privateMessage.setTargetId(targetId);
			privateMessage.setObjectName(MessageConstrants.TEXT_TYPE);
			privateMessage.setContent(txtMessage);
			PrivateUtil privateUtil = new PrivateUtil(RongCloudUtil.appKey, RongCloudUtil.appSecret);
			ResponseResult result = privateUtil.send(privateMessage);
			if (result.getCode() != 200) {
				throw new MyException("0", result.getMsg());
			}
		} else if (msgtype == 1) {
			// 群组聊天
			TChatGroupLog ChatGroupLog = new TChatGroupLog();
			ChatGroupLog.setSenderid(senduserid);
			ChatGroupLog.setGroupid(targetid);
			ChatGroupLog.setType(MessageConstrants.TEXT);
			ChatGroupLog.setTextcontent(txtcontent);
			ChatGroupLog.setAddtime(new Date());
			int insertSelective = ChatGroupLogMapper.insertSelective(ChatGroupLog);
			if (insertSelective <= 0) {
				throw new MyException("0", "发送失败");
			}
			GroupMessage model = new GroupMessage().setSenderId(senduserid).setTargetId(targetId)
					.setObjectName(MessageConstrants.TEXT_TYPE).setContent(txtMessage);
			ResponseResult result = RongCloudUtil.getRongCloud().message.group.send(model);
			if (result.code != 200) {
				throw new MyException("0", result.getMsg());
			}
		}
		return 1;
	}

	@Override
	public Integer SendMessageByImg(String senduserid, String targetid, String url, Integer msgtype) throws Exception {
		String baseurl=getBase64(url);
		String targetId[] = { targetid };
		ImgMessage imgmessage = new ImgMessage(baseurl, null, url);
		TChatOneLog tChatOneLog = new TChatOneLog();
		tChatOneLog.setSenderid(senduserid);
		tChatOneLog.setTargetid(targetid);
		tChatOneLog.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		tChatOneLog.setType(2);
		tChatOneLog.setResoureurl(url);
		int insertSelective = chatonelog.insertSelective(tChatOneLog);
		if (insertSelective <= 0) {
			throw new MyException("0", "发送失败");
		}
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setSenderId(senduserid);
		privateMessage.setTargetId(targetId);
		privateMessage.setObjectName(MessageConstrants.IMAGE_TYPE);
		privateMessage.setContent(imgmessage);
		PrivateUtil privateUtil = new PrivateUtil(RongCloudUtil.appKey, RongCloudUtil.appSecret);
		ResponseResult result = privateUtil.send(privateMessage);
		if (result.getCode() != 200) {
			throw new MyException("0", result.getMsg());
		}
		return 1;
	}

	@Override
	public Integer sendMessageByVideo(String senduserid, String targetid, long time, String url, Integer msgtype)
			throws Exception {
		// TODO Auto-generated method stub
		String baseurl=getBase64(url);
		VoiceMessage voiceMessage = new VoiceMessage(baseurl, null,time);
		String targetId[] = { targetid };
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setSenderId(senduserid);
		privateMessage.setTargetId(targetId);
		privateMessage.setObjectName(MessageConstrants.VOICE_TYPE);
		privateMessage.setContent(voiceMessage);
		PrivateUtil privateUtil = new PrivateUtil(RongCloudUtil.appKey, RongCloudUtil.appSecret);
		ResponseResult result = privateUtil.send(privateMessage);
		if (result.getCode() != 200) {
			throw new MyException("0", result.getMsg());
		}
		TChatOneLog tChatOneLog = new TChatOneLog();
		tChatOneLog.setSenderid(senduserid);
		tChatOneLog.setTargetid(targetid);
		tChatOneLog.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		tChatOneLog.setType(3);
		tChatOneLog.setDuration(time);
		tChatOneLog.setResoureurl(url);
		int insertSelective = chatonelog.insertSelective(tChatOneLog);
		if (insertSelective <= 0) {
			throw new MyException("0", "发送失败");
		}
		return 1;
	}

	@Override
	public List<ChatRecord> selectChatRecords(String senderid, String targetid, Integer type) throws MyException {
		// TODO Auto-generated method stub
		List<ChatRecord> selectChatRecord = chatonelog.selectChatRecord(senderid, targetid, type);
		if (selectChatRecord.isEmpty()) {
			return null;
		}
		return selectChatRecord;
	}

	private String getBase64(String url) {
		url = "/hot-manage" + url;
		File file = new File(url);
		InputStream input = null;
		BufferedInputStream bufferedInputStream = null;
		byte[] b = null;
		try {
			input = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(input);
			b = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedInputStream.close();
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String q = Base64Utils.encodeToString(b);
		return q;

	}
}
