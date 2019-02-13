package com.hot.parse.service.xiaoan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.mc.Log_mc;
import com.hot.parse.entity.sxdl.Log_sxdl;
import com.hot.parse.entity.sxdy.Log_sxdy;
import com.hot.parse.entity.sy.Log_sy;
import com.hot.parse.entity.sydl.Log_sydl;
import com.hot.parse.utils.CRCHelp;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class XiaoAnServiceTcp {

	private Logger logger = LoggerFactory.getLogger(XiaoAnServiceTcp.class);

	private RabbitMQSendMsg mqSendMsg = new RabbitMQSendMsg();

	/*public void xiaoan_AnalysisTcp(ChannelHandlerContext ctx, Integer moduleid, String data) throws Exception {
		*//****************************************************
		 * 消安解析 0202 0100 22 00000000
		 * 00E20101080033500F0096022206000000000000B783275485
		 * 
		 * 0202---帧起始符 0100---标识传感器数据报文类型 2200---报文长度 00000000---帧序号
		 * E20101080033---时间 -------------传感器报文 50---报文头 0F---数据报文长度 0096---网络号
		 * 02---设备号 22---电池电压
		 * 
		 * 06---传感器类型 3：水压传感器 5：电压传感器 6：电流传感器 9：常开开关量传感器 10：常关开关量传感器
		 * 000000000000---传感器数据
		 * 
		 * B783---CRC16校验 27---信号强度 5485---CRC16校验
		 ***************************************************//*
		// 判断是否为包头
		if (data.substring(0, 4).equalsIgnoreCase("0202")) {
			// 转发特斯联的数据
			TSLMsgModel msgModelTSL = new TSLMsgModel();
			String MsgType = data.substring(4, 8);
			String numstr = data.substring(12, 20);
			String devStr = data.substring(32);// , data.length());
			switch (MsgType) {
			case "0010":// 注册
				ReRegister(ctx, "0010", numstr);
				break;
			case "0210":// 心跳
				// 01心跳
				msgModelTSL.setCommand("01");
				ReRegister(ctx, "0010", numstr);
				break;
			case "0100":// 传感器数据
				msgModelTSL.setCommand("01");
				String devType = devStr.substring(12, 14);
				String networkNo = devStr.substring(4, 8);// 网络号
				String devAddr = devStr.substring(8, 10);// 设备地址
				String voltage = devStr.substring(10, 12);// 电池电压
				// 电量
				int voltageInt = Integer.parseInt(voltage.toString(), 16);
				Integer battery = (int) (((voltageInt / 10) / 3.6) * 100);
				msgModelTSL.setBatteryLevel(String.valueOf(battery));

				String dataStr = devStr.substring(14);// , devStr.length());//
														// 传感器数据
				ReceiveModel model = new ReceiveModel();
				model.setData(dataStr);
				// 判断是哪一种设备
				switch (devType) {
				case "03":// 水压
					
					 * 020201001E000100000018061216242D
					 * 
					 * 50 0B 0096 40 21 03 0050 21173A9719
					 
					// 15水压
					msgModelTSL = shuiya(16, devAddr, model, msgModelTSL);
					break;
				case "05":// 三相交流电压传感器
					
					 * 0202 0100 220009000000120512120F34500F0096012405
					 * 
					 * 0ED9 0EA8 0EA8
					 * 
					 * 7C3939A77A
					 
					// 17电压
					msgModelTSL = sxdy(15, devAddr, model, msgModelTSL);
					break;
				case "06":// 三相交流电流传感器
					
					 * 0202 0100 2200 08000000 180519001B37500F0096022206
					 * 
					 * 0F18----15.24 1F01----31.01 2117----33.23
					 * 
					 * DB8F440840
					 
					// 16三相交流电流
					msgModelTSL = sxdl(14, devAddr, model, msgModelTSL);
					break;
				case "07":// 剩余电流传感器
					
					 * 020201001E0001000000120512111D27500B00960322 07 1B00
					 * 1CCD39A77A
					 
					// 18剩余电流
					msgModelTSL = sydl(17, devAddr, model, msgModelTSL);
					break;
				case "09":// 常开开关量传感器,门磁
					msgModelTSL = mc(11, devAddr, model, msgModelTSL);
					break;
				case "10":// 常关开关量传感器
					break;
				default:
					break;
				}
				// System.out.println(Integer.valueOf(msgModelTSL.getBatteryLevel()));
				if (msgModelTSL.getSerialNo() != null) {
					mqSendMsg.jsonPost(msgModelTSL);
				}
				ReRegister(ctx, "0010", numstr);
			}
		}
	}

	// 门磁解析
	public TSLMsgModel mc(int moduleid, String devAddr, ReceiveModel model, TSLMsgModel msgModelTSL) {
		
		 * 50 0A 0096 04 1D 09 01 FB6D
		 
		Log_mc log_mc = new Log_mc();
		msgModelTSL.Type = "11";
		log_mc.setData(model.getData());
		log_mc.setAddtime(ConvertHelp.getOnTime());
		log_mc.setVoltage(msgModelTSL.getBatteryLevel());
		log_mc.setState(model.getData().equals("01"));
		model.setData(model.getData().substring(0, 2));
		msgModelTSL.setDevValue(model.getData());
		// 根据设备编号查出设备信息
		DevMsg devMsg = new DevMsg();
		// tcp/ip数据
		devMsg = Injection.injection.devMapper.seldevnum(moduleid, devAddr);
		if (devMsg != null && devMsg.getMac() != null && !TextUtils.isEmpty(devMsg.getMac())) {
			log_mc.setMacAddr(devMsg.getMac());
		}
		// 入库，门磁日志表
		Injection.injection.mcMapper.insertSelective(log_mc);
		// 是否报警
		boolean isalarm = false;
		if (devMsg != null && !devMsg.equals("")) {
			// 防止根据mac地址查询出的设备不存在,null空指针错误
			if (model.getData().equals("01") || Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				if (model.getData().equals("01")) {
					// 开门报警
					msgModelTSL.Status = "2";
					msgModelTSL.Command = "02";
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("开门报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					isalarm = true;
				} else if (Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
					// 电量低于百分之20，低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					isalarm = true;
				}
				// 往设备报警表插入数据
				 Injection.injection.devMapper.insertSelective(devAlarm); 
				// 修改设备状态
				
				 * Injection.injection.devMapper.updatedev(moduleid,
				 * devMsg.getMac(), 1,
				 * Integer.valueOf(msgModelTSL.getBatteryLevel()));
				 
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			// 判断是否报警
			if (isalarm) {
				// 信息类型
				msgModelTSL.setCommand("02");
				// 设备状态2报警
				msgModelTSL.setStatus("2");
			} else {
				// 设备状态1正常
				msgModelTSL.setStatus("1");
			}
			// 设备地址
			msgModelTSL.setDevAddr(devMsg.getCode());
			// 设备mac
			msgModelTSL.setSerialNo(devMsg.getMac());
			// 门磁温度
			msgModelTSL.setDevValue(model.getData());
			// 经度
			msgModelTSL.setLong(devMsg.getLng());
			// 纬度
			msgModelTSL.setLat(devMsg.getLat());
			// 当前时间戳
			msgModelTSL.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// 设备类型
			msgModelTSL.setDeviceBrand("水压");
			// moduleid
			msgModelTSL.setDeviceModule(String.valueOf(16));
			msgModelTSL.Topic = "menci";
		}
		return msgModelTSL;
	}

	// 水压解析
	public TSLMsgModel shuiya(int moduleid, String devAddr, ReceiveModel model, TSLMsgModel msgModelTSL)
			throws Exception {
		msgModelTSL.setType("15");
		model.setData(model.getData().substring(0, 4));
		// 压力数据整数部分
		String integerPart = model.getData().substring(0, 2);
		// 压力数据小数部分
		String decimalPart = model.getData().substring(2, 4);
		// 水压数据
		String pressureStr = String.valueOf(Integer.parseInt(integerPart, 16)) + "."
				+ String.valueOf(Integer.parseInt(decimalPart, 16));
		// 水压数据库日志表
		Log_sy logsy = new Log_sy();
		// 数据添加时间
		logsy.setInput_time(ConvertHelp.getOnTime());
		// 水压数据值
		logsy.setTemp_value(Float.valueOf(pressureStr));
		// 根据设备编号查出设备信息
		DevMsg devMsg = new DevMsg();
		// tcp/ip数据
		devMsg = Injection.injection.devMapper.seldevnum(moduleid, devAddr);
		if (devMsg != null && devMsg.getMac() != null && !TextUtils.isEmpty(devMsg.getMac())) {
			logsy.setMac_addr(devMsg.getMac());
		}
		// 将处理完成的数据插入数据库(日志数据)
		Injection.injection.syMapper.insertSelective(logsy);
		// 是否报警
		boolean isalarm = false;
		if (devMsg != null && !devMsg.equals("")) {
			// 防止根据mac地址查询出的设备不存在,null空指针错误
			if (devMsg.getAlarmset() != 0 && (Float.valueOf(devMsg.getLessalarmvalue()) > logsy.getTemp_value()
					|| Float.valueOf(devMsg.getTopalarmvalue()) < logsy.getTemp_value())) {
				// 设备报警了
				isalarm = true;
				// 判断是否报警,如果需要报警，但不存在超高报警，超低报警就没必要进来了
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				switch (devMsg.getAlarmset()) {
				case 1:
					if (Float.valueOf(devMsg.getLessalarmvalue()) > logsy.getTemp_value()) {// 判断当前值是否低于最低阈值
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 2:
					if (Float.valueOf(devMsg.getTopalarmvalue()) < logsy.getTemp_value()) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 3:
					// 低于最低或高于最高
					if (Float.valueOf(devMsg.getTopalarmvalue()) < logsy.getTemp_value()) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (Float.valueOf(devMsg.getLessalarmvalue()) > logsy.getTemp_value()) {
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				default:
					break;
				}
				if (Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
					// 电量低于百分之20，低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1,
						Integer.valueOf(msgModelTSL.getBatteryLevel()), ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			// 判断是否报警
			if (isalarm) {
				// 信息类型
				msgModelTSL.setCommand("02");
				// 设备状态2报警
				msgModelTSL.setStatus("2");
			} else {
				// 设备状态1正常
				msgModelTSL.setStatus("1");
			}
			// 设备地址
			msgModelTSL.setDevAddr(devMsg.getCode());
			// 设备mac
			msgModelTSL.setSerialNo(devMsg.getMac());
			// 水压数据
			msgModelTSL.setDevValue(pressureStr);
			// 经度
			msgModelTSL.setLong(devMsg.getLng());
			// 纬度
			msgModelTSL.setLat(devMsg.getLat());
			// 当前时间戳
			msgModelTSL.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// 设备类型
			msgModelTSL.setDeviceBrand("水压");
			// moduleid
			msgModelTSL.setDeviceModule(String.valueOf(16));
			//
			msgModelTSL.setTopic("xiaoanshuiya");
		}
		return msgModelTSL;
	}

	// 三相电压解析
	public TSLMsgModel sxdy(int moduleid, String devAddr, ReceiveModel model, TSLMsgModel msgModelTSL)
			throws Exception {
		msgModelTSL.setType("17");
		model.setData(model.getData().substring(0, 12));
		// 电压A数据整数部分
		String DY_AInt = model.getData().substring(0, 2);
		// 电压A数据小数部分
		String DY_APart = model.getData().substring(2, 4);
		// 电压A数据
		String DY_A = String.valueOf(Integer.parseInt(DY_AInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DY_APart, 16));
		// 电压B数据整数部分
		String DY_BInt = model.getData().substring(4, 6);
		// 电压B数据小数部分
		String DY_BPart = model.getData().substring(6, 8);
		// 电压B数据
		String DY_B = String.valueOf(Integer.parseInt(DY_BInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DY_BPart, 16));
		// 电压C数据整数部分
		String DY_CInt = model.getData().substring(8, 10);
		// 电压C数据小数部分
		String DY_CPart = model.getData().substring(10, 12);
		// 电压C数据
		String DY_C = String.valueOf(Integer.parseInt(DY_CInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DY_CPart, 16));
		// 三相电压数据库日志表
		Log_sxdy log_sxdy = new Log_sxdy();
		// 数据插入时间
		log_sxdy.setInput_time(ConvertHelp.getOnTime());
		// 电压A数据
		log_sxdy.setDY_A(DY_A);
		// 电压B数据
		log_sxdy.setDY_B(DY_B);
		// 电压C数据
		log_sxdy.setDY_C(DY_C);
		// 根据设备编号查出设备信息
		DevMsg devMsg = new DevMsg();
		// tcp/ip数据
		devMsg = Injection.injection.devMapper.seldevnum(moduleid, devAddr);
		if (devMsg != null && devMsg.getMac() != null && !TextUtils.isEmpty(devMsg.getMac())) {
			log_sxdy.setMac_addr(devMsg.getMac());
		}
		// 将处理完成的数据插入数据库(日志数据)
		Injection.injection.sxdyMapper.insertSelective(log_sxdy);
		// 是否报警
		boolean isalarm = false;
		if (devMsg != null && !devMsg.equals("")) {
			// 防止根据mac地址查询出的设备不存在,null空指针错误
			if (devMsg.getAlarmset() != 0
					&& (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_C())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_C()))) {
				// 设备报警了
				isalarm = true;
				// 判断是否报警,如果需要报警，但不存在超高报警，超低报警就没必要进来了
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				switch (devMsg.getAlarmset()) {
				case 1:
					if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_C())) {// 判断当前值是否低于最低阈值
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 2:
					if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_C())) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 3:
					// 低于最低或高于最高
					if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdy.getDY_C())) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdy.getDY_C())) {
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				default:
					break;
				}
				if (Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
					// 电量低于百分之20，低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1,
						Integer.valueOf(msgModelTSL.getBatteryLevel()), ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			// 判断是否报警
			if (isalarm) {
				// 信息类型
				msgModelTSL.setCommand("02");
				// 设备状态2报警
				msgModelTSL.setStatus("2");
			} else {
				// 设备状态1正常
				msgModelTSL.setStatus("1");
			}
			// 设备地址
			msgModelTSL.setDevAddr(devMsg.getCode());
			// 设备mac
			msgModelTSL.setSerialNo(devMsg.getMac());
			// 水压数据
			msgModelTSL.setDevValue(DY_A + "_" + DY_B + "_" + DY_C);
			// 经度
			msgModelTSL.setLong(devMsg.getLng());
			// 纬度
			msgModelTSL.setLat(devMsg.getLat());
			// 当前时间戳
			msgModelTSL.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// 设备类型
			msgModelTSL.setDeviceBrand("三相电压");
			// moduleid
			msgModelTSL.setDeviceModule(String.valueOf(15));
			//
			msgModelTSL.setTopic("sanxiangdianya");
		}
		return msgModelTSL;
	}

	// 三相电流解析
	public TSLMsgModel sxdl(int moduleid, String devAddr, ReceiveModel model, TSLMsgModel msgModelTSL)
			throws Exception {
		msgModelTSL.setType("16");
		model.setData(model.getData().substring(0, 12));
		// 电流A数据整数部分
		String DL_AInt = model.getData().substring(0, 2);
		// 电流A数据小数部分
		String DL_APart = model.getData().substring(2, 4);
		// 电流A数据
		String DL_A = String.valueOf(Integer.parseInt(DL_AInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DL_APart, 16));
		// 电流数据整数部分
		String DL_BInt = model.getData().substring(4, 6);
		// 电流B数据小数部分
		String DL_BPart = model.getData().substring(6, 8);
		// 电流B数据
		String DL_B = String.valueOf(Integer.parseInt(DL_BInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DL_BPart, 16));
		// 电流C数据整数部分
		String DL_CInt = model.getData().substring(8, 10);
		// 电流C数据小数部分
		String DL_CPart = model.getData().substring(10, 12);
		// 电流C数据
		String DL_C = String.valueOf(Integer.parseInt(DL_CInt, 16)) + "."
				+ String.valueOf(Integer.parseInt(DL_CPart, 16));
		// System.out.println(DL_A + "," + DL_B + "," + DL_C);
		// 三相电流数据库日志表
		Log_sxdl log_sxdl = new Log_sxdl();
		// 数据插入时间
		log_sxdl.setInput_time(ConvertHelp.getOnTime());
		// 电流A数据
		log_sxdl.setDL_A(DL_A);
		// 电流B数据
		log_sxdl.setDL_B(DL_B);
		// 电流C数据
		log_sxdl.setDL_C(DL_C);
		// 根据设备编号查出设备信息
		DevMsg devMsg = new DevMsg();
		// tcp/ip数据
		devMsg = Injection.injection.devMapper.seldevnum(moduleid, devAddr);
		if (devMsg != null && devMsg.getMac() != null && !TextUtils.isEmpty(devMsg.getMac())) {
			log_sxdl.setMac_addr(devMsg.getMac());
		}
		// 将处理完成的数据插入数据库(日志数据)
		Injection.injection.sxdlMapper.insertSelective(log_sxdl);
		// 是否报警
		boolean isalarm = false;
		if (devMsg != null && !devMsg.equals("")) {
			// 防止根据mac地址查询出的设备不存在,null空指针错误
			if (devMsg.getAlarmset() != 0
					&& (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C()))) {
				// 设备报警了
				isalarm = true;
				// 判断是否报警,如果需要报警，但不存在超高报警，超低报警就没必要进来了
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				switch (devMsg.getAlarmset()) {
				case 1:
					if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())) {// 判断当前值是否低于最低阈值
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 2:
					if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C())) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 3:
					// 低于最低或高于最高
					if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C())) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
							|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())) {
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				default:
					break;
				}
				if (Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
					// 电量低于百分之20，低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1,
						Integer.valueOf(msgModelTSL.getBatteryLevel()), ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			// 判断是否报警
			if (isalarm) {
				// 信息类型
				msgModelTSL.setCommand("02");
				// 设备状态2报警
				msgModelTSL.setStatus("2");
			} else {
				// 设备状态1正常
				msgModelTSL.setStatus("1");
			}
			// 设备地址
			msgModelTSL.setDevAddr(devMsg.getCode());
			// 设备mac
			msgModelTSL.setSerialNo(devMsg.getMac());
			// 水压数据
			msgModelTSL.setDevValue(DL_A + "_" + DL_B + "_" + DL_C);
			// 经度
			msgModelTSL.setLong(devMsg.getLng());
			// 纬度
			msgModelTSL.setLat(devMsg.getLat());
			// 当前时间戳
			msgModelTSL.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// 设备类型
			msgModelTSL.setDeviceBrand("三相电流");
			// moduleid
			msgModelTSL.setDeviceModule(String.valueOf(14));
			//
			msgModelTSL.setTopic("sanxiangdianliu");
		}
		return msgModelTSL;
	}

	// 剩余电流解析
	public TSLMsgModel sydl(int moduleid, String devAddr, ReceiveModel model, TSLMsgModel msgModelTSL)
			throws Exception {
		msgModelTSL.setType("18");
		model.setData(model.getData().substring(0, 4));
		// 剩余电流整数位
		String electriccuintegerPart = model.getData().substring(0, 2);
		// 剩余电流小数位
		String electriccudecimalPart = model.getData().substring(2, 4);
		// 剩余电流数据
		String ElectriccurrentStr = String.valueOf(Integer.parseInt((electriccuintegerPart), 16)) + "."
				+ String.valueOf(Integer.parseInt((electriccudecimalPart), 16));
		// 剩余电流数据库日志表
		Log_sydl log_sydl = new Log_sydl();
		// 数据插入时间
		log_sydl.setInput_time(ConvertHelp.getOnTime());
		// 当前电流数据
		log_sydl.setTemp_value(Float.valueOf(ElectriccurrentStr));
		// 根据设备编号查出设备信息
		DevMsg devMsg = new DevMsg();
		// tcp/ip数据
		devMsg = Injection.injection.devMapper.seldevnum(moduleid, devAddr);
		if (devMsg != null && devMsg.getMac() != null && !TextUtils.isEmpty(devMsg.getMac())) {
			log_sydl.setMac_addr(devMsg.getMac());
		}
		// 将处理完成的数据插入数据库(日志数据)
		Injection.injection.sydlMapper.insertSelective(log_sydl);
		// 是否报警
		boolean isalarm = false;
		if (devMsg != null && !devMsg.equals("")) {
			// 防止根据mac地址查询出的设备不存在,null空指针错误
			if (devMsg.getAlarmset() != 0 && (Float.valueOf(devMsg.getLessalarmvalue()) > log_sydl.getTemp_value()
					|| Float.valueOf(devMsg.getTopalarmvalue()) < log_sydl.getTemp_value())) {
				// 设备报警了
				isalarm = true;
				// 判断是否报警,如果需要报警，但不存在超高报警，超低报警就没必要进来了
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				switch (devMsg.getAlarmset()) {
				case 1:
					if (Float.valueOf(devMsg.getLessalarmvalue()) > log_sydl.getTemp_value()) {// 判断当前值是否低于最低阈值
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 2:
					if (Float.valueOf(devMsg.getTopalarmvalue()) < log_sydl.getTemp_value()) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				case 3:
					// 低于最低或高于最高
					if (Float.valueOf(devMsg.getTopalarmvalue()) < log_sydl.getTemp_value()) {
						// 超高报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (Float.valueOf(devMsg.getLessalarmvalue()) > log_sydl.getTemp_value()) {
						// 超低报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					break;
				default:
					break;
				}
				if (Integer.valueOf(msgModelTSL.getBatteryLevel()) < 20) {
					// 电量低于百分之20，低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1,
						Integer.valueOf(msgModelTSL.getBatteryLevel()), ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			// 判断是否报警
			if (isalarm) {
				// 信息类型
				msgModelTSL.setCommand("02");
				// 设备状态2报警
				msgModelTSL.setStatus("2");
			} else {
				// 设备状态1正常
				msgModelTSL.setStatus("1");
			}
			// 设备地址
			msgModelTSL.setDevAddr(devMsg.getCode());
			// 设备mac
			msgModelTSL.setSerialNo(devMsg.getMac());
			// 水压数据
			msgModelTSL.setDevValue(ElectriccurrentStr);
			// 经度
			msgModelTSL.setLong(devMsg.getLng());
			// 纬度
			msgModelTSL.setLat(devMsg.getLat());
			// 当前时间戳
			msgModelTSL.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// 设备类型
			msgModelTSL.setDeviceBrand("剩余电流");
			// moduleid
			msgModelTSL.setDeviceModule(String.valueOf(17));
			//
			msgModelTSL.setTopic("shengyudianliu");
		}
		return msgModelTSL;
	}

	// 注册/心跳，回复数据
	public void ReRegister(ChannelHandlerContext ctx, String ackStr, String num) throws UnsupportedEncodingException {
		byte[] by = new byte[6];
		Calendar nowTime = Calendar.getInstance();
		by[0] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.YEAR)).substring(2, 4));
		by[1] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MONTH) + 1));
		by[2] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.DAY_OF_MONTH)));
		by[3] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.HOUR_OF_DAY)));
		by[4] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MINUTE)));
		by[5] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.SECOND)));
		String timeStr = ConvertHelp.StringParseByte(by);
		timeStr = "0202" + ackStr + "1200" + num + timeStr;
		String crc16str = CRCHelp.getCRC16_CCITT(timeStr);
		if (crc16str.length() != 4) {
			nowTime = Calendar.getInstance();
			by[0] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.YEAR)).substring(2, 4));
			by[1] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MONTH) + 1));
			by[2] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.DAY_OF_MONTH)));
			by[3] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.HOUR_OF_DAY)));
			by[4] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.MINUTE)));
			by[5] = ConvertHelp.ConvertBCD(String.valueOf(nowTime.get(Calendar.SECOND)));
			timeStr = ConvertHelp.StringParseByte(by);
			timeStr = "0202" + ackStr + "1200" + num + timeStr;
			crc16str = CRCHelp.getCRC16_CCITT(timeStr);
		}
		String crc16true = crc16str.substring(2) + crc16str.substring(0, 2);
		timeStr = timeStr + crc16true;
		byte[] sendData = ConvertHelp.strToToHexByte(timeStr);
		// 发送消息
		ctx.writeAndFlush(Unpooled.copiedBuffer(sendData));
		String datastr = "";
		String a = "";
		for (int i = 0; i < sendData.length; i++) {
			// 拼接原始十六进制数据
			a = Integer.toHexString(sendData[i]);
			if (a.length() < 2) {
				a = "0" + a;
			}
			datastr = datastr + a;
		}
		// 清除ffffff废数据
		datastr = datastr.replace("ffffff", "");
		logger.error("回复消息{}", datastr);
		// ctx.writeAndFlush(Unpooled.copiedBuffer(datastr,CharsetUtil.UTF_8));
	}*/
}
