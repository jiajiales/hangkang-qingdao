package com.hot.parse.service.krq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.krq.Log_krq;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class KrqService {

	public void krq_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] krq_byte = ConvertHelp.strToToHexByte(model.data);
		// 可燃气日志数据库表
		Log_krq logkrq = new Log_krq();
		// 判断包头; B1报警信息，B2正常信息
		byte[] a0 = new byte[1];
		System.arraycopy(krq_byte, 0, a0, 0, 1);
		String FrameTypeStr = ConvertHelp.StringParseByte(a0);
		if (FrameTypeStr.equalsIgnoreCase("b1") || FrameTypeStr.equalsIgnoreCase("b2")) {
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			logkrq.state = false;
			// 火灾报警状态
			int state1 = Integer.parseInt(String.format("%02x", new Integer(krq_byte[1] & 0xff)), 16);
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(state1, n);
				switch (n) {
				case 3:// 火灾报警(Fire Alarm)指示， 为 1 有效
						// string FRA = i > 0 ? "true" : "false";
					logkrq.fraflag = i > 0 ? true : false;
					logkrq.state = true;
					break;
				}
			}
			// 无线底座状态
			int state2 = Integer.parseInt(String.format("%02x", new Integer(krq_byte[2] & 0xff)), 16);
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(state2, n);
				switch (n) {
				case 0:// 无线底座其他故障报警
					logkrq.mofaflag = String.valueOf(i) == "1" ? true : false;
					break;
				case 1:// 无线底座低电压报警
					logkrq.lbflag = String.valueOf(i) == "1" ? true : false;
					break;
				case 4:// 无线底座防拆报警
					logkrq.soaflag = i > 0 ? true : false;
					break;
				}
			}
			// 写入可燃气日志数据表参数
			logkrq.voltage = logkrq.lbflag ? "1.2" : "3.6";
			logkrq.voltagestate = logkrq.lbflag ? "1" : "0";
			logkrq.setKeepalive(true);
			logkrq.setQueueid(model.getId());
			logkrq.setTemperature("0");
			logkrq.setAddtime(ConvertHelp.getOnTime());
			logkrq.setCommsysType(model.getExtra().getCommsysType());
			logkrq.setRssi(model.getExtra().getRssi());
			logkrq.setSnr(model.getExtra().getSnr());
			logkrq.setFrameCnt(model.getExtra().getFrameCnt());
			logkrq.setGwid(model.getExtra().getGwid());
			logkrq.setGwip(model.getExtra().getGwip());
			logkrq.setChannel(model.getExtra().getChannel());
			logkrq.setSf(model.getExtra().getSf());
			logkrq.setPub(model.getPub());
			logkrq.setMacAddr(model.getMacAddr());
			logkrq.setData(model.getData());
			logkrq.setCalibration(true);
			logkrq.setFport(0);
			logkrq.setGsensor("");
			logkrq.setLastvalue("");
			logkrq.setRecv(ConvertHelp.getOnTime());
			// 入库日志表
			Injection.injection.krqMapper.insertSelective(logkrq);
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, logkrq.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				if (logkrq.fraflag || logkrq.lbflag) {
					// 判断该条数据是否为报警数据
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					Integer battery = null;
					if (logkrq.fraflag) {
						// 火灾报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("检测报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (logkrq.lbflag) {
						// 低电压报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						battery = 10;
					}
					// 往设备报警表插入数据
					Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, logkrq.getMacAddr(), 1, battery,
							ConvertHelp.getOnTime());
					// 推送报警数据
					try {
						RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
				} else {
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), null, null,
							ConvertHelp.getOnTime());
				}
			}
		} else if (FrameTypeStr.equalsIgnoreCase("b3")) {
			// 测试报警数据
			System.out.println("测试报警数据");
		} else {
			System.out.println("未知数据");
		}
	}

	public void krq_AnalysisFunHotcomm(Integer moduleid, ReceiveModel model) {
		// 字符串转16进制字节数组
		byte[] krq_date = ConvertHelp.strToToHexByte(model.getData());
		// 29代表可燃气
		String bthead = String.format("%02x", new Integer(krq_date[1] & 0xff));
		// 设备状态0x00 正常状态 0x01 防拆报警 0x02 气体泄漏和防拆报警 0x03 气体泄漏 0x04 低电压
		String devstate = String.format("%02x", new Integer(krq_date[2] & 0xff));
		// 传感器数据Byte3：温度值整数 Byte2：温度值小数 Byte1：电压值整数 Byte0：电压值小数
		// 电压00 29 00 22550311 0000350000
		String battery = String.valueOf(Integer.parseInt(String.format("%02x", new Integer(krq_date[4] & 0xff)), 16));
		// + "." + String.valueOf(Integer.parseInt(String.format("%02x", new
		// Integer(krq_date[3] & 0xff)), 16));
		// 温度
		String temperature = String
				.valueOf(Integer.parseInt(String.format("%02x", new Integer(krq_date[6] & 0xff)), 16)) + "."
				+ String.valueOf(Integer.parseInt(String.format("%02x", new Integer(krq_date[5] & 0xff), 16)));
		// 判断是否为可燃气数据
		if (bthead.equals("29")) {
			// 入库log_yg数据库
			Log_krq logkrq = new Log_krq();
			logkrq.setVoltage(battery);
			logkrq.setTemperature(temperature);
			logkrq.setKeepalive(true);
			logkrq.setQueueid(model.getId());
			logkrq.setAddtime(ConvertHelp.getOnTime());
			logkrq.setCommsysType(model.getExtra().getCommsysType());
			logkrq.setRssi(model.getExtra().getRssi());
			logkrq.setSnr(model.getExtra().getSnr());
			logkrq.setFrameCnt(model.getExtra().getFrameCnt());
			logkrq.setGwid(model.getExtra().getGwid());
			logkrq.setGwip(model.getExtra().getGwip());
			logkrq.setChannel(model.getExtra().getChannel());
			logkrq.setSf(model.getExtra().getSf());
			logkrq.setPub(model.getPub());
			logkrq.setMacAddr(model.getMacAddr());
			logkrq.setData(model.getData());
			logkrq.setCalibration(true);
			logkrq.setRecv(ConvertHelp.getOnTime());
			// 将处理完成的数据插入数据库(日志数据)
			Injection.injection.krqMapper.insertSelective(logkrq);
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, model.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				// 判断是否报警0x00 正常状态 0x01 防拆报警 0x02 气体泄漏和防拆报警 0x03 气体泄漏 0x04 低电压
				if (devstate.equals("01") || devstate.equals("02") || devstate.equals("03") || devstate.equals("04")) {
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (devstate.equals("01")) {
						// 防拆报警
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("防拆报警", moduleid).getDeviceid());
					} else if (devstate.equals("02")) {
						// 气体泄漏和防拆同时报警
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("气体泄漏和防拆同时报警", moduleid).getDeviceid());
					} else if (devstate.equals("03")) {
						// 气体泄漏
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("气体泄漏报警", moduleid).getDeviceid());
					} else if (devstate.equals("04")) {
						// 低电压
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("低电压报警", moduleid).getDeviceid());
					}
					// 往设备报警表插入数据
					Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, model.getMacAddr(), 1, Integer.parseInt(battery),
							ConvertHelp.getOnTime());
					// 推送报警数据
					try {
						RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
				} else {
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), null, Integer.parseInt(battery),
							ConvertHelp.getOnTime());
				}
			}
		}
	}
}
