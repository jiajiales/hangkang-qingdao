package com.hot.parse.service.jg;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.jg.Log_jg;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class JgService {

	public void jg_AnalysisFun(Integer moduleid, ReceiveModel model) {
		byte[] jg_date = ConvertHelp.strToToHexByte(model.getData());
		Log_jg logjg = new Log_jg();
		if (jg_date.length == 9) {
			// 取第一段进行分析
			byte[] status = new byte[1];
			System.arraycopy(jg_date, 0, status, 0, 1);
			String stateStr = "";
			for (int n = 0; n < 1; n++) {
				stateStr += String.format("%02x", new Integer(status[n] & 0xff));
			}
			int state = Integer.parseInt(stateStr, 16);
			for (int n = 0; n < 8; n++) {
				// 二進制裝換
				int i = ConvertHelp.getIntegerSomeBit(state, n);
				switch (n) {
				case 3:
					logjg.voltagestate = i > 0 ? "true" : "false";
					break;
				case 2:
					logjg.calibration = i > 0 ? true : false;
					break;
				case 1:
					logjg.keepalive = i > 0 ? true : false;
					break;
				case 0:
					logjg.state = i > 0 ? true : false;
					break;
				}
			}
			// 取第二段进行分析.电量
			byte[] btvoltage = new byte[1];
			System.arraycopy(jg_date, 2, btvoltage, 0, 1);
			String btvoltageStr = "";
			for (int n = 0; n < 1; n++) {
				btvoltageStr += String.format("%02x", new Integer(btvoltage[n] & 0xff));
			}
			// battery电量
			Integer battery = (int) (((Integer.parseInt(btvoltageStr, 16) / 10) / 3.6) * 100);
			// 取第三段进行分析
			byte[] gsensage = new byte[6];
			System.arraycopy(jg_date, 2, gsensage, 0, 6);
			String gsensageStr = "";
			for (int n = 0; n < 6; n++) {
				gsensageStr += String.format("%02x", new Integer(gsensage[n] & 0xff));
			}
			// 给相应数据库设备日志表字段赋值
			logjg.setGsensor(gsensageStr);
			logjg.setQueueid(model.getId());
			logjg.setVoltage(String.valueOf(Integer.parseInt(btvoltageStr, 16) / 10));
			logjg.setTemperature(Integer.parseInt(String.format("%02x", new Integer(jg_date[2] & 0xff)), 16));
			logjg.lbflag = logjg.voltagestate == "true" ? true : false;
			logjg.setAddtime(ConvertHelp.getOnTime());
			logjg.setRecv(model.getRecv());
			logjg.setCommsysType(model.extra.getCommsysType());
			logjg.setRssi(model.getExtra().getRssi());
			logjg.setSnr(model.getExtra().getSnr());
			logjg.setFrameCnt(model.getExtra().getFrameCnt());
			logjg.setGwid(model.getExtra().getGwid());
			logjg.setGwip(model.getExtra().getGwip());
			logjg.setChannel(model.getExtra().getChannel());
			logjg.setSf(model.getExtra().getSf());
			logjg.setPub(model.getPub());
			logjg.setFport(100);
			logjg.setMacAddr(model.getMacAddr());
			logjg.setData(model.getData());
			// 将处理完成的数据插入数据库(日志数据)
			int addResultModel = Injection.injection.jgMapper.insertSelective(logjg);
			if (addResultModel > 0) {
				model.IsSucess = true;
			}
			// 判断该条数据是否为报警数据
			if (logjg.state || logjg.lbflag) {
				System.out.println("井盖报警数据");
				DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, logjg.getMacAddr());
				if (devMsg != null && !devMsg.equals("")) {
					// 防止根据mac地址查询出的设备不存在,null空指针错误
					DevAlarm devAlarm = new DevAlarm();
					devAlarm.setDeviceid(devMsg.getDeviceid());
					devAlarm.setMac(devMsg.getMac());
					devAlarm.setRecvtime(ConvertHelp.getOnTime());
					devAlarm.setAddtime(ConvertHelp.getOnTime());
					devAlarm.setModuleid(moduleid);
					if (logjg.state) {
						// 报警类型：开盖报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("开盖报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					} else if (logjg.lbflag) {
						// 报警类型：低电压报警
						DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("低电压报警", moduleid);
						devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
					}
					// 往设备报警表插入数据
					addResultModel = Injection.injection.devMapper.insertSelective(devAlarm);
					// 修改井盖设备状态，由于数据库字段问题报警统一修改为报警状态1
					addResultModel = Injection.injection.devMapper.updatedev(moduleid, logjg.getMacAddr(), 1, battery,
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
		} else {
			System.out.println("格式错误，包长不对！！！");
		}
	}

}
