package com.hotcomm.prevention.service.datashow.UrbanFloodPrevention;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.common.DeviceForDevnum;
import com.hotcomm.prevention.bean.mysql.common.DeviceHandleTime;
import com.hotcomm.prevention.bean.mysql.common.DeviceInfo;
import com.hotcomm.prevention.bean.mysql.common.DeviceInsertParam;
import com.hotcomm.prevention.bean.mysql.common.T_devresrelation;
import com.hotcomm.prevention.bean.mysql.common.params.ReciveLngLat;
import com.hotcomm.prevention.bean.mysql.common.params.UpdateStationParam;
import com.hotcomm.prevention.bean.mysql.datashow.camera.TDeviceVideo;
import com.hotcomm.prevention.bean.mysql.datashow.video.DevRelationVideoPageParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DeviceRelationVideoVo;
import com.hotcomm.prevention.bean.mysql.datashow.video.Push;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;
import com.hotcomm.prevention.bean.mysql.datashow.vo.DevOptionalUser;
import com.hotcomm.prevention.bean.mysql.datashow.vo.DeviceType;
import com.hotcomm.prevention.bean.mysql.datashow.vo.FloodPreventionSituationVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.LastSevenTimesDrp;
import com.hotcomm.prevention.bean.mysql.datashow.vo.LastSevenTimesZ;
import com.hotcomm.prevention.bean.mysql.datashow.vo.MaterialInfoVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.MaterialVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PositionVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.SelectStationInfoVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.SiteInfoByTypeVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationAlarmRankVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationCountVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationDataStatisticsVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationInfo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationThresholdVO;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.db.mysql.common.DevresrelationMapper;
import com.hotcomm.prevention.db.mysql.datashow.UrbanFloodPrevention.UrbanFloodPreventionMapper;
import com.hotcomm.prevention.db.mysql.datashow.camera.TDeviceVideoMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.video.PushService;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.MapUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class UrbanFloodPreventionServiceImpl implements UrbanFloodPreventionService {

	@Autowired
	private UrbanFloodPreventionMapper urbanFloodPreventionMapper;
	@Autowired
	TDeviceVideoMapper DeviceVideoMapper;
	@Autowired
	private PushService pushService;

	private Map<String, ReciveLngLat> userMap = new HashMap<>();
	
	@Autowired
	private DevresrelationMapper devresrelationMapper;
	
	@Value("${spring.video.ffmpeg}")
	private String ffmpeg;

	@Override
	public List<FloodPreventionSituationVO> floodPreventionSituation(Integer timeType, String stnm, String stcd)
			throws MyException {
		String startTimeStr = null;
		String endTimeStr = null;
		if (timeType == 1) {
			// startTimeStr = "2018-06-13 22:00:00";
			// endTimeStr = "2018-06-13 23:00:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");// 设置日期格式
			startTimeStr = df.format(new Date());
			Date endTime = new Date();
			endTime.setHours(new Date().getHours() + 1);
			endTimeStr = df.format(endTime);
		} else if (timeType == 2) {
			// startTimeStr = "2018-06-13 00:00:00";
			// endTimeStr = "2018-06-14 00:00:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(new Date());
			Date endTime = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endTime);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			endTime = calendar.getTime();
			endTimeStr = df.format(endTime);
		} else if (timeType == 3) {
			// startTimeStr = "2018-06-01 00:00:00";
			// endTimeStr = "2018-07-01 00:00:00";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -30);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(calendar.getTime());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			endTimeStr = df2.format(new Date());
		} else if (timeType == 4) {
			// startTimeStr = "2017-07-01 00:00:00";
			// endTimeStr = "2018-06-30 00:00:00";
			// SimpleDateFormat df = new SimpleDateFormat("yyyy-01-01
			// 00:00:00");// 设置日期格式
			// startTimeStr = df.format(new Date());
			// SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd
			// 23:59:59");// 设置日期格式
			// endTimeStr = df2.format(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -12);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(calendar.getTime());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 0);
			endTimeStr = df2.format(calendar.getTime());
		} else if (timeType == 5) {
			// startTimeStr = "2017-07-01 00:00:00";
			// endTimeStr = "2018-12-31 00:00:00";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -11);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-01 00:00:00");// 设置日期格式
			startTimeStr = df.format(calendar.getTime());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			calendar.setTime(new Date());
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			endTimeStr = df2.format(new Date());
		}
		if (timeType != 5) {
			return urbanFloodPreventionMapper.floodPreventionSituation(startTimeStr, endTimeStr, timeType, stnm, stcd);
		} else {

			List<FloodPreventionSituationVO> vo = urbanFloodPreventionMapper.floodPreventionSituation(startTimeStr,
					endTimeStr, 4, stnm, stcd);
			FloodPreventionSituationVO test = new FloodPreventionSituationVO();
			List<FloodPreventionSituationVO> voNew = new ArrayList<>();
			double drp1 = Double.parseDouble(vo.get(0).getDRP());
			double drp2 = Double.parseDouble(vo.get(1).getDRP());
			double drp3 = Double.parseDouble(vo.get(2).getDRP());
			double drpAll = drp1 + drp2 + drp3;
			double z1 = Double.parseDouble(vo.get(0).getZ());
			double z2 = Double.parseDouble(vo.get(1).getZ());
			double z3 = Double.parseDouble(vo.get(2).getZ());
			double zAll = z1 + z2 + z3;
			System.out.println("水位1：" + z1);
			System.out.println("水位2：" + z2);
			System.out.println("水位3：" + z3);
			test.setDRP(String.valueOf(drpAll));
			test.setZ(String.valueOf(zAll / 3));
			test.setTimeInfo("季度1");
			voNew.add(test);

			FloodPreventionSituationVO test1 = new FloodPreventionSituationVO();
			double drp4 = Double.parseDouble(vo.get(3).getDRP());
			double drp5 = Double.parseDouble(vo.get(4).getDRP());
			double drp6 = Double.parseDouble(vo.get(5).getDRP());
			double drpAll1 = drp4 + drp5 + drp6;
			double z4 = Double.parseDouble(vo.get(3).getZ());
			double z5 = Double.parseDouble(vo.get(4).getZ());
			double z6 = Double.parseDouble(vo.get(5).getZ());
			double zAll1 = z4 + z5 + z6;
			test1.setDRP(String.valueOf(drpAll1));
			test1.setZ(String.valueOf(zAll1 / 3));
			test1.setTimeInfo("季度2");
			voNew.add(test1);

			FloodPreventionSituationVO test2 = new FloodPreventionSituationVO();
			double drp7 = Double.parseDouble(vo.get(6).getDRP());
			double drp8 = Double.parseDouble(vo.get(7).getDRP());
			double drp9 = Double.parseDouble(vo.get(8).getDRP());
			double drpAll2 = drp7 + drp8 + drp9;
			double z7 = Double.parseDouble(vo.get(6).getZ());
			double z8 = Double.parseDouble(vo.get(7).getZ());
			double z9 = Double.parseDouble(vo.get(8).getZ());
			double zAll2 = z7 + z8 + z9;
			test2.setDRP(String.valueOf(drpAll2));
			test2.setZ(String.valueOf(zAll2 / 3));
			test2.setTimeInfo("季度3");
			voNew.add(test2);

			FloodPreventionSituationVO test3 = new FloodPreventionSituationVO();
			double drp10 = Double.parseDouble(vo.get(9).getDRP());
			double drp11 = Double.parseDouble(vo.get(10).getDRP());
			double drp12 = Double.parseDouble(vo.get(11).getDRP());
			double drpAll3 = drp10 + drp11 + drp12;
			double z10 = Double.parseDouble(vo.get(9).getZ());
			double z11 = Double.parseDouble(vo.get(10).getZ());
			double z12 = Double.parseDouble(vo.get(11).getZ());
			double zAll3 = z10 + z11 + z12;
			test3.setDRP(String.valueOf(drpAll3));
			test3.setZ(String.valueOf(zAll3 / 3));
			test3.setTimeInfo("季度4");
			voNew.add(test3);

			return voNew;
		}
	}

	@Override
	public List<SiteInfoByTypeVO> selectSiteByType(Integer siteType) throws MyException {
		return urbanFloodPreventionMapper.selectSiteByType(siteType);
	}

	@Override
	public List<SelectStationInfoVO> selectStationInfo(String context, Integer queryType) throws MyException {
		return urbanFloodPreventionMapper.selectStationInfo(context, queryType);
	}

	@Override
	public List<StationAlarmRankVO> selectStationAlarmRank(Integer timeType, String stationType) throws MyException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat formatForEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		String startTimeStr = null;
		Date endTime = new Date();
		if (timeType == 1) {

		} else if (timeType == 2) {
			c.setTime(new Date());
			c.add(Calendar.DATE, -7);
			Date d = c.getTime();
			startTimeStr = format.format(d);
		} else if (timeType == 3) {
			c.setTime(new Date());
			c.add(Calendar.MONTH, -1);
			Date m = c.getTime();
			startTimeStr = format.format(m);
		} else if (timeType == 4) {
			c.setTime(new Date());
			c.add(Calendar.MONTH, -3);
			Date m3 = c.getTime();
			startTimeStr = format.format(m3);
		} else if (timeType == 5) {
			c.setTime(new Date());
			c.add(Calendar.YEAR, -1);
			Date y = c.getTime();
			startTimeStr = format.format(y);
		} else if (timeType == 6) {
			c.setTime(new Date());
			c.add(Calendar.YEAR, -1);
			Date y = c.getTime();
			startTimeStr = format.format(y);
		}
		return urbanFloodPreventionMapper.selectStationAlarmRank(startTimeStr, formatForEndTime.format(endTime),
				stationType);
	}

	@Override
	public StationCountVO selectStationCount() throws MyException {
		return urbanFloodPreventionMapper.selectStationCount();
	}

	@Override
	public Integer updateStationThreshold(UpdateStationParam param) throws MyException {
		if (param.getStcd() != null && param.getStcd() != "") {

			List<StationThresholdVO> vo = selectThreshold(param.getStcd());
			if (param.getPpAlarmValue() == "") {
				param.setPpAlarmValue(vo.get(0).getPP_AlarmValue());
			}
			if (param.getPpYJValue() == "") {
				param.setPpYJValue(vo.get(0).getPP_YJValue());
			}
			if (param.getZzAlarmValue() == "") {
				System.out.println(vo.get(0).getZZ_AlarmValue());
				param.setZzAlarmValue(vo.get(0).getZZ_AlarmValue());
			}
			if (param.getZzYJValue() == "") {
				param.setZzYJValue(vo.get(0).getZZ_YJValue());
			}

		}
		return urbanFloodPreventionMapper.updateStationThreshold(param);
	}

	/**
	 * 行政区域及行政区内雨量、水位状态 PP：雨量，ZZ:水位,PZ：雨量水位一体,SP：视频,WZ：物资
	 */
	@Override
	public List<PositionVO> selectPositionVO(String type) throws MyException {
		List<PositionVO> list = new ArrayList<>();
		List<PositionVO> positionVO = urbanFloodPreventionMapper.selectPositionVO();
		for (PositionVO v : positionVO) {
			if (type.contains("PP")) {
				if (v.getSttp().equals("PP") || v.getSttp().contains("PZ")) {
					list.add(v);
				}
			}
			if (type.contains("ZZ") && type.contains("PP")) {
				if (v.getSttp().equals("ZZ")) {
					list.add(v);
				}
			} else if (type.contains("ZZ")) {
				if (v.getSttp().equals("ZZ") || v.getSttp().contains("PZ")) {
					list.add(v);
				}
			}
			if (type.contains("SP")) {
				if (v.getSttp().equals("SP")) {
					list.add(v);
				}
			}
			if (type.contains("WZ")) {
				if (v.getSttp().equals("WZ")) {
					list.add(v);
				}
			}
		}
		boolean bool = false;
		List<PositionVO> array = new ArrayList<>();
		for (PositionVO positionVO2 : list) {
			if (array.size() != 0) {
				for (int i = 0; i < array.size(); i++) {
					if (array.get(i).getAreacode().equals(positionVO2.getAreacode())) {
						bool = true;
						if (array.get(i).getSwStatus() == null) {
							array.get(i).setSwStatus(positionVO2.getSwStatus());
						} else if (positionVO2.getSwStatus() != null) {
							Integer b = (array.get(i).getSwStatus() - positionVO2.getSwStatus()) > 0
									? array.get(i).getSwStatus() : positionVO2.getSwStatus();
							array.get(i).setSwStatus(b);
						}
						if (array.get(i).getYlStatus() == null) {
							array.get(i).setYlStatus(positionVO2.getYlStatus());
						} else if (positionVO2.getYlStatus() != null) {
							Integer a = (array.get(i).getYlStatus() - positionVO2.getYlStatus()) > 0
									? array.get(i).getYlStatus() : positionVO2.getYlStatus();
							array.get(i).setYlStatus(a);
						}
					}
				}
				if (!bool) {
					array.add(positionVO2);
				}
				bool = false;
			} else {
				array.add(positionVO2);
			}
		}
		return array;
	}

	/**
	 * 设备或站点信息筛选 PP：雨量，ZZ:水位,PZ：雨量水位一体,SP：视频,WZ：物资 type 可以是多种类型拼接，已逗号分割
	 */
	@Override
	public List<StationInfo> queryStationInfo(String type, String adcd) {
		List<StationInfo> array = new ArrayList<>();
		List<StationInfo> list = urbanFloodPreventionMapper.queryStationInfo(adcd);
		for (StationInfo stationInfo : list) {
			// if (type.contains("PP")) {
			// if (stationInfo.getSttp().equals("PP") ||
			// (stationInfo.getSttp().equals("PZ")&&!stationInfo.getDrp().isEmpty()))
			// {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("ZZ") && type.contains("PP")) {
			// if (stationInfo.getSttp().equals("ZZ")) {
			// array.add(stationInfo);
			// }
			// } else if (type.contains("ZZ")) {
			// if (stationInfo.getSttp().equals("ZZ") ||
			// (stationInfo.getSttp().equals("PZ")&&!stationInfo.getZ().isEmpty()))
			// {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("SP")) {
			// if (stationInfo.getSttp().equals("SP")) {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("WZ")) {
			// if (stationInfo.getSttp().equals("WZ")) {
			// array.add(stationInfo);
			// }
			// }
			if (type.contains("PP")) {
				if (stationInfo.getDrp() != null) {
					array.add(stationInfo);
				}
			}
			if (type.contains("ZZ")) {
				if (stationInfo.getZ() != null) {
					/*
					 * if (stationInfo.getSttp().equals("PZ")) { double
					 * d=Double.parseDouble(stationInfo.getLgtd())-0.01;
					 * stationInfo.setLgtd(String.valueOf(d)); }
					 */
					array.add(stationInfo);
				}
			}
			if (type.contains("SP")) {
				if (stationInfo.getSttp().equals("SP")) {
					array.add(stationInfo);
				}
			}
			if (type.contains("WZ")) {
				if (stationInfo.getSttp().equals("WZ")) {
					array.add(stationInfo);
				}
			}
		}
		return array;
	}

	@Override
	public List<StationDataStatisticsVO> selectStationDataStatistics(String startTime, String endTime, String stcd)
			throws MyException {
		return urbanFloodPreventionMapper.selectStationDataStatistics(startTime, endTime, stcd);
	}

	@Override
	public List<LastSevenTimesDrp> selectLastSevenTimesDrp(String startTime, String endTime, String stcd)
			throws MyException {
		return urbanFloodPreventionMapper.selectLastSevenTimesDrp(startTime, endTime, stcd);
	}

	@Override
	public List<LastSevenTimesZ> selectLastSevenTimesZ(String startTime, String endTime, String stcd)
			throws MyException {
		return urbanFloodPreventionMapper.selectLastSevenTimesZ(startTime, endTime, stcd);
	}

	@Override
	public List<FloodPreventionSituationVO> floodPreventionSituationBystcd(Integer timeType, String stcd)
			throws MyException {
		// TODO Auto-generated method stub
		String startTimeStr = null;
		String endTimeStr = null;
		if (timeType == 1) {
			// startTimeStr = "2018-06-13 22:00:00";
			// endTimeStr = "2018-06-13 23:00:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");// 设置日期格式
			startTimeStr = df.format(new Date());
			Date endTime = new Date();
			endTime.setHours(new Date().getHours() + 1);
			endTimeStr = df.format(endTime);
		} else if (timeType == 2) {
			// startTimeStr = "2018-06-13 00:00:00";
			// endTimeStr = "2018-06-14 00:00:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(new Date());
			Date endTime = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endTime);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			endTime = calendar.getTime();
			endTimeStr = df.format(endTime);
		} else if (timeType == 3) {
			// startTimeStr = "2018-06-01 00:00:00";
			// endTimeStr = "2018-07-01 00:00:00";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// calendar.set(Calendar.DAY_OF_MONTH,
			// calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.DATE, -30);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(calendar.getTime());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			endTimeStr = df2.format(new Date());
		} else if (timeType == 4) {
			// startTimeStr = "2018-01-01 00:00:00";
			// endTimeStr = "2018-12-31 00:00:00";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -11);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			startTimeStr = df.format(calendar.getTime());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 1);
			endTimeStr = df2.format(calendar.getTime());
		} else if (timeType == 5) {
			return floodPreventionSituation(5, "", stcd);
		}
		return urbanFloodPreventionMapper.floodPreventionSituationBystcd(startTimeStr, endTimeStr, timeType, stcd);
	}

	@Override
	public List<StationThresholdVO> selectThreshold(String stcd) throws MyException {
		return urbanFloodPreventionMapper.selectThreshold(stcd);
	}

	@Override
	public List<MaterialVo> selectMaterial(Integer areaid) throws MyException {
		return urbanFloodPreventionMapper.selectMaterial(areaid);
	}

	@Override
	public MaterialInfoVo selectMaterialInfo(String materialnum) throws MyException {
		HashMap<String, String> msgList = new HashMap<>();
		MaterialInfoVo selectMaterialInfo = urbanFloodPreventionMapper.selectMaterialInfo(materialnum);
		if (selectMaterialInfo == null) {
			return null;
		}
		String msg = selectMaterialInfo.getMsg();
		String list[] = msg.split(",");
		for (String str : list) {
			String strmsg[] = str.split(":");
			msgList.put(strmsg[0], strmsg[1]);
		}
		selectMaterialInfo.setMsgList(msgList);
		return selectMaterialInfo;
	}

	/**
	 * 范围查询附近的站点或设备信息
	 */
	@Override
	public List<StationInfo> queryScopeInfo(String type, Double lng, Double lat, Double r) throws MyException {
		List<StationInfo> array = new ArrayList<>();
		List<StationInfo> Scope = new ArrayList<>();
		List<StationInfo> list = urbanFloodPreventionMapper.queryScopeInfo();
		for (StationInfo stationInfo : list) {
			// if (type.contains("PP")) {
			// if (stationInfo.getSttp().equals("PP")) {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("ZZ")) {
			// if (stationInfo.getSttp().equals("ZZ")) {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("SP")) {
			// if (stationInfo.getSttp().equals("SP")) {
			// array.add(stationInfo);
			// }
			// }
			// if (type.contains("WZ")) {
			// if (stationInfo.getSttp().equals("WZ")) {
			// array.add(stationInfo);
			// }
			// }
			if (type.contains("PP")) {
				if (stationInfo.getDrp() != null) {
					array.add(stationInfo);
				}
			}
			if (type.contains("ZZ")) {
				if (stationInfo.getZ() != null) {
					/*
					 * if (stationInfo.getSttp().equals("PZ")) { double
					 * d=Double.parseDouble(stationInfo.getLgtd())-0.01;
					 * stationInfo.setLgtd(String.valueOf(d)); }
					 */
					array.add(stationInfo);
				}
			}
			if (type.contains("SP")) {
				if (stationInfo.getSttp().equals("SP")) {
					array.add(stationInfo);
				}
			}
			if (type.contains("WZ")) {
				if (stationInfo.getSttp().equals("WZ")) {
					array.add(stationInfo);
				}
			}
		}
		for (StationInfo info : array) {
			if (info.getLttd() != null && info.getLgtd() != null) {
				double d = MapUtils.GetDistance(lat, lng, Double.parseDouble(info.getLttd()),
						Double.parseDouble(info.getLgtd()));
				if (d < r) {
					Scope.add(info);
				}
			}
		}
		return Scope;
	}

	/**
	 * 通过设备编号调取视频信息
	 * 
	 * @throws IOException
	 */
	@Override
	public Map<String, String> queryVideoInfo(String devnum, Integer userid) throws MyException, IOException {
		Example example = new Example(TDeviceVideo.class);
		example.createCriteria().andEqualTo("devnum", devnum).andEqualTo("isenable", true);
		List<TDeviceVideo> list = DeviceVideoMapper.selectByExample(example);
		if (list.size() == 0) {
			return null;
		}
		// 取到摄像头ip等信息
		Push push = new Push();
		push.setUserid(userid.toString());
		push.setUsername(list.get(0).getUsername());
		push.setPassword(list.get(0).getPassword());
		push.setNvrip(list.get(0).getNvrip());
		push.setNvrchannel(list.get(0).getNvrchannel());
		push.setIp(list.get(0).getIp());
		push.setProd(list.get(0).getPort());
		push.setFfmpeg(ffmpeg);
		PushMsg p = pushService.push(push);
		Map<String, String> m = new HashMap<String, String>();
		m.put("url", p.getOutput());
		return m;
	}

	@Override
	public List<Map<String, String>> queryVideoList(Integer userid) throws MyException, IOException {
//		List<Map<String, String>> test=urbanFloodPreventionMapper.queryVideoList();
//		for (int i = 0; i < test.size(); i++) {
//			Map<String, String> info = test.get(i);
//			String videoNum=info.get("devnum");
//			Map<String, String> vInfo = queryVideoInfo(videoNum, userid);
//			if (vInfo.size()==0) {
//				System.out.println("啥也没有");
//			}
//		}
		return urbanFloodPreventionMapper.queryVideoList();
	}

	@Override
	public Map<String, ReciveLngLat> reciveUserLngLat(ReciveLngLat pa) throws MyException, IOException, ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = df.parse(df.format(new Date()));
		Date date = df.parse(pa.getRecivetime());
		long l = now.getTime() - date.getTime(); // 获取时间差
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		String dat2 = day + "";
		String hour2 = hour + "";
		String min2 = min + "";
		String s2 = s + "";

		Integer dat3 = Integer.parseInt(dat2.replace("-", ""));
		Integer hour3 = Integer.parseInt(hour2.replace("-", ""));
		Integer min3 = Integer.parseInt(min2.replace("-", ""));
		Integer s3 = Integer.parseInt(s2.replace("-", ""));

		if (dat3 > 0 || hour3 > 0 || min3 > 5 || s3 > 300) {
			pa.setOnlinestate(0);
		} else {
			pa.setOnlinestate(1);
		}

		if (dat3 > 3) {
			userMap.remove(pa.getUserid());
		}
		ReciveLngLat test = urbanFloodPreventionMapper.getuserinfo(pa.getUserid());
		pa.setRealname(test.getRealname());
		pa.setTelephone(test.getTelephone());
		userMap.put(pa.getUserid().toString(), pa);

		System.out.println(userMap);
		return userMap;

	}

	@Override
	public List<ReciveLngLat> UserLngLat() throws MyException, IOException, ParseException {
		List<ReciveLngLat> list = new ArrayList<>();
		for (Entry<String, ReciveLngLat> vo : userMap.entrySet()) {
			vo.getKey();
			vo.getValue();
//			System.out.println(vo.getKey() + "  " + vo.getValue());
			ReciveLngLat test = userMap.get(vo.getKey());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = df.parse(df.format(new Date()));
			Date date = df.parse(test.getRecivetime());
			long l = now.getTime() - date.getTime(); // 获取时间差
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			String dat2 = day + "";
			String hour2 = hour + "";
			String min2 = min + "";
			String s2 = s + "";

			Integer dat3 = Integer.parseInt(dat2.replace("-", ""));
			Integer hour3 = Integer.parseInt(hour2.replace("-", ""));
			Integer min3 = Integer.parseInt(min2.replace("-", ""));
			Integer s3 = Integer.parseInt(s2.replace("-", ""));

			if (dat3 > 0 || hour3 > 0 || min3 > 5 || s3 > 300) {
				test.setOnlinestate(0);
			} else {
				test.setOnlinestate(1);
			}

			userMap.put(test.getUserid().toString(), test);

			list.add(test);

		}
		return list;
	}
	
	@Override
	public List<DevOptionalUser> selectOpationalUserid(Integer userid) throws MyException {
		List<DevOptionalUser> selectOpationalUser = urbanFloodPreventionMapper.selectDevOptionalUser(userid);
		return selectOpationalUser;
	}

	@Override
	public List<DeviceType> selectAppDeviceType(Integer moduleid) throws MyException {
		return urbanFloodPreventionMapper.selectAppDeviceType(moduleid);
	}

	@Override
	public PageInfo<DeviceRelationVideoVo> getDeviceRelationVideoPage(DevRelationVideoPageParam param)
			throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<DeviceRelationVideoVo> page = urbanFloodPreventionMapper.getDeviceRelationVideoPage(param);
		List<DeviceRelationVideoVo> list = ConverUtil.converPage(page, DeviceRelationVideoVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam) throws MyException {

		DeviceForDevnum devnum = this.selectAppDeviceForDevnum(deviceInsertParam.getDevnum());
		if (devnum != null) {
			getresult(0, "该设备已存在,请勿重复添加");
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		deviceInsertParam.setAddtime(sdf.format(d));
		Integer i = urbanFloodPreventionMapper.insertAPPDevice(moduleid, userid, deviceInsertParam);
		getresult(i, "添加设备失败");
		Integer devid = deviceInsertParam.getInsertId();
		if (deviceInsertParam.getPictureUrl() != null) {
			for (String picture : deviceInsertParam.getPictureUrl()) {
				T_devresrelation record = new T_devresrelation();
				record.setModuleid(moduleid);
				record.setAddtime(sdf.format(d));
				record.setDevid(deviceInsertParam.getInsertId());
				record.setDevid(devid);
				record.setUrl(picture);
				getresult(devresrelationMapper.insertSelective(record), "添加图片失败");
			}
		}
		i = urbanFloodPreventionMapper.insertDevReGroup(devid, deviceInsertParam.getGroupid(), moduleid,
				deviceInsertParam.getAddtime(), userid);
		getresult(i, "添加设备与组的关联失败");
		if (deviceInsertParam.getVideoid() != null) {
			for (Integer videoid : deviceInsertParam.getVideoid()) {
				i = urbanFloodPreventionMapper.insertDevReVideo(devid, videoid, moduleid);
				getresult(i, "添加摄像头失败");
			}
		}
		if (deviceInsertParam.getItempicid() != null) {
			i = urbanFloodPreventionMapper.insertDevItemPic(deviceInsertParam.getItempicid(), devid, moduleid,
					deviceInsertParam.getAddtime());
		}
		return i;
	}
	
	@Override
	public DeviceForDevnum selectAppDeviceForDevnum(String devnum) throws MyException {
		// TODO Auto-generated method stub
		urbanFloodPreventionMapper.dropTemporaryTable();
		urbanFloodPreventionMapper.createTemporaryTable();
		DeviceForDevnum deviceForDevnum = urbanFloodPreventionMapper.selectAppDeviceforDevnum(devnum);
		if (deviceForDevnum == null) {
			deviceForDevnum = null;
		} else {
			deviceForDevnum.setDevtype("智能" + deviceForDevnum.getName());
		}
		return deviceForDevnum;
	}
	
	/**
	 * 判断是否成功
	 * 
	 * @param i
	 * @param msg
	 * @return
	 * @throws MyException
	 */
	private Integer getresult(Integer i, String msg) throws MyException {
		if (i <= 0) {
			throw new MyException("0", msg);
		} else {
			return i;
		}
	}

	@Override
	public DeviceInfo selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException {
		DeviceInfo deviceInfo = urbanFloodPreventionMapper.AppDeviceInfo(deviceid, moduleid);
		if (deviceInfo == null) {
			return null;
		}
		List<DeviceHandleTime> handleTime = urbanFloodPreventionMapper.getDeviceAlarmHandleTime(deviceid, moduleid);
		if (handleTime.size() == 0) {
			handleTime = null;
		}
		deviceInfo.setHandleTime(handleTime);
		return deviceInfo;
	}

}
