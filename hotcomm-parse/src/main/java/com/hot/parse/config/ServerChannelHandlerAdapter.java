package com.hot.parse.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.hot.parse.utils.MyTaskTcp;
import com.hot.parse.utils.ThreadPool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Configuration
@Component
@Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {

	private Logger logger;

	private ThreadPool handerdc;

	private static String remnantdata;

	// @Value("${SOCKET.LISTENER.PORT}")
	private int PORT;

	// tcp处理线程数
	/*
	 * @Value("${spring.tcp.worker_num}") private int worker_num;
	 */

	public ServerChannelHandlerAdapter() {
		// 构造方法
		this.logger = LoggerFactory.getLogger(ServerChannelHandlerAdapter.class);
		this.handerdc = ThreadPool.getThreadPool(2);
	}

	// 捕捉异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	// 监听接收消息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		String datastr = "";
		String a = "";
		for (int i = 0; i < buf.readableBytes(); i++) {
			// Integer.toHexString16进制转字符串方法
			a = Integer.toHexString(buf.getByte(i));
			if (a.length() < 2) {
				// 判断是否缺少位数
				a = "0" + a;
			}
			// 拼接原始十六进制数据
			datastr = datastr + a;
		}
		// 清除ffffff废数据
		datastr = datastr.replace("ffffff", "");
		System.out.println(datastr);
		List<Integer> posion = new ArrayList<>();
		// 查找包头0202/020202分割数据，解决tcp粘包问题
		if (datastr.indexOf("020202") == -1) {
			// 不存在020202,表示不是注册，心跳
			for (int i = -1; i <= datastr.lastIndexOf("0202"); ++i) {
				i = datastr.indexOf("0202", i);
				posion.add(i);
			}
		} else {
			// 存在020202,表示是注册，心跳
			for (int i = -1; i <= datastr.lastIndexOf("020202"); ++i) {
				i = datastr.indexOf("020202", i);
				posion.add(i);
			}
		}
		for (int i = 0; i < posion.size(); i++) {
			if ((posion.size() - 1) == i) {
				// 最后一条
				System.out.println("接收到的数据：" + datastr.substring(posion.get(i), datastr.length()));
				if (posion.size() != 1 && datastr.substring(posion.get(i), datastr.length()).length() != datastr
						.substring(posion.get(i - 1), posion.get(i)).length()) {
					// 最后一条数据长度是否等于前一条数据长度，不等于：非完整数据
					remnantdata = datastr.substring(posion.get(i), datastr.length());
					System.out.println("非完整数据:" + remnantdata);
				} else {
					// 完整数据
					// 把任务分发给线程池处理
					logger.info("监听端口{}来源消息{}", PORT, datastr.substring(posion.get(i), datastr.length()));
					handerdc.execute(
							new Runnable[] { new MyTaskTcp(ctx, datastr.substring(posion.get(i), datastr.length())) });
					logger.info("线程信息：{}", handerdc);
				}
			} else {
				System.out.println("接收到的数据：" + datastr.substring(posion.get(i), posion.get(i + 1)));
				if (i == 0 && posion.get(0) != 0) {
					// 残余上次的数据
					remnantdata = remnantdata + datastr.substring(0, posion.get(0));
					System.out.println("残余上次的数据:" + datastr.substring(0, posion.get(0)));
					// 把任务分发给线程池处理
					logger.info("监听端口{}来源消息{}", PORT, remnantdata);
					handerdc.execute(new Runnable[] { new MyTaskTcp(ctx, remnantdata) });
					logger.info("线程信息：{}", handerdc);
					remnantdata = "";
				} else {
					remnantdata = datastr.substring(posion.get(i), posion.get(i + 1));
					// 把任务分发给线程池处理
					logger.info("监听端口{}来源消息{}", PORT, remnantdata);
					handerdc.execute(new Runnable[] { new MyTaskTcp(ctx, remnantdata) });
					logger.info("线程信息：{}", handerdc);
				}
			}
		}
		// 释放资源
		buf.release();
	}
}
