package com.hot.parse.service.hw;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.hw.Log_hw;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class HwService {
	public void hw_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割data数据
		byte[] hw_byte = ConvertHelp.strToToHexByte(model.data);
		// 数据库红外日志表
		Log_hw loghw = new Log_hw();
		// 检查是否低电压报警
		boolean lowbattery = ConvertHelp
				.getIntegerSomeBit(Integer.parseInt(String.format("%02x", new Integer(hw_byte[3] & 0xff)), 16), 7) == 1
						? true : false;
		int battery7bit = ConvertHelp.setIntegerSomeBit(7,
				Integer.parseInt(String.format("%02x", new Integer(hw_byte[3] & 0xff)), 16), false);
		// 电量
		Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
		// 温度
		byte[] a4 = new byte[2];
		System.arraycopy(hw_byte, 4, a4, 0, 2);
		double TemperatureInt = Integer.parseInt(ConvertHelp.StringParseByte(a4), 16) * 0.01;// 16进制转10进制
		// 光照
		byte[] a6 = new byte[2];
		System.arraycopy(hw_byte, 6, a6, 0, 2);
		double illuminanceInt = Integer.parseInt(ConvertHelp.StringParseByte(a6), 16);// 16进制转10进制
		// 是否有侵入
		boolean OccupyBool = Integer.parseInt(String.format("%02x", new Integer(hw_byte[8] & 0xff)), 16) == 1 ? true
				: false;
		// 写入数据库红外日志表数据
		loghw.setVoltage(String.valueOf(battery));
		loghw.setState(OccupyBool);
		loghw.setLbflag(lowbattery);
		loghw.setKeepalive(true);
		loghw.setQueueid(model.getId());
		loghw.setTemperature(Integer.parseInt(new java.text.DecimalFormat("0").format(TemperatureInt)));
		loghw.setAddtime(ConvertHelp.getOnTime());
		loghw.setCommsysType(model.getExtra().getCommsysType());
		loghw.setRssi(model.getExtra().getRssi());
		loghw.setSnr(model.getExtra().getSnr());
		loghw.setFrameCnt(model.getExtra().getFrameCnt());
		loghw.setGwid(model.getExtra().getGwid());
		loghw.setGwip(model.getExtra().getGwip());
		loghw.setChannel(model.getExtra().getChannel());
		loghw.setSf(model.getExtra().getSf());
		loghw.setPub(model.getPub());
		loghw.setRecv(model.getRecv());
		loghw.setMacAddr(model.getMacAddr());
		loghw.setData(model.getData());
		// 入库pm日志表
		Injection.injection.hwMapper.insertSelective(loghw);
		if (OccupyBool || lowbattery) {// 判断是否为报警数据
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, loghw.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				if (OccupyBool) {
					// 侵入报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("入侵报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				} else if (lowbattery) {
					// 低电压报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, loghw.getMacAddr(), 1, battery,
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