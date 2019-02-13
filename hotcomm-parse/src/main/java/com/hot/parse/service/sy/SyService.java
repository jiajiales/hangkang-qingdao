package com.hot.parse.service.sy;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.sy.Log_sy;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class SyService {
	/**
	 * 消安水压
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void sy_AnalysisFunXiaoan(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] sy_byte = ConvertHelp.strToToHexByte(model.data);
		// 水压
		if (model.getData().substring(2, 4).equals("03")) {
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			// 电量
			int battery7bit = ConvertHelp.setIntegerSomeBit(7,
					Integer.parseInt(String.format("%02x", new Integer(sy_byte[1] & 0xff)), 16), false);
			Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
			/*
			 * byte[] a1 = new byte[2]; System.arraycopy(sy_byte, 2, a1, 0, 2);
			 * String pressureStr = String.format("%.1f",
			 * Integer.parseInt(ConvertHelp.StringParseByte(a1), 16) * 0.1);//
			 * 16进制转10进制
			 */
			// 压力数据整数部分
			String integerPart = String.format("%02x", new Integer(sy_byte[2] & 0xff));
			// 压力数据小数部分
			String decimalPart = String.format("%02x", new Integer(sy_byte[3] & 0xff));
			// 水压数据
			String pressureStr = String.valueOf(Integer.parseInt(integerPart, 16)) + "."
					+ String.valueOf(Integer.parseInt(decimalPart, 16));
			// 水压数据库日志表
			Log_sy log_sy = new Log_sy();
			log_sy.setTemp_value(Float.valueOf(pressureStr));
			log_sy.setDf_id(model.getId());
			log_sy.setFrame_cnt(String.valueOf(model.getExtra().getFrameCnt()));
			log_sy.setGwid(model.getExtra().getGwid());
			log_sy.setGwip(model.getExtra().getGwip());
			log_sy.setChannel(model.getExtra().getChannel());
			log_sy.setSf(model.getExtra().getSf());
			log_sy.setMac_addr(model.getMacAddr());
			log_sy.setInput_time(ConvertHelp.getOnTime());
			log_sy.setRssi(model.getExtra().getRssi());
			// 入库水压日志表
			Injection.injection.syMapper.insertSelective(log_sy);
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, log_sy.getMac_addr());
			// 是否报警
			boolean isalarm = false;
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				if (devMsg.getAlarmset() != 0 && (Float.valueOf(devMsg.getLessalarmvalue()) > log_sy.getTemp_value()
						|| Float.valueOf(devMsg.getTopalarmvalue()) < log_sy.getTemp_value())) {
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
						if (Float.valueOf(devMsg.getLessalarmvalue()) > log_sy.getTemp_value()) {// 判断当前值是否低于最低阈值
							// 超低报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						break;
					case 2:
						if (Float.valueOf(devMsg.getTopalarmvalue()) < log_sy.getTemp_value()) {
							// 超高报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						break;
					case 3:
						// 低于最低或高于最高
						if (Float.valueOf(devMsg.getTopalarmvalue()) < log_sy.getTemp_value()) {
							// 超高报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						} else if (Float.valueOf(devMsg.getLessalarmvalue()) > log_sy.getTemp_value()) {
							// 超低报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						break;
					default:
						break;
					}
					if (battery < 20) {
						// 电量低于百分之20，低电压报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					// 往设备报警表插入数据
					Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1, battery,
							ConvertHelp.getOnTime());
					// 推送报警数据
					try {
						RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, null);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
				} else {
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), null, battery,
							ConvertHelp.getOnTime());
				}
			}
		}
	}

	/**
	 * 消安水压,托普索尔
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void sy_AnalysisFunTuoPuSR(Integer moduleid, ReceiveModel model) {
		// 分割协议数据 10 01 1E 01 01 00 00 01 41 22 50
		byte[] sy_byte = ConvertHelp.strToToHexByte(model.data);
		// devtype 设备类型 01消火栓 02 压力表 03 液位
		String devtype = String.valueOf(Integer.parseInt(String.format("%02x", new Integer(sy_byte[1] & 0xff)), 16));
		if (devtype.equals("1")) {// 设备类型 01消火栓 02 压力表 03 液位
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			// devstate 01为消火栓状态正常；02为消火栓被撞，设备发生倾斜；03为消火栓被盗，有加速度产生；
			String devstate = String
					.valueOf(Integer.parseInt(String.format("%02x", new Integer(sy_byte[4] & 0xff)), 16));
			// 02为消火栓被撞，设备发生倾斜
			boolean devincline = false;
			// 03为消火栓被盗，有加速度产生
			boolean devsteal = false;
			// 设备故障
			boolean devfault = false;
			switch (devstate) {
			case "1":
				System.out.println("状态正常");
				break;
			case "2":
				// 02为消火栓被撞，设备发生倾斜
				devincline = true;
				break;
			case "3":
				// 03为消火栓被盗，有加速度产生
				devsteal = true;
				break;
			default:
				break;
			}
			// 解析出数据值 01 41 22 50
			// State设备数据状态
			String State = model.getData().substring(14, 15);
			// Unit数据单位
			String Unit = model.getData().substring(15, 16);
			// 小数点位置
			Integer position = 5 - Integer.valueOf(model.getData().substring(16, 17));
			// vaule数据数值
			String vaule = model.getData().substring(17);
			// 构造一个StringBuilder对象
			StringBuilder sb = new StringBuilder(vaule);
			sb.insert(position, ".");
			vaule = sb.toString();
			// 数据状态
			switch (State) {
			// 0表示数据正常，1表示数据低压超限，2表示数据高压超限，3表示压力波动变化超限，4表示设备故障
			case "0":
				break;
			case "1":
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				// 设备故障
				devfault = true;
				break;
			default:
				break;
			}
			// 数据单位
			switch (Unit) {
			// 1表示压力MPa， 2表示压力Bar，3表示压力KPa，4表示温度℃；5表示液位M，6表示流量m3/h
			case "1":
				Unit = "MPa";
				break;
			case "2":
				Unit = "Bar";
				break;
			case "3":
				Unit = "KPa";
				break;
			case "4":
				Unit = "℃";
				break;
			case "5":
				Unit = "M";
				break;
			case "6":
				Unit = "m3/h";
				break;
			default:
				break;
			}

			// 水压数据库日志表
			Log_sy log_sy = new Log_sy();
			// 水压数据
			log_sy.setTemp_value(Float.valueOf(vaule));
			// 数据单位
			log_sy.setCommsys_type(Unit);
			log_sy.setDf_id(model.getId());
			log_sy.setFrame_cnt(String.valueOf(model.getExtra().getFrameCnt()));
			log_sy.setGwid(model.getExtra().getGwid());
			log_sy.setGwip(model.getExtra().getGwip());
			log_sy.setChannel(model.getExtra().getChannel());
			log_sy.setSf(model.getExtra().getSf());
			log_sy.setMac_addr(model.getMacAddr());
			log_sy.setInput_time(ConvertHelp.getOnTime());
			log_sy.setRssi(model.getExtra().getRssi());
			// 入库水压日志表
			Injection.injection.syMapper.insertSelective(log_sy);

			// 报警处理
			boolean isalarm = false;
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, model.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				isalarm = true;
				if (devfault || devincline || devsteal) {// 故障，被撞（倾斜），被盗（有加速度）
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (devfault) {
						// 故障
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("故障报警", moduleid).getDeviceid());
					} else if (devsteal) {
						// 倾斜
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("倾斜报警", moduleid).getDeviceid());
					} else if (devincline) {
						// 被盗
						devAlarm.setAlarmstateid(
								Injection.injection.devMapper.seldevstate("被盗报警", moduleid).getDeviceid());
					}
					// 往设备报警表插入数据
					Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改设备状态
					Injection.injection.devMapper.updatedev(moduleid, devMsg.getMac(), 1, null,
							ConvertHelp.getOnTime());
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
}
