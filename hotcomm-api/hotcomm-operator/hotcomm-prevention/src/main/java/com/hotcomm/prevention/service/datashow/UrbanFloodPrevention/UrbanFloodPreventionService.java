package com.hotcomm.prevention.service.datashow.UrbanFloodPrevention;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hotcomm.prevention.bean.mysql.common.DeviceForDevnum;
import com.hotcomm.prevention.bean.mysql.common.DeviceInfo;
import com.hotcomm.prevention.bean.mysql.common.DeviceInsertParam;
import com.hotcomm.prevention.bean.mysql.common.params.ReciveLngLat;
import com.hotcomm.prevention.bean.mysql.common.params.UpdateStationParam;
import com.hotcomm.prevention.bean.mysql.datashow.camera.TDeviceVideo;
import com.hotcomm.prevention.bean.mysql.datashow.video.DevRelationVideoPageParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DeviceRelationVideoVo;
import com.hotcomm.prevention.bean.mysql.datashow.video.PushMsg;
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
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DeviceParam;
import com.hotcomm.prevention.exception.MyException;

public interface UrbanFloodPreventionService {

	List<FloodPreventionSituationVO> floodPreventionSituation(Integer timeType, String stnm, String stcd)
			throws MyException;

	List<FloodPreventionSituationVO> floodPreventionSituationBystcd(Integer timeType, String stcd) throws MyException;

	List<SiteInfoByTypeVO> selectSiteByType(Integer siteType) throws MyException;

	List<SelectStationInfoVO> selectStationInfo(String context, Integer queryType) throws MyException;

	List<StationAlarmRankVO> selectStationAlarmRank(Integer timeType, String stationType) throws MyException;

	StationCountVO selectStationCount() throws MyException;

	Integer updateStationThreshold(UpdateStationParam param) throws MyException;

	List<PositionVO> selectPositionVO(String type) throws MyException;

	List<StationInfo> queryStationInfo(String type, String adcd);

	List<StationDataStatisticsVO> selectStationDataStatistics(String startTime, String endTime, String stcd)
			throws MyException;

	List<LastSevenTimesDrp> selectLastSevenTimesDrp(String startTime, String endTime, String stcd) throws MyException;

	List<LastSevenTimesZ> selectLastSevenTimesZ(String startTime, String endTime, String stcd) throws MyException;

	List<StationThresholdVO> selectThreshold(String stcd) throws MyException;

	// 物资地图信息
	List<MaterialVo> selectMaterial(Integer areaid) throws MyException;

	// 物资详情
	MaterialInfoVo selectMaterialInfo(String materialnum) throws MyException;

	/**
	 * 站点范围查询
	 * 
	 * @param type
	 * @param adcd
	 * @return
	 */
	List<StationInfo> queryScopeInfo(String type, Double lng, Double lat, Double r) throws MyException;

	/**
	 * 查询视频信息
	 * 
	 * @return
	 * @throws IOException
	 */
	Map<String, String> queryVideoInfo(String devnum, Integer userid) throws MyException, IOException;

	List<Map<String, String>> queryVideoList(Integer userid) throws MyException, IOException;

	Map<String, ReciveLngLat> reciveUserLngLat(ReciveLngLat pa) throws MyException, IOException, ParseException;

	List<ReciveLngLat> UserLngLat() throws MyException, IOException, ParseException;

	List<com.hotcomm.prevention.bean.mysql.datashow.vo.DevOptionalUser> selectOpationalUserid(Integer userid)
			throws MyException;

	List<DeviceType> selectAppDeviceType(Integer moduleid) throws MyException;

	// 分页查询与选中设备关联的摄像头
	PageInfo<DeviceRelationVideoVo> getDeviceRelationVideoPage(DevRelationVideoPageParam param) throws MyException;
	
	Integer insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam) throws MyException;
	
	DeviceForDevnum selectAppDeviceForDevnum(String devnum) throws MyException;
	
	DeviceInfo selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException;

}
