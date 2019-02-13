package com.hot.parse.service.pm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.http.util.TextUtils;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.pm.Log_pm;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class PmService {
	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void pm_AnalysisFun(Integer moduleid, ReceiveModel model) {
		byte[] pm_byte = ConvertHelp.strToToHexByte(model.getData());
		// pm日志表
		Log_pm logpm = new Log_pm();
		// 判断回传数据类型
		String bthead = String.format("%02x", new Integer(pm_byte[2] & 0xff));
		int BatteryDou = 0;
		boolean lowbattery = false;
		String pm10_01, pm25_01 = "0", pm100_01 = "0";
		String pm10_02, pm25_02 = "0", pm100_02 = "0";
		String pm03_03, pm05_03, pm10_03 = "";
		String pm25_04, pm50_04, pm100_04 = "";
		String pmo3_05, pmco_05, pmno_05 = "";
		String pmno2_06, pmso2_06, pmh2s_06 = "";
		String pmco2_07, pmnh3_07, pmnoise_07 = "0";
		String pmph_08, pmTemperaturewithPH_08, pmORP_08 = "";
		String pmNTU_09, pmTemperaturewithNTU_09, pmEC5SoildHumidtiy_09 = "";
		String pm5TESoildHumidtiy_0a, pm5TESoildTemp_0a, pmWaterLevel_0a = "";
		String pmTemperaturewithLDO_0b, pmLDODOValue_0b, pmLDOSatValue_0b = "";
		String pmTemperature_0c = "0", pmHumidity_0c = "0", pmWindSpeed_0c = "0";
		String pmWindDirection_0d, pmAtomsphere_0d = "";
		byte[] a1 = new byte[2];
		byte[] a3 = new byte[2];
		byte[] a4 = new byte[2];
		byte[] a6 = new byte[2];
		byte[] a8 = new byte[2];
		System.arraycopy(pm_byte, 1, a1, 0, 2);
		System.arraycopy(pm_byte, 3, a3, 0, 2);
		System.arraycopy(pm_byte, 4, a4, 0, 2);
		System.arraycopy(pm_byte, 6, a6, 0, 2);
		if (!bthead.equalsIgnoreCase("0D")) {
			System.arraycopy(pm_byte, 8, a8, 0, 2);
		}
		// 判断消息类型
		switch (bthead) {
		case "01":
			// 检查是否低电压报警
			lowbattery = ConvertHelp.getIntegerSomeBit(
					Integer.parseInt(String.format("%02x", new Integer(pm_byte[3] & 0xff)), 16), 7) == 1 ? true : false;
			double battery7bit = ConvertHelp.setIntegerSomeBit(7,
					Integer.parseInt(String.format("%02x", new Integer(pm_byte[3] & 0xff)), 16), false);
			BatteryDou = Integer.parseInt(String.valueOf(battery7bit), 16);// 16进制转10进制
			pm10_01 = ConvertHelp.StringParseByte(a4);
			pm25_01 = ConvertHelp.StringParseByte(a6);
			pm100_01 = ConvertHelp.StringParseByte(a8);
			break;
		case "02":
			pm10_02 = ConvertHelp.StringParseByte(a4);
			String pm25_01_2 = ConvertHelp.StringParseByte(a6);
			if (pm25_01_2.equalsIgnoreCase("FFFF")) {
				pm25_02 = "0";
			} else {
				pm25_02 = String.valueOf(Integer.parseInt(pm25_01_2, 16));
			}
			pm100_02 = ConvertHelp.StringParseByte(a8);
			break;
		case "03":
			pm03_03 = ConvertHelp.StringParseByte(a4);
			pm05_03 = ConvertHelp.StringParseByte(a6);
			pm10_03 = ConvertHelp.StringParseByte(a8);
			break;
		case "04":
			pm25_04 = ConvertHelp.StringParseByte(a4);
			pm50_04 = ConvertHelp.StringParseByte(a6);
			pm100_04 = ConvertHelp.StringParseByte(a8);
			break;
		case "05":
			pmo3_05 = ConvertHelp.StringParseByte(a4);
			pmco_05 = ConvertHelp.StringParseByte(a6);
			pmno_05 = ConvertHelp.StringParseByte(a8);
			break;
		case "06":
			pmno2_06 = ConvertHelp.StringParseByte(a4);
			pmso2_06 = ConvertHelp.StringParseByte(a6);
			pmh2s_06 = ConvertHelp.StringParseByte(a8);
			break;
		case "07":
			pmco2_07 = ConvertHelp.StringParseByte(a4);
			pmnh3_07 = ConvertHelp.StringParseByte(a6);
			String pmnoise_07_1 = ConvertHelp.StringParseByte(a8);
			if (pmnoise_07_1.equalsIgnoreCase("FFFF")) {
				pmnoise_07 = "0";
			} else {
				pmnoise_07 = String.valueOf((Integer.parseInt(pmnoise_07_1, 16)));
			}
			break;
		case "08":
			pmph_08 = ConvertHelp.StringParseByte(a4);
			pmTemperaturewithPH_08 = ConvertHelp.StringParseByte(a6);
			pmORP_08 = ConvertHelp.StringParseByte(a8);
			break;
		case "09":
			pmNTU_09 = ConvertHelp.StringParseByte(a4);
			pmTemperaturewithNTU_09 = ConvertHelp.StringParseByte(a6);
			pmEC5SoildHumidtiy_09 = ConvertHelp.StringParseByte(a8);
			break;
		case "0A":
		case "0a":
			pm5TESoildHumidtiy_0a = ConvertHelp.StringParseByte(a4);
			pm5TESoildTemp_0a = ConvertHelp.StringParseByte(a6);
			pmWaterLevel_0a = ConvertHelp.StringParseByte(a8);
			break;
		case "0B":
		case "0b":
			pmTemperaturewithLDO_0b = ConvertHelp.StringParseByte(a4);
			pmLDODOValue_0b = ConvertHelp.StringParseByte(a6);
			pmLDOSatValue_0b = ConvertHelp.StringParseByte(a8);
			break;
		case "0C":
		case "0c":
			pmTemperature_0c = ConvertHelp.StringParseByte(a4);
			if (pmTemperature_0c.equalsIgnoreCase("FFFF")) {
				pmTemperature_0c = "0";
			} else {
				pmTemperature_0c = String.valueOf(Integer.parseInt(pmTemperature_0c, 16) * 0.01);
			}
			pmHumidity_0c = ConvertHelp.StringParseByte(a6);
			if (pmHumidity_0c.equalsIgnoreCase("FFFF")) {
				pmHumidity_0c = "0";
			} else {
				pmHumidity_0c = String.valueOf(Integer.parseInt(pmHumidity_0c, 16) * 0.01);
			}
			pmWindSpeed_0c = ConvertHelp.StringParseByte(a8);
			if (pmWindSpeed_0c.equalsIgnoreCase("FFFF")) {
				pmWindSpeed_0c = "0";
			} else {
				pmWindSpeed_0c = String.valueOf(Integer.parseInt(pmWindSpeed_0c, 16) * 0.01);
			}
			break;
		case "0D":
		case "0d":
			pmWindDirection_0d = ConvertHelp.StringParseByte(a4);
			pmAtomsphere_0d = ConvertHelp.StringParseByte(a6);
			break;
		}
		// 温度
		String TemperatureStr = ConvertHelp.StringParseByte(a1);
		double TemperatureInt = Integer.parseInt(TemperatureStr, 16) * 0.01;// 16进制转10进制
		// 光照
		String illuminanceStr = ConvertHelp.StringParseByte(a3);
		double illuminanceInt = Integer.parseInt(illuminanceStr, 16) * 0.01;// 16进制转10进制
		// 写入pm日志表数据
		logpm.setVoltage(String.valueOf(BatteryDou));
		logpm.setVoltagestate(String.valueOf(lowbattery));
		logpm.setLastvalue(pm25_02);// pm2.5
		logpm.setRssi(Double.valueOf(pmnoise_07));// 噪音
		logpm.setLbflag(lowbattery);
		// 临时存储字段
		String lastvalueed = "";
		if (logpm.getLastvalue().equals(null) || TextUtils.isEmpty(logpm.getLastvalue())) {
			lastvalueed = "0";
		} else {
			lastvalueed = logpm.getLastvalue();
		}
		if (Integer.parseInt(lastvalueed) > 120) {
			logpm.setState(true);
		} else {
			if (logpm.getRssi() > 88) {
				logpm.setState(true);
			} else {
				if (logpm.lbflag) {
					logpm.setState(true);
				} else {
					logpm.setState(false);
				}
			}
		}
		logpm.setKeepalive(true);
		logpm.setQueueid(model.getId());
		logpm.setTemperature(pmTemperature_0c);// 温度
		logpm.setAddtime(ConvertHelp.getOnTime());
		logpm.setCommsysType(pmHumidity_0c);// commsysType = pmHumidity_0c;// 湿度
		logpm.setSnr(model.getExtra().getSnr());
		logpm.setFrameCnt(model.getExtra().getFrameCnt());
		logpm.setGwid(model.getExtra().getGwid());
		logpm.setGwip(model.getExtra().getGwip());
		logpm.setChannel(model.getExtra().getChannel());
		logpm.setSf(model.getExtra().getSf());
		logpm.setPub(pmWindSpeed_0c);// 风速
		logpm.setMacAddr(model.getMacAddr());
		logpm.setData(model.getData());
		// 入库pm日志表
		Injection.injection.pmMapper.insertSelective(logpm);
		// 判断该条数据是否为报警数据
		if (Integer.parseInt(pm25_02) > 100 || Double.valueOf(pmnoise_07) > 60 || lowbattery) {
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, logpm.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				DevAlarm devAlarm = new DevAlarm();
				devAlarm.setDeviceid(devMsg.getDeviceid());
				devAlarm.setMac(devMsg.getMac());
				devAlarm.setRecvtime(ConvertHelp.getOnTime());
				devAlarm.setAddtime(ConvertHelp.getOnTime());
				devAlarm.setModuleid(moduleid);
				if (Integer.valueOf(pm25_02) > 100) {
					// pm2.5超标报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("pm2.5超标报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				} else if (Integer.valueOf(pmnoise_07) > 60) {
					// 噪音超标报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("噪音超标报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				} else if (lowbattery) {
					// 电量低报警
					DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
					devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
				}
				// 往设备报警表插入数据
				Injection.injection.devMapper.insertSelective(devAlarm);
				// 修改设备状态
				Injection.injection.devMapper.updatedev(moduleid, logpm.getMacAddr(), 1, null, ConvertHelp.getOnTime());
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
			Injection.injection.devMapper.updatedev(moduleid, model.getMacAddr(), null, null, ConvertHelp.getOnTime());
		}
	}
}
