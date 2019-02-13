package com.hot.parse.service.sydl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.sydl.Log_sydl;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class SydlService {
	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void sydl_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] sydl_byte = ConvertHelp.strToToHexByte(model.data);
		// 剩余电流
		if (model.getData().substring(2, 4).equals("09")) {
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			// 转发特斯联的数据
			// 电量
			int battery7bit = ConvertHelp.setIntegerSomeBit(7,
					Integer.parseInt(String.format("%02x", new Integer(sydl_byte[1] & 0xff)), 16), false);
			Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
			/*
			 * byte[] a1 = new byte[2]; System.arraycopy(sydl_byte, 2, a1, 0,
			 * 2); String ElectriccurrentStr = String.format("%.1f",
			 * Integer.parseInt(ConvertHelp.StringParseByte(a1), 16) * 0.1);//
			 * 16进制转10进制
			 */
			// 剩余电流前部分
			String electriccuintegerPart = String.format("%02x", new Integer(sydl_byte[2] & 0xff));
			// 剩余电流后部分
			String electriccudecimalPart = String.format("%02x", new Integer(sydl_byte[3] & 0xff));
			// 剩余电流数据
			String ElectriccurrentStr = String
					.valueOf(Integer.parseInt((electriccudecimalPart + electriccuintegerPart), 16) * 0.1);
			System.out.println(ElectriccurrentStr);
			// 剩余电流数据库日志表
			Log_sydl log_sydl = new Log_sydl();
			log_sydl.setTemp_value(Float.valueOf(ElectriccurrentStr));
			log_sydl.setDf_id(model.getId());
			log_sydl.setFrame_cnt(String.valueOf(model.getExtra().getFrameCnt()));
			log_sydl.setGwid(model.getExtra().getGwid());
			log_sydl.setGwip(model.getExtra().getGwip());
			log_sydl.setChannel(model.getExtra().getChannel());
			log_sydl.setSf(model.getExtra().getSf());
			log_sydl.setMac_addr(model.getMacAddr());
			log_sydl.setInput_time(ConvertHelp.getOnTime());
			log_sydl.setRssi(model.getExtra().getRssi());
			// 入库三相电流日志表
			Injection.injection.sydlMapper.insertSelective(log_sydl);
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, log_sydl.getMac_addr());
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
						RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
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
}
