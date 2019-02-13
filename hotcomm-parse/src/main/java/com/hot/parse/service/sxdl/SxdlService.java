package com.hot.parse.service.sxdl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hot.parse.config.RabbitMQSendMsg;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.entity.sxdl.Log_sxdl;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.Injection;

public class SxdlService {
	/**
	 * 
	 * @param moduleid
	 * @param model
	 */
	public void sxdl_AnalysisFun(Integer moduleid, ReceiveModel model) {
		// 分割协议数据
		byte[] sxdl_byte = ConvertHelp.strToToHexByte(model.data);
		// 判断是否为三箱电流设备
		if (model.getData().substring(2, 4).equals("07")) {
			// 接口回复特斯联
			RabbitMQSendMsg msg = new RabbitMQSendMsg();
			// 电量
			int battery7bit = ConvertHelp.setIntegerSomeBit(7,
					Integer.parseInt(String.format("%02x", new Integer(sxdl_byte[1] & 0xff)), 16), false);
			Integer battery = (int) (((battery7bit / 10) / 3.6) * 100);
			/*
			 * // A相电流数据 byte[] a1 = new byte[2]; System.arraycopy(sxdl_byte, 2,
			 * a1, 0, 2); String DL_A = String.format("%.1f",
			 * Integer.parseInt(ConvertHelp.StringParseByte(a1), 16) * 0.1);//
			 * 16进制转10进制 // B相电流数据 byte[] a2 = new byte[2];
			 * System.arraycopy(sxdl_byte, 4, a2, 0, 2); String DL_B =
			 * String.format("%.1f",
			 * Integer.parseInt(ConvertHelp.StringParseByte(a2), 16) * 0.1);//
			 * 16进制转10进制 // C相电流数据 byte[] a3 = new byte[2];
			 * System.arraycopy(sxdl_byte, 6, a3, 0, 2); String DL_C =
			 * String.format("%.1f",
			 * Integer.parseInt(ConvertHelp.StringParseByte(a3), 16) * 0.1);//
			 * 16进制转10进制
			 */
			// 电流A数据前部分
			String DL_AInt = String.format("%02x", new Integer(sxdl_byte[6] & 0xff));
			// 电流A数据后部分
			String DL_APart = String.format("%02x", new Integer(sxdl_byte[7] & 0xff));
			// 电流A数据
			String DL_A = String.valueOf(Integer.parseInt(DL_APart + DL_AInt, 16) * 0.01);
			// 电流数据前部分
			String DL_BInt = String.format("%02x", new Integer(sxdl_byte[4] & 0xff));
			// 电流B数据后部分
			String DL_BPart = String.format("%02x", new Integer(sxdl_byte[5] & 0xff));
			// 电流B数据
			String DL_B = String.valueOf(Integer.parseInt(DL_BPart + DL_BInt, 16) * 0.01);
			// 电流C数据前部分
			String DL_CInt = String.format("%02x", new Integer(sxdl_byte[2] & 0xff));
			// 电流C数据后部分
			String DL_CPart = String.format("%02x", new Integer(sxdl_byte[3] & 0xff));
			// 电流C数据
			String DL_C = String.valueOf(Integer.parseInt(DL_CPart + DL_CInt, 16) * 0.01);
			System.out.println(DL_A + "," + DL_B + "," + DL_C);
			// 三相电流数据库日志表
			Log_sxdl log_sxdl = new Log_sxdl();
			log_sxdl.setDL_A(DL_A);
			log_sxdl.setDL_B(DL_B);
			log_sxdl.setDL_C(DL_C);
			log_sxdl.setDf_id(model.getId());
			log_sxdl.setFrame_cnt(String.valueOf(model.getExtra().getFrameCnt()));
			log_sxdl.setGwid(model.getExtra().getGwid());
			log_sxdl.setGwip(model.getExtra().getGwip());
			log_sxdl.setChannel(model.getExtra().getChannel());
			log_sxdl.setSf(model.getExtra().getSf());
			log_sxdl.setMac_addr(model.getMacAddr());
			log_sxdl.setInput_time(ConvertHelp.getOnTime());
			log_sxdl.setRssi(model.getExtra().getRssi());
			// 入库三相电流日志表
			Injection.injection.sxdlMapper.insertSelective(log_sxdl);
			// 根据mac查出设备信息
			DevMsg devMsg = Injection.injection.devMapper.DevMsg(moduleid, log_sxdl.getMac_addr());
			// 是否报警
			boolean isalarm = false;
			if (devMsg != null && !devMsg.equals("")) {
				// 防止根据mac地址查询出的设备不存在,null空指针错误
				if (devMsg.getAlarmset() != 0
						&& (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C()))) {
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
						if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())) {// 判断当前值是否低于最低阈值
							// 超低报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超低报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						break;
					case 2:
						if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C())) {
							// 超高报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						}
						break;
					case 3:
						// 低于最低或高于最高
						if (Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getTopalarmvalue()) < Float.valueOf(log_sxdl.getDL_C())) {
							// 超高报警
							DevMsg devalarmstate = Injection.injection.devMapper.seldevstate("超高报警", moduleid);
							devAlarm.setAlarmstateid(devalarmstate.getDeviceid());
						} else if (Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_A())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_B())
								|| Float.valueOf(devMsg.getLessalarmvalue()) > Float.valueOf(log_sxdl.getDL_C())) {
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
