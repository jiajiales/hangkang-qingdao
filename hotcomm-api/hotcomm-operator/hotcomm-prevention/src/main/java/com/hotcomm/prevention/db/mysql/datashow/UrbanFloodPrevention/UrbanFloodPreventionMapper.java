package com.hotcomm.prevention.db.mysql.datashow.UrbanFloodPrevention;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.DeviceForDevnum;
import com.hotcomm.prevention.bean.mysql.common.DeviceHandleTime;
import com.hotcomm.prevention.bean.mysql.common.DeviceInfo;
import com.hotcomm.prevention.bean.mysql.common.DeviceInsertParam;
import com.hotcomm.prevention.bean.mysql.common.entity.TStStbprpB;
import com.hotcomm.prevention.bean.mysql.common.params.ReciveLngLat;
import com.hotcomm.prevention.bean.mysql.common.params.UpdateStationParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DevRelationVideoPageParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DeviceRelationVideoVo;
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
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DeviceParam;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface UrbanFloodPreventionMapper extends Mapper<TStStbprpB> {

	// 查询防汛态势
	List<FloodPreventionSituationVO> floodPreventionSituation(@Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("timeType") Integer timeType, @Param("stnm") String stnm,
			@Param("stcd") String stcd) throws MyException;

	List<FloodPreventionSituationVO> floodPreventionSituationBystcd(@Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("timeType") Integer timeType, @Param("stcd") String stcd)
					throws MyException;

	// 根据测站类型查询站点列表
	List<SiteInfoByTypeVO> selectSiteByType(@Param("siteType") Integer siteType) throws MyException;

	// 设备栏快捷筛选，行政区域以及状态
	List<PositionVO> selectPositionVO();

	// 防汛监测站点
	List<SelectStationInfoVO> selectStationInfo(@Param("context") String context, @Param("queryType") Integer queryType)
			throws MyException;

	// 站点报警排名
	List<StationAlarmRankVO> selectStationAlarmRank(@Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("stationType") String stationType) throws MyException;

	// 站点统计
	StationCountVO selectStationCount() throws MyException;

	// 站点更新阈值
	Integer updateStationThreshold(UpdateStationParam param) throws MyException;

	// 所有的设备信息（中间地图筛选）
	List<StationInfo> queryStationInfo(@Param("adcd") String adcd);

	// 所有的设备信息（中间地图筛选）
	List<StationInfo> queryStationInfo();

	// 站点更多趋势
	List<StationDataStatisticsVO> selectStationDataStatistics(@Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("stcd") String stcd) throws MyException;

	// 查询某雨量站最新七条检测数据
	List<LastSevenTimesDrp> selectLastSevenTimesDrp(@Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("stcd") String stcd) throws MyException;

	// 查询某水位站最新七条检测数据
	List<LastSevenTimesZ> selectLastSevenTimesZ(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("stcd") String stcd) throws MyException;

	// 查询某站点阈值
	List<StationThresholdVO> selectThreshold(@Param("stcd") String stcd) throws MyException;

	// 物资地图信息
	List<MaterialVo> selectMaterial(@Param("areaid") Integer areaid) throws MyException;

	// 物资详情
	MaterialInfoVo selectMaterialInfo(@Param("materialnum") String materialnum) throws MyException;

	// 站点设备信息查询
	List<StationInfo> queryScopeInfo();

	List<Map<String, String>> queryVideoList();

	@Select("SELECT u.`contacts` as realname,u.`telephone` FROM t_user u WHERE u.`id`=#{userid}")
	ReciveLngLat getuserinfo(@Param("userid") Integer userid);

	List<DevOptionalUser> selectDevOptionalUser(@Param("userid") Integer userid) throws MyException;

	List<DeviceType> selectAppDeviceType(@Param("moduleid") Integer moduleid) throws MyException;

	// 分页查询与选中设备关联的摄像头列表
	Page<DeviceRelationVideoVo> getDeviceRelationVideoPage(@Param("param") DevRelationVideoPageParam param);
	
	Integer insertAPPDevice(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("deviceInsertParam") DeviceInsertParam deviceInsertParam) throws MyException;
	
	/**
	 * 创建临时表(App)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer createTemporaryTable() throws MyException;

	/**
	 * 删除临时表(App)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer dropTemporaryTable() throws MyException;
	
	DeviceForDevnum selectAppDeviceforDevnum(@Param("devnum") String devnum) throws MyException;
	
	Integer insertDevReGroup(@Param("devid") Integer devid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime, @Param("userid") Integer userid)
					throws MyException;
	
	Integer insertDevItemPic(@Param("itempicid") Integer itempicid, @Param("devid") Integer devid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime) throws MyException;
	
	Integer insertDevReVideo(@Param("devid") Integer devid, @Param("videoid") Integer videoid,
			@Param("moduleid") Integer moduleid) throws MyException;
	
	DeviceInfo AppDeviceInfo(@Param("deviceid") Integer deviceid, @Param("moduleid") Integer moduleid)
			throws MyException;
	
	List<DeviceHandleTime> getDeviceAlarmHandleTime(@Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid) throws MyException;
}
