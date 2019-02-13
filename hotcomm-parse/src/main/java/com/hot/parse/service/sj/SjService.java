package com.hot.parse.service.sj;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.sj.Log_sj;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class SjService {
	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void sj_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] sj_byte = ConvertHelp.strToToHexByte(model.getData());
		// 水浸数据库日志表
		Log_sj logsj = new Log_sj();
		// 检查是否低电压报警
		boolean lowbattery = ConvertHelp
				.getIntegerSomeBit(Integer.parseInt(String.format("%02x", new Integer(sj_byte[3] & 0xff)), 16), 7) == 1
						? true : false;
		int battery7bit = ConvertHelp.setIntegerSomeBit(7,
				Integer.parseInt(String.format("%02x", new Integer(sj_byte[3] & 0xff)), 16), false);
		// 电量
		Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
		// Water1Leak
		byte[] a4 = new byte[1];
		System.arraycopy(sj_byte, 4, a4, 0, 1);
		String Water1LeakStr = ConvertHelp.StringParseByte(a4);
		double Water1LeakInt = Integer.parseInt(Water1LeakStr, 16);// 16进制转10进制
		// Water2Leak
		byte[] a5 = new byte[1];
		System.arraycopy(sj_byte, 5, a5, 0, 1);
		String Water2LeakStr = ConvertHelp.StringParseByte(a5);
		double Water2LeakInt = Integer.parseInt(Water2LeakStr, 16);// 16进制转10进制
		// set数据库日志表数据
		logsj.setVoltage(String.valueOf(battery7bit / 10));
		logsj.setVoltagestate(String.valueOf(lowbattery));
		logsj.state = Water1LeakInt == 1 ? true : false;// 水浸1漏水
		logsj.setLbflag(lowbattery);
		logsj.keepalive = Water2LeakInt == 1 ? true : false;// 水浸2漏水
		logsj.setQueueid(model.getId());
		logsj.setTemperature(0);
		logsj.setAddtime(ConvertHelp.getOnTime());
		logsj.setCommsysType(model.getExtra().getCommsysType());
		logsj.setRssi(model.getExtra().getRssi());
		logsj.setSnr(model.getExtra().getSnr());
		logsj.setFrameCnt(model.getExtra().getFrameCnt());
		logsj.setGwid(model.getExtra().getGwid());
		logsj.setGwip(model.getExtra().getGwip());
		logsj.setChannel(model.getExtra().getChannel());
		logsj.setSf(model.getExtra().getSf());
		logsj.setPub(model.getPub());
		logsj.setMacAddr(model.getMacAddr());
		logsj.setData(model.getData());
		// 入库，水浸日志表
		Injection.injection.sjMapper.insertSelective(logsj);
		if (logsj.state || logsj.keepalive || lowbattery) {
			// 判断该条数据是否为报警数据
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, logsj.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				if (logsj.state || logsj.keepalive) {
					// 漏水报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("漏水报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				} else if (lowbattery) {
					// 低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, logsj.getMacAddr(), 1, battery,
						ConvertHelp.getOnTime());
				// 推送报警数据
				try {
					RabbitMQSendMsg.sendMsg(devMsg, devAlarm, moduleid, model.getRecv());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
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
