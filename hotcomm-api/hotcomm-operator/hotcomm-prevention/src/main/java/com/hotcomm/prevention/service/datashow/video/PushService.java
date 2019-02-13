package com.hotcomm.prevention.service.datashow.video;

import java.io.IOException;

import com.hotcomm.prevention.bean.mysql.datashow.video.Push;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;

public interface PushService {
	public PushMsg push(Push push) throws IOException;

	public boolean close(String userid) throws IOException;
}
