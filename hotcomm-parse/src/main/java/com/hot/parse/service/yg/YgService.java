package com.hot.parse.service.yg;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.yg.Log_yg;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class YgService {

	/**
	 * 第一款烟感设备解析方法
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void yg_AnalysisFunOne(Integer moduleid, ReceiveModel model) {
		// 字符串转16进制字节数组
		byte[] yg_date = ConvertHelp.strToToHexByte(model.getData());
		// 入库log_yg数据库
		Log_yg newModed = new Log_yg();
		// 取出包头判断数据信息 0xB1 代表报警信息，为 0xB2 代表工作状态信息
		String bthead = String.format("%02x", new Integer(yg_date[0] & 0xff));
		// 设备是否故障
		boolean FaultAlarm = false;
		if (bthead.equalsIgnoreCase("b1") || bthead.equalsIgnoreCase("b2")) {// 该条数据为报警信息或者心跳
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			// 分析yg_date[1]
			int FrameCountStateint = Integer.parseInt(String.format("%02x", new Integer(yg_date[1] & 0xff)), 16);
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(FrameCountStateint, n);
				switch (n) {
				case 0:
					newModed.temp_alarm = i > 0 ? true : false;
					break;
				case 2:// SFA故障报警
					FaultAlarm = i > 0 ? true : false;
					break;
				case 3:// FRA
					newModed.fire_alarm = i > 0 ? "true" : "false";
					// newModel.Command = i == 1 ? "1" : "2";
					newModed.status = (short) i;
					break;
				}
			}
			// 分析yg_date[2]
			int ParkFlagBatteryLevelint = Integer.parseInt(String.format("%02x", new Integer(yg_date[2] & 0xff)), 16);
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(ParkFlagBatteryLevelint, n);
				switch (n) {
				case 0:
					newModed.fault_alarm += (i > 0 ? "MOFA" : "") + ",";
					break;
				case 1:
					newModed.lb_alarm = i > 0 ? "true" : "false";
					// newModel.Command = i == 1 ? "4" : "2";
					// newModel.BatteryLevel = i == 1 ? "0" : "255";
					break;
				case 2:
					newModed.fault_alarm += (i > 0 ? "TSFA" : "") + ",";
					break;
				case 3:
					newModed.fault_alarm += (i > 0 ? "SLFA" : "") + ",";
					break;
				case 4:
					newModed.fault_alarm += (i > 0 ? "SOA" : "") + ",";
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				}
			}
			// 给对应数据库设备日志表字段赋值
			int temp_valueInt = Integer.parseInt(String.format("%02x", new Integer(yg_date[3] & 0xff)), 16);
			newModed.setTemp_value(temp_valueInt);
			newModed.setChannel(model.id);
			newModed.setInput_time(ConvertHelp.getOnTime());
			newModed.setRssi((int) model.extra.rssi);
			newModed.setSnr((int) model.extra.snr);
			newModed.setGwid(model.extra.gwid);
			newModed.setGwip(model.extra.gwip);
			newModed.setChannel(model.extra.channel);
			newModed.setSf(model.extra.sf);
			newModed.setMac_addr(model.macAddr);
			newModed.setRepeater(model.data);
			newModed.setFault_alarm(newModed.getFault_alarm().replace("null", ""));
			// 将处理完成的数据插入数据库(日志数据)
			int addResultModel = Injection.injection.ygMapper.insertSelective(newModed);
			if (addResultModel > 0) {
				model.IsSucess = true;
			}
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, newModed.getMac_addr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				// 判断报警字段
				if (newModed.fire_alarm == "true" || newModed.lb_alarm == "true" || FaultAlarm) {
					// 报警类型：火灾报警！//报警类型：低电报警！//报警类型：故障报警
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (newModed.fire_alarm == "true" || newModed.lb_alarm == "true") {
						// 设备报警
						if (newModed.fire_alarm == "true") {
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("烟雾报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						} else if (newModed.lb_alarm == "true") {
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						addResultModel = Injection.injection.devMapper.updatedev(moduleid, newModed.getMac_addr(), 1,
								null, ConvertHelp.getOnTime());
					} else if (FaultAlarm) {
						// 故障报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("故障报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						// 修改设备状态
						addResultModel = Injection.injection.devMapper.updatedev(moduleid, newModed.getMac_addr(), 2,
								null, ConvertHelp.getOnTime());
					}
					// 插入设备报警表数据
					addResultModel = Injection.injection.devMapper.insertSelective(devAlarm);
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
		}
	}

	/**
	 * 烟感设备解析方法 ,泛海三江
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void yg_AnalysisFunFanHaiSJ(Integer moduleid, ReceiveModel model) {
		// 入库log_yg数据库
		Log_yg newModed = new Log_yg();
		newModed.setChannel(model.id);
		newModed.setInput_time(ConvertHelp.getOnTime());
		newModed.setRssi((int) model.extra.rssi);
		newModed.setSnr((int) model.extra.snr);
		newModed.setGwid(model.extra.gwid);
		newModed.setGwip(model.extra.gwip);
		newModed.setChannel(model.extra.channel);
		newModed.setSf(model.extra.sf);
		newModed.setMac_addr(model.macAddr);
		newModed.setRepeater(model.data);
		// 将处理完成的数据插入数据库(日志数据)
		Injection.injection.ygMapper.insertSelective(newModed);
		DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, newModed.getMac_addr());
		if (devMsg != null && !devMsg.equals("")) {// 防止根据mac地址查询出的设备不存在,null空指针错误
			// data转ASCII
			String ASCII = ConvertHelp.asciiToString(ConvertHelp.s16To10str(model.getData()));
			System.out.println("ASCII：" + ASCII);
			// 报警表数据devAlarm
			DevAlarm devAlarm = new DevAlarm();
			devAlarm.setDeviceid(devMsg.getDeviceid());
			devAlarm.setMac(devMsg.getMac());
			devAlarm.setRecvtime(ConvertHelp.getOnTime());
			devAlarm.setAddtime(ConvertHelp.getOnTime());
			devAlarm.setModuleid(moduleid);
			switch (ASCII) {
			case "020100":
				devAlarm.setAlarmstateid(Injection.injection.devMapper.seldevstate("烟雾报警", moduleid).getDeviceid());
				System.out.println("火警");
				break;
			case "020200":
				devAlarm.setAlarmstateid(Injection.injection.devMapper.seldevstate("故障报警", moduleid).getDeviceid());
				System.out.println("传感器故障");
				break;
			case "020300":
				devAlarm.setAlarmstateid(Injection.injection.devMapper.seldevstate("低电压报警", moduleid).getDeviceid());
				System.out.println("电池低电压");
				break;
			case "020400":
				System.out.println("火警消除");
				break;
			case "020500":
				System.out.println("传感器故障消除");
				break;
			case "020600":
				System.out.println("低电压恢复");
				break;
			case "020000":
				System.out.println("正常状态");
				break;
			case "020E00":
				System.out.println("探测器上电");
				break;
			case "020D00":
				System.out.println("按键自检");
				break;
			default:
				break;
			}
			if (devAlarm.getAlarmstateid() != null) {
				// 设备报警
				// 插入设备报警表数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 烟感设备解析方法 ,hotcomm
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void yg_AnalysisFunHotcomm(Integer moduleid, ReceiveModel model) {
		// 字符串转16进制字节数组
		byte[] yg_date = ConvertHelp.strToToHexByte(model.getData());
		// 22代表烟感
		String bthead = String.format("%02x", new Integer(yg_date[1] & 0xff));
		// 设备状态0x00 正常状态 0x01 火警 0x02 自检 0x03 故障 0x04 低电压
		String devstate = String.format("%02x", new Integer(yg_date[2] & 0xff));
		// 传感器数据Byte3：温度值整数 Byte2：温度值小数 Byte1：电压值整数 Byte0：电压值小数
		// 电压
		String battery = String.valueOf(Integer.parseInt(String.format("%02x", new Integer(yg_date[4] & 0xff)), 16));
		// + "." + String.valueOf(Integer.parseInt(String.format("%02x", new
		// Integer(yg_date[3] & 0xff)), 16));
		// 温度
		String temperature = String.valueOf(Integer.parseInt(String.format("%02x", new Integer(yg_date[6] & 0xff)), 16))
				+ "." + String.valueOf(Integer.parseInt(String.format("%02x", new Integer(yg_date[5] & 0xff), 16)));
		// 判断是否为烟感数据
		if (bthead.equals("22")) {
			// 入库log_yg数据库
			Log_yg newModed = new Log_yg();
			newModed.setChannel(model.id);
			newModed.setInput_time(ConvertHelp.getOnTime());
			newModed.setRssi((int) model.extra.rssi);
			newModed.setSnr((int) model.extra.snr);
			newModed.setGwid(model.extra.gwid);
			newModed.setGwip(model.extra.gwip);
			newModed.setChannel(model.extra.channel);
			newModed.setSf(model.extra.sf);
			newModed.setMac_addr(model.macAddr);
			newModed.setRepeater(model.data);
			newModed.setTemp_value(Float.parseFloat(temperature));
			// 将处理完成的数据插入数据库(日志数据)
			Injection.injection.ygMapper.insertSelective(newModed);
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, model.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {// 防止根据mac地址查询出的设备不存在,null空指针错误
				// 判断是否报警0x00 正常状态 0x01 火警 0x02 自检 0x03 故障 0x04 低电压
				if (devstate.equals("01") || devstate.equals("03") || devstate.equals("04")) {
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (devstate.equals("01")) {
						// 火警
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("烟雾报警", moduleid).getDeviceid());
					} else if (devstate.equals("03")) {
						// 故障
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("故障报警", moduleid).getDeviceid());
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
