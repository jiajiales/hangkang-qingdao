package com.hotcomm.prevention.controller.datashow.UrbanFloodPrevention;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.DeviceForDevnum;
import com.hotcomm.prevention.bean.mysql.common.DeviceInsertParam;
import com.hotcomm.prevention.bean.mysql.common.params.ReciveLngLat;
import com.hotcomm.prevention.bean.mysql.common.params.UpdateStationParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DevRelationVideoPageParam;
import com.hotcomm.prevention.bean.mysql.datashow.video.DeviceRelationVideoVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PositionVO;
import com.hotcomm.prevention.bean.mysql.datashow.vo.StationInfo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.UrbanFloodPrevention.UrbanFloodPreventionService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class UrbanFloodPreventionController {

	@Autowired
	UrbanFloodPreventionService urbanFloodPreventionService;

	/**
	 * 防汛态势
	 * 
	 * @param timeType
	 * @param queryType
	 * @param stcd
	 * @return
	 */
	@PostMapping("/analysis/selectUrbanFloodPrevention")
	public ApiResult getUsers(Integer timeType, String stnm, String stcd) {
		return ApiResult.resultInfo("1", "获取成功",
				urbanFloodPreventionService.floodPreventionSituation(timeType, stnm, stcd));
	}

	/**
	 * 防汛态势bySTCD
	 * 
	 * @param timeType
	 * @param queryType
	 * @param stcd
	 * @return
	 */
	@PostMapping("/analysis/getUsersBystcd")
	public ApiResult getUsersBystcd(Integer timeType, String stcd) {
		return ApiResult.resultInfo("1", "获取成功",
				urbanFloodPreventionService.floodPreventionSituationBystcd(timeType, stcd));
	}

	/**
	 * 站点列表
	 * 
	 * @param siteType
	 * @return
	 */
	@PostMapping("/analysis/selectSiteByType")
	public ApiResult selectSiteByType(Integer siteType) {
		return ApiResult.resultInfo("1", "获取成功", urbanFloodPreventionService.selectSiteByType(siteType));
	}

	/**
	 * 防汛监测站点
	 * 
	 * @param context
	 * @return
	 */
	@PostMapping("/analysis/selectStationInfo")
	public ApiResult selectStationInfo(String context, Integer queryType) {
		return ApiResult.resultInfo("1", "获取成功", urbanFloodPreventionService.selectStationInfo(context, queryType));
	}

	/**
	 * 站点报警排名
	 * 
	 * @param timeType
	 * @param stationType
	 * @return
	 */
	@PostMapping("/analysis/selectStationAlarmRank")
	public ApiResult selectStationAlarmRank(Integer timeType, String stationType) {
		return ApiResult.resultInfo("1", "获取成功",
				urbanFloodPreventionService.selectStationAlarmRank(timeType, stationType));
	}

	/**
	 * 站点统计
	 * 
	 * @return
	 */
	@PostMapping("/analysis/selectStationCount")
	public ApiResult selectStationCount() {
		return ApiResult.resultInfo("1", "获取成功", urbanFloodPreventionService.selectStationCount());
	}

	/**
	 * 站点更新边界值
	 * 
	 * @param UpdateStationParam
	 * @return
	 */
	@PostMapping("/analysis/updateStationThreshold")
	public ApiResult updateStationThreshold(UpdateStationParam param) {
		Integer judge = urbanFloodPreventionService.updateStationThreshold(param);
		String judgeText;
		if (judge > 0) {
			judgeText = "修改成功";
		} else if (judge == -1) {
			judgeText = "修改失败,原因：预警值不能大于或等于报警值。";
		} else {
			judgeText = "修改失败";
		}
		return ApiResult.resultInfo("1", judgeText, judge);
	}

	/**
	 * 行政区域及行政区内雨量、水位状态 PP：雨量，ZZ:水位,PZ：雨量水位一体,SP：视频,WZ：物资
	 * 
	 * @param type
	 *            可以是多个字符拼接以逗号分割
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/analysis/selectPositionVO")
	public ApiResult selectPositionVO(String type) throws MyException {
		List<PositionVO> list = urbanFloodPreventionService.selectPositionVO(type);
		return ApiResult.resultInfo("1", "请求成功", list);
	}

	/**
	 * 设备或站点信息筛选（中间地图） PP：雨量，ZZ:水位,SP：视频,WZ：物资
	 * 
	 * @param type
	 *            type 可以是多个字符拼接以逗号分割
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/analysis/queryMapStationInfo")
	public ApiResult queryMapStationInfo(String type, String adcd) throws MyException {
		List<StationInfo> list = urbanFloodPreventionService.queryStationInfo(type, adcd);
		return ApiResult.resultInfo("1", "请求成功", list);

	}

	/* 站点更多趋势 */
	@PostMapping("/analysis/selectStationDataStatistics")
	public ApiResult selectStationDataStatistics(String startTime, String endTime, String stcd) throws MyException {
		return ApiResult.resultInfo("1", "请求成功",
				urbanFloodPreventionService.selectStationDataStatistics(startTime, endTime, stcd));
	}

	/* 查询某雨量站最新七条检测数据 */
	@PostMapping("/analysis/selectLastSevenTimesDrp")
	public ApiResult selectLastSevenTimesDrp(String startTime, String endTime, String stcd) throws MyException {
		return ApiResult.resultInfo("1", "请求成功",
				urbanFloodPreventionService.selectLastSevenTimesDrp(startTime, endTime, stcd));
	}

	/* 查询某水位站最新七条检测数据 */
	@PostMapping("/analysis/selectLastSevenTimesZ")
	public ApiResult selectLastSevenTimesZ(String startTime, String endTime, String stcd) throws MyException {
		return ApiResult.resultInfo("1", "请求成功",
				urbanFloodPreventionService.selectLastSevenTimesZ(startTime, endTime, stcd));
	}

	/* 查询某站点阈值 */
	@PostMapping("/analysis/selectThreshold")
	public ApiResult selectThreshold(String stcd) throws MyException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.selectThreshold(stcd));
	}

	/* 物资资源地图信息 */
	@PostMapping("/analysis/selectMaterial")
	public ApiResult selectMaterial(Integer areaid) throws MyException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.selectMaterial(areaid));
	}

	/* 物资资源详情 */
	@PostMapping("/analysis/selectMaterialInfo")
	public ApiResult selectMaterialInfo(String materialnum) throws MyException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.selectMaterialInfo(materialnum));
	}

	/**
	 * 范围查询站点设备信息
	 * 
	 * @param type
	 *            查询类型
	 * @param lng
	 *            经度
	 * @param lat
	 *            维度
	 * @param r
	 *            半径 m
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/analysis/queryScopeInfo")
	public ApiResult queryScopeInfo(String type, Double lng, Double lat, Double r) throws MyException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.queryScopeInfo(type, lng, lat, r));
	}

	/**
	 * 视频调用（通过指定的设备编号获取视频链接）
	 * 
	 * @param devnum
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	@PostMapping("/analysis/queryVideoInfo")
	public ApiResult queryVideoInfo(String devnum, Integer userid) throws MyException, IOException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.queryVideoInfo(devnum, userid));
	}

	@PostMapping("/analysis/queryVideoList")
	public ApiResult queryVideoList(Integer userid) throws MyException, IOException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.queryVideoList(userid));
	}

	@PostMapping("/analysis/reciveUserLngLat")
	public ApiResult reciveUserLngLat(ReciveLngLat pa) throws MyException, IOException, ParseException {
		urbanFloodPreventionService.reciveUserLngLat(pa);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	@PostMapping("/analysis/getUserLngLat")
	public ApiResult getUserLngLat() throws MyException, IOException, ParseException {
		return ApiResult.resultInfo("1", "请求成功", urbanFloodPreventionService.UserLngLat());
	}

	// app可选责任人
	@PostMapping("/Device/selectOptionalUser")
	public ApiResult selectOptionalUser(Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", urbanFloodPreventionService.selectOpationalUserid(userid));
		return apiResult;
	}

	@PostMapping("/Device/selectAPPDeviceType")
	public ApiResult selectAPPDeviceType(Integer moduleid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", urbanFloodPreventionService.selectAppDeviceType(moduleid));
		return apiResult;
	}

	// 设备列表分页
	@PostMapping("/video/getRelationDevPageInfo")
	public ApiResult getRelationDevPageInfo(DevRelationVideoPageParam param) throws MyException {
		PageInfo<DeviceRelationVideoVo> selectPage = urbanFloodPreventionService.getDeviceRelationVideoPage(param);
		return ApiResult.resultInfo("1", "成功", selectPage);
	}

	// app设备安装
	@PostMapping("/insertAppDevice")
	public ApiResult insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				urbanFloodPreventionService.insertAppDevice(moduleid, userid, deviceInsertParam));
		return apiResult;
	}

	// app根据编号查找设备
	@PostMapping("/selectAppDeviceForDevnum")
	public ApiResult selectAppDeviceForDevnum(String devnum) throws MyException {
		DeviceForDevnum devnum2 = urbanFloodPreventionService.selectAppDeviceForDevnum(devnum);
		ApiResult apiResult;
		if (devnum2 != null) {
			apiResult = new ApiResult("1", "执行成功", devnum2);
		} else {
			apiResult = new ApiResult("0", "该设备不存在", null);
		}
		return apiResult;
	}

	// app设备详情
	@PostMapping("/selectAPPDeviceInfo")
	public ApiResult selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", urbanFloodPreventionService.selectAPPDeviceInfo(deviceid, moduleid));
		return apiResult;
	}

}
