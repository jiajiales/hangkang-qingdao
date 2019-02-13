package com.hotcomm.prevention.controller.datashow.video;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.datashow.video.Push;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;
import com.hotcomm.prevention.service.datashow.video.PushService;

@RestController
@RequestMapping("/VideoController")
public class VideoController {
	@Autowired
	private PushService pushService;

	@PostMapping("/push")
	public PushMsg push(Push push) throws IOException {
		return pushService.push(push);
	}

	@PostMapping("/closevideo")
	public boolean close(String userid) throws IOException {
		return pushService.close(userid);
	}
}
