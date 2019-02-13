package com.hotcomm.prevention.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hotcomm.prevention.bean.mysql.datashow.video.Push;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;

public class VideoUtil {

	// 存正在播放视频的连接
	public static ConcurrentMap<String, ConcurrentMap<String, Object>> resultMapAll = new ConcurrentHashMap<String, ConcurrentMap<String, Object>>();

	public static PushMsg getVideo(Push push) throws IOException {
		// 遍历视频列表，如果这次点播的视频已存在则直接返回播放地址
		for (ConcurrentMap<String, Object> map : resultMapAll.values()) {
			Push p = (Push) map.get("push");
			if (p.getIp().equals(push.getIp())) {
				p.setUserid(push.getUserid());
				resultMapAll.put(push.getUserid(), map);
				System.out.println("不同用户点播同一视频，直接返回url");
				// 该视频流视频列表已存在，直接返回url
				return ((PushMsg) map.get("pushMsg"));
			}
		}
		PushMsg pushMsg = new PushMsg();
		pushMsg.setInput("rtsp://" + push.getUsername() + ":" + push.getPassword() + "@" + push.getNvrip() + ":"
				+ push.getProd() + "/h264/" + push.getNvrchannel() + "/main/av_stream");
		pushMsg.setOutput("rtmp://localhost:1935/live/" + "video" + push.getIp());
		pushMsg.setVideoname("video" + push.getIp());
		pushMsg.setFfmpeg(push.getFfmpeg());
		// 从map里面取数据，组装成命令
		String comm = getComm4Map(pushMsg);
		// 执行命令行
		final Process proc = Runtime.getRuntime().exec(comm);
		System.out.println("执行命令----start commond");
		System.out.println("摄像头：" + push.getIp() + "开启转发");
		OutHandler errorGobbler = new OutHandler(proc.getErrorStream(), "Error");
		OutHandler outputGobbler = new OutHandler(proc.getInputStream(), "Info");
		// // 启动线程打印命令行输出
		errorGobbler.start();
		outputGobbler.start();
		// 保存本次播放信息
		// 返回参数
		ConcurrentMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
		resultMap.put("outputGobbler", outputGobbler);
		resultMap.put("errorGobbler", errorGobbler);
		resultMap.put("proc", proc);
		resultMap.put("push", push);
		resultMap.put("pushMsg", pushMsg);
		// 存进静态变量
		resultMapAll.put(push.getUserid(), resultMap);
		// 放回本地ip地址的播放源
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString(); // 获取本机ip
		pushMsg.setOutput("rtmp://" + ip + ":1935/live/" + "video" + push.getIp());
		return pushMsg;
	}

	public static boolean closeVideo(String userid) {
		// 遍历视频列表，查询处理该用户以外是否还有其他用户正在查看同一个视频
		for (ConcurrentMap<String, Object> map : resultMapAll.values()) {
			// map取出的播放信息
			Push push = ((Push) map.get("push"));
			// 当前用户播放视频信息
			Push useridpush = ((Push) resultMapAll.get(userid).get("push"));
			if (push.getIp().equals(useridpush.getIp()) && !push.getUserid().equals(userid)) {
				// 还有其他用户在查看视频，所以不关闭推送流，把用户从视频列表移除
				System.out.println("还有其他用户在查看视频，所以不关闭推送流，把用户从视频列表移除");
				resultMapAll.remove(userid);
				return true;
			}
		}
		ConcurrentMap<String, Object> map = resultMapAll.get(userid);
		// 关闭打印命令行线程
		((OutHandler) map.get("outputGobbler")).destroy();
		((OutHandler) map.get("errorGobbler")).destroy();
		// 暂时先这样写，后期加日志
		System.out.println("停止命令-----end commond");
		System.out.println("摄像头：" + ((Push) resultMapAll.get(userid).get("push")).getIp() + "停止转发");
		// 关闭命令主进程
		((Process) map.get("proc")).destroy();
		// 从视频列表移除
		resultMapAll.remove(userid);
		return true;
	}

	// 组装命令
	protected static String getComm4Map(PushMsg pushMsg) {
		// -i：输入流地址或者文件绝对地址
		StringBuilder comm = new StringBuilder("ffmpeg -i ");
		// 是否有必输项：输入地址，输出地址，应用名
		if (pushMsg.getInput() != null && pushMsg.getOutput() != null && pushMsg.getVideoname() != null) {
			comm.append(pushMsg.getInput()).append(" ");
			// 一个视频源，可以有多个输出，第二个输出为拷贝源视频输出
			// comm.append(" ").append(" -f flv -s 1024*720 -an
			// ").append(pushMsg.getOutput());
			comm.append(" ").append(" " + pushMsg.getFfmpeg() + " ").append(pushMsg.getOutput());
			System.out.println(comm.toString());
			return comm.toString();
		} else {
			throw new RuntimeException("输入流地址不能为空！");
		}
	}
}
