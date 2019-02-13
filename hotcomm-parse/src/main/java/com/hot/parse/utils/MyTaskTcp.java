package com.hot.parse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hot.parse.service.xiaoan.XiaoAnServiceTcp;
import com.hot.parse.service.ywj.YwjServiceTcp;

import io.netty.channel.ChannelHandlerContext;

//线程池任务类tcp
public class MyTaskTcp implements Runnable {

	private YwjServiceTcp ywjServiceTcp;

	private XiaoAnServiceTcp xiaoAnServiceTcp;

	private ChannelHandlerContext ctx;

	private Logger logger;

	private String datastr;

	public MyTaskTcp(ChannelHandlerContext ctx, String datastr) {
		this.ywjServiceTcp = new YwjServiceTcp();
		this.xiaoAnServiceTcp = new XiaoAnServiceTcp();
		this.ctx = ctx;
		this.datastr = datastr;
		this.logger = LoggerFactory.getLogger(MyTaskTcp.class);
	}

	@Override
	public void run() {
		try {
			if (datastr.length() >= 8) {// 防止数据长度小于8报错
				// 判断模块数据
				if (datastr.substring(0, 8).equalsIgnoreCase("7E7E7E7E")) {
					// 液位计
					ywjServiceTcp.ywj_AnalysisFun(ctx, 13, datastr);
				} else {
					// 三相电压，三相电流，剩余电压，水压
//					xiaoAnServiceTcp.xiaoan_AnalysisTcp(ctx, null, datastr);
				}
			}
		} catch (Exception e) {
			/*
			 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			 * e.printStackTrace(new PrintWriter(outputStream));
			 */
			try {
				// 如果报错也要回复
//				xiaoAnServiceTcp.ReRegister(ctx, "0010", datastr.substring(12, 20));
			} catch (Exception e1) {
				logger.error("error:{}", e1);
				e1.printStackTrace();
				return;
			}
			logger.error("error:{}", e);
			e.printStackTrace();
			return;
		}
	}
}
