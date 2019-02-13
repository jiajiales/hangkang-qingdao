package com.hot.parse.service.ljt;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.ljt.Log_ljt;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class LjtService {

	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void ljt_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 字符串转16进制字节数组
		byte[] ljt_date = ConvertHelp.strToToHexByte(model.getData());
		// 入库log_ljt数据库
		Log_ljt logljt = new Log_ljt();
		// 取出包头判断数据信息判断是否为垃圾桶数据
		String bthead = String.format("%02x", new Integer(ljt_date[0] & 0xff));
		// 判断包头0A
		if (bthead.equalsIgnoreCase("0a")) {
			byte[] trashHighbyte = new byte[2];
			// 将ljt_date第三第四位赋值给trashHighbyte解析，传感器采集值垃圾高度
			System.arraycopy(ljt_date, 2, trashHighbyte, 0, 2);
			String trashHighStr = "";
			for (int n = 0; n < 2; n++) {
				// 去除0X
				trashHighStr += String.format("%02x", new Integer(trashHighbyte[n] & 0xff));
			}
			// 将有效数据转16进制
			logljt.setLastvalue(String.valueOf(Integer.parseInt(trashHighStr, 16)));
			// 取出第五位数据电压值，转十进制
			String btvoltageStr = String.format("%02x", new Integer(ljt_date[4] & 0xff));
			Integer battery = (int) (((Integer.parseInt(btvoltageStr, 16) / 10) / 3.6) * 100);
			// 给相应数据库设备日志表字段赋值
			logljt.setVoltage(btvoltageStr);
			logljt.setData(model.getData());
			logljt.setQueueid(model.getId());
			logljt.setVoltage(String.valueOf(Integer.parseInt(btvoltageStr, 16) / 10));
			logljt.setTemperature(0);
			logljt.setLbflag(false);
			logljt.setAddtime(ConvertHelp.getOnTime());
			logljt.setRecv(model.getRecv());
			logljt.setCommsysType(model.getExtra().getCommsysType());
			logljt.setRssi(model.getExtra().getRssi());
			logljt.setSnr(model.getExtra().getSnr());
			logljt.setFrameCnt(model.getExtra().getFrameCnt());
			logljt.setGwid(model.getExtra().getGwid());
			logljt.setGwip(model.getExtra().getGwip());
			logljt.setChannel(model.getExtra().getChannel());
			logljt.setSf(model.getExtra().getSf());
			logljt.setPub(model.getPub());
			logljt.setFport(100);
			logljt.setMacAddr(model.getMacAddr());
			logljt.setData(model.getData());
			// 将处理完成的数据插入数据库(日志数据)
			int addResultModel = Injection.injection.ljtMapper.insertSelective(logljt);
			if (addResultModel > 0) {
				model.IsSucess = true;
			}
			// 根据mac查出该设备信息，
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, model.getMacAddr());
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				if (devMsg.getHeight() - Double.parseDouble(logljt.getLastvalue()) > devMsg.getAlarmheight()
						|| battery < 20) {
					// 判断是否达到垃圾桶报警高度或者有没有达到低电压报警程度(低于百分之20的电量报警)
					System.out.println("垃圾桶报警数据");
					// 垃圾桶高度超出报警
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (battery < 20) {
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else {
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("溢出报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					// 在数据库设备报警表插入报警数据
					addResultModel = Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改垃圾桶设备状态，由于数据库字段问题报警统一修改为报警状态1
					addResultModel = Injection.injection.devMapper.updatedev(moduleid, logljt.getMacAddr(), 1, battery,
							ConvertHelp.getOnTime());
					if (addResultModel > 0) {
						model.IsSucess = true;
					}
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
			}
		} else {
			// 非垃圾桶数据
			System.out.println("非垃圾桶数据!!!");
		}
	}
}
