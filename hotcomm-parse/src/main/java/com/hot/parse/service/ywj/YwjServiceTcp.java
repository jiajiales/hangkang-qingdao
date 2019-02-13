package com.hot.parse.service.ywj;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.ywj.Log_ywj;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class YwjServiceTcp {
	/**
	 * 
	 * @param moduleid
	 * @param data
	 */
	public void ywj_AnalysisFun(ChannelHandlerContext ctx, Integer moduleid, String datastr) throws Exception {
		// 液位计数据库日志表
		Log_ywj newModed = new Log_ywj();
		// 设备是否故障
		boolean FaultAlarm = false;
		// 判断包头
		if (datastr.substring(0, 8).equalsIgnoreCase("7E7E7E7E")) {
			String IMEI = datastr.substring(8, 38);// 33353937373130343032363838383830
			newModed.IMEI = IMEI; // 333539373731303430323638383838
			String Name = datastr.substring(40, 70);// 31313131313131313131313131313130
			newModed.Name = Name; // 313131313131313131313131313131
			String MsgType = datastr.substring(72, 74);
			newModed.MsgType = MsgType;
			String MsgLen = datastr.substring(76, 80);
			newModed.MsgLen = MsgLen;
			int MsgLenInt = Integer.parseInt(MsgLen, 16);
			String MsgBody = datastr.substring(80, (MsgLenInt * 2) + 80);
			newModed.MsgBody = MsgBody;
			newModed.DataStr = datastr;
			switch (MsgType) {
			case "01":// 注册
				ReRegister(ctx);
				break;
			case "02":// 心跳
				break;
			case "03":// 数据消息
				break;
			case "05":// AT命令
				break;
			case "06":// 点对点配置信息
				break;
			case "07":// 设置DTU数据采集间隔
				break;
			case "08":// 设置DTU每次发送的数据包数目
				break;
			case "09":// 液位设备数据
				String dataStr = MsgBody.substring(20, (MsgLenInt * 2 - 20) + 20);
				int _n = dataStr.length() % 4;
				int __n = 0;
				double totalValue = 0;
				if (_n == 0) {
					int n = dataStr.length() / 8;
					for (int i = 0; i < n; i++) {// 05 40 00 00
						String item = dataStr.substring(i * 8, 8 + (i * 8));
						String isNormal = item.substring(0, 1);
						String typeStr = item.substring(1, 2);
						String pointStr = item.substring(2, 3);
						String valueStr = item.substring(3, 8);
						StringBuilder valueStrsb = new StringBuilder(valueStr);// 构造一个StringBuilder对象
						if (isNormal == "0" && typeStr == "5") {
							// 0表示设备正常5表示液位计设备
							__n++;
							valueStr = valueStrsb.insert(5 - Integer.parseInt(pointStr), ".").toString();
							if (Double.valueOf(valueStr) != 0) {
								totalValue += Double.valueOf(valueStr);
							}
						}
						if (isNormal == "3" && typeStr == "5") {
							// 3表示设备故障5表示液位计设备
							FaultAlarm = true;
						}
					}

					newModed.LastValue = String.valueOf(totalValue / __n);
					newModed.setAddtime(ConvertHelp.getOnTime());
					// 将处理完成的数据插入数据库(日志数据)
					int addResultModel = Injection.injection.ywjMapper.insertSelective(newModed);
					if (addResultModel > 0) // 数据插入成功
					{
						// 根据mac查出设备数据
						DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, newModed.getIMEI());
						if (devMsg != null && !devMsg.equals("")) {
							// 防止根据mac地址查询出的设备不存在,null空指针错误
							DevAlarm devAlarm = new DevAlarm();
							devAlarm.setDeviceid(devMsg.getDeviceid());
							devAlarm.setMac(devMsg.getMac());
							devAlarm.setRecvtime(ConvertHelp.getOnTime());
							devAlarm.setAddtime(ConvertHelp.getOnTime());
							devAlarm.setModuleid(moduleid);
							if (FaultAlarm
									|| (devMsg.getPlusminus() > 0 && Double.valueOf(devMsg.getAlarmvalue()) > Double
											.valueOf(newModed.getLastValue()))
									|| (devMsg.getPlusminus() == 0 && Double.valueOf(devMsg.getAlarmvalue()) < Double
											.valueOf(newModed.getLastValue()))) {
								// 判断是否为报警数据
								if ((devMsg.getPlusminus() > 0 && Double.valueOf(devMsg.getAlarmvalue()) > Double
										.valueOf(newModed.getLastValue()))
										|| (devMsg.getPlusminus() == 0
												&& Double.valueOf(devMsg.getAlarmvalue()) < Double
														.valueOf(newModed.getLastValue()))) {
									// plusminus 大于 0 表示为最低水位，低于最低报警 否则高于最高报警
									if (devMsg.getPlusminus() > 0) {
										// 水位超低报警
										DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("水位超低报警",
												moduleid);
										devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
									} else if (devMsg.getPlusminus() == 0) {
										// 水位超高报警
										DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("水位超高报警",
												moduleid);
										devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
									}
									// 修改设备状态
									addResultModel = Injection.injection.devMapper.updatedev(moduleid,
											newModed.getIMEI(), 1, null, ConvertHelp.getOnTime());
								} else if (FaultAlarm) {
									// 设备故障报警
									DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("故障报警", moduleid);
									devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
									// 修改设备状态
									addResultModel = Injection.injection.devMapper.updatedev(moduleid,
											newModed.getIMEI(), 2, null, ConvertHelp.getOnTime());
								}
								// 插入设备报警表数据
								addResultModel = Injection.injection.devMapper.insertSelective(devAlarm);
								// 推送报警数据
								try {
									RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
								} catch (IOException e) {
									e.printStackTrace();
								} catch (TimeoutException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				break;
			}
		}
	}

	// 注册消息回复
	private void ReRegister(ChannelHandlerContext ctx) {
		byte[] by = new byte[6];
		Calendar nowTime = Calendar.getInstance();
		by[0] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.YEAR)).substring(2, 4));
		by[1] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MONTH) + 1));
		by[2] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.DAY_OF_MONTH)));
		by[3] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.HOUR_OF_DAY)));
		by[4] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MINUTE)));
		by[5] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.SECOND)));

		String timeStr = ConvertHelp.StringParseByte(by);
		timeStr = "2b544f505341494c" + timeStr;
		byte[] sendData = ConvertHelp.strToToHexByte(timeStr);
		// 发送消息
		ctx.writeAndFlush(Unpooled.copiedBuffer(sendData));
	}
}
