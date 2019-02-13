package com.hot.parse.service.dc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.dc.Log_dc;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class DcService {

	/**
	 * @param model
	 */
	public void dc_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 字符串转16进制字节数组
		byte[] dc_date = ConvertHelp.strToToHexByte(model.getData());
		// 地磁日志表数据
		Log_dc dcmodel = new Log_dc();
		// 取出包头判断数据信息 0xB1 代表报警信息，为 0xB2 代表工作状态信息
		String bthead = String.format("%02x", new Integer(dc_date[0] & 0xff));
		// 取出包头判断数据信息
		if (bthead.equalsIgnoreCase("ab")) {
			int FrameCountStateint = Integer.parseInt(String.format("%02x", new Integer(dc_date[1] & 0xff)), 16);
			String recvStr = "";
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(FrameCountStateint, n);
				switch (n) {
				case 0:
				case 1:
				case 2:
				case 3:
					recvStr = i + recvStr;
					break;
				}
			}
			/*
			 * 地磁车辆检测器状态/信息： 0：车位变空 1：车位被占 2：心跳上报 3：强磁干扰 4：低电压报警 5: 磁传感器检测失效（可读
			 * IC 信息） F: 磁传感器硬件损坏(不可读 IC 信息)
			 */
			dcmodel.setRecv(String.valueOf(Integer.parseInt(recvStr, 2)));// 字符串转换二进制
			// 分析yg_date[2]
			int ParkFlagBatteryLevelint = Integer.parseInt(String.format("%02x", new Integer(dc_date[2] & 0xff)), 16);
			String voltageStr = "";
			for (int n = 0; n < 8; n++) {
				int i = ConvertHelp.getIntegerSomeBit(ParkFlagBatteryLevelint, n);
				switch (n) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					voltageStr = i + voltageStr;
					break;
				case 7:
					dcmodel.carflag = i > 0 ? true : false;
					break;
				}
			}
			dcmodel.setVoltage(String.valueOf(Integer.parseInt(voltageStr, 2)));
			// 给对应数据库设备日志表字段赋值
			dcmodel.setStmcflag(true);
			dcmodel.setMcflag(true);
			dcmodel.setLbflag(false);
			dcmodel.setKeepalive(true);
			dcmodel.setQueueid(model.getId());
			dcmodel.setMagnetic("");
			dcmodel.setTemperature(0);
			dcmodel.setAddtime(ConvertHelp.getOnTime());
			dcmodel.setCommsysType(model.extra.getCommsysType());
			dcmodel.setRssi(model.getExtra().getRssi());
			dcmodel.setSnr(model.getExtra().getSnr());
			dcmodel.setFrameCnt(model.getExtra().getFrameCnt());
			dcmodel.setGwid(model.getExtra().getGwid());
			dcmodel.setGwip(model.getExtra().gwip);
			dcmodel.setChannel(model.getExtra().getChannel());
			dcmodel.setSf(model.getExtra().getSf());
			dcmodel.setPub(model.getPub());
			dcmodel.setMacAddr(model.getMacAddr());
			dcmodel.setData(model.getData());
			// 将处理完成的数据插入数据库(日志数据)
			int addResultModel = Injection.injection.dcMapper.insertSelective(dcmodel);
			if (addResultModel > 0) {
				model.IsSucess = true;
			}
			// 判断该条数据是否为报警数据
			if (dcmodel.carflag || recvStr.equals("4") || recvStr.equals("5") || recvStr.equalsIgnoreCase("f")) {
				// 在数据库设备报警表插入数据
				System.out.println("地磁报警数据");
				DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, dcmodel.getMacAddr());
				if (devMsg != null && !devMsg.equals("")) {
					// 防止根据mac地址查询出的设备不存在,null空指针错误
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (dcmodel.carflag) {
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("占用报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (recvStr.equals("4")) {// 低电压报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (recvStr.equals("5") || recvStr.equalsIgnoreCase("f")) {// 故障报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("故障报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					// 插入报警表数据
					addResultModel = Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改设备状态
					// 有车辆驶入
					addResultModel = Injection.injection.devMapper.updatedev(moduleid, dcmodel.getMacAddr(), 1,
							Integer.valueOf(dcmodel.getVoltage()), ConvertHelp.getOnTime());
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
				// 车辆驶离
				addResultModel = Injection.injection.devMapper.updatedev(moduleid, dcmodel.getMacAddr(), 0,
						Integer.valueOf(dcmodel.getVoltage()), ConvertHelp.getOnTime());
			}
		}
	}
}
