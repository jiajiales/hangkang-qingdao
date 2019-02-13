package com.hotcomm.prevention.service.datashow.video;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.video.Push;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;
import com.hotcomm.prevention.utils.VideoUtil;

@Service
public class PushServiceImpl implements PushService {

	// 开启转发
	public PushMsg push(Push push) throws IOException {
		if (VideoUtil.resultMapAll.containsKey(push.getUserid())
				&& ((Push) VideoUtil.resultMapAll.get(push.getUserid()).get("push")).getIp().equals(push.getIp())) {
			// 判断是否同一个用户重复点播同一个视频
			System.out.println("同一用户重复点播");
			return ((PushMsg) VideoUtil.resultMapAll.get(push.getUserid()).get("pushMsg"));
		} else if (VideoUtil.resultMapAll.containsKey(push.getUserid())) {
			// 该用户以存在点播视频，先关闭之前视频流
			System.out.println("该用户以存在点播视频，先关闭之前视频流");
			close(push.getUserid());
		}
		return VideoUtil.getVideo(push);
	}

	// 关闭视频进程，根据userid关闭
	@Override
	public boolean close(String userid) throws IOException {
		if (!VideoUtil.resultMapAll.containsKey(userid)) {
			// 该视频不在播放列表
			return false;
		}
		return VideoUtil.closeVideo(userid);
	}

}
