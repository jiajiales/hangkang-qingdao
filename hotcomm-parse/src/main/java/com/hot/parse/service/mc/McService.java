package com.hot.parse.service.mc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.mc.Log_mc;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class McService {
	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void mc_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] mc_byte = ConvertHelp.strToToHexByte(model.getData());
		// 门磁日志表
		Log_mc logmc = new Log_mc();
		// 检查是否低电压报警
		boolean lowbattery = ConvertHelp
				.getIntegerSomeBit(Integer.parseInt(String.format("%02x", new Integer(mc_byte[3] & 0xff)), 16), 7) == 1
						? true : false;
		int battery7bit = ConvertHelp.setIntegerSomeBit(7,
				Integer.parseInt(String.format("%02x", new Integer(mc_byte[3] & 0xff)), 16), false);
		// 电量
		Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
		// 开关
		boolean ContactSwitchOnOffBool = Integer.parseInt(String.format("%02x", new Integer(mc_byte[4] & 0xff)),
				16) == 1 ? true : false;
		// set门磁日志表数据
		logmc.setVoltage(String.valueOf(battery7bit / 10));
		logmc.setVoltagestate(String.valueOf(lowbattery));
		logmc.setState(ContactSwitchOnOffBool);
		logmc.setLbflag(lowbattery);
		logmc.setKeepalive(true);
		logmc.setQueueid(model.getId());
		logmc.setTemperature(0);
		logmc.setAddtime(ConvertHelp.getOnTime());
		logmc.setCommsysType(model.getExtra().getCommsysType());
		logmc.setRssi(model.getExtra().getRssi());
		logmc.setSnr(model.getExtra().getSnr());
		logmc.setFrameCnt(model.getExtra().getFrameCnt());
		logmc.setGwid(model.getExtra().gwid);
		logmc.setGwip(model.getExtra().getGwip());
		logmc.setChannel(model.getExtra().getChannel());
		logmc.setSf(model.getExtra().getSf());
		logmc.setPub(model.getPub());
		logmc.setMacAddr(model.getMacAddr());
		logmc.setData(model.getData());
		// 入库，门磁日志表
		Injection.injection.mcMapper.insertSelective(logmc);
		if (ContactSwitchOnOffBool || lowbattery) {
			// 判断是否为报警数据
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, logmc.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				if (ContactSwitchOnOffBool) {
					// 开门报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("开门报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				} else if (lowbattery) {
					// 低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, logmc.getMacAddr(), 1, battery,
						ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// 修改设备状态
			Injection.injection.devMapper.updatedev(moduleid, model.getMacAddr(), null, battery,
					ConvertHelp.getOnTime());
		}
	}
}
