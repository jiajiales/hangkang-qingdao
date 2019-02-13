package com.hotcomm.prevention.controller.datashow.jg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTypequery;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.jg.WellCoverService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("WellCover")
public class WellCoverController {
	@Autowired

	private WellCoverService wsellCoverService;


	/** 翻盖频率统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param querytype
	 * @param userid
	 * @return
	 */
	@PostMapping("/selectFlipNum")
	public ApiResult selectFlipNum(String starttime, String endtime, Integer moduleid, Integer querytype,
			Integer userid) {
		return ApiResult.resultInfo("1", "成功",
				wsellCoverService.selectFlipNum(starttime, endtime, moduleid, querytype, userid));
	}

	
	/**井盖分类统计
	 * @param moduleid
	 * @param queryType
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectJGType")
	public ApiResult selectJGType( Integer moduleid, Integer queryType, Integer userid)
			throws MyException {
		return ApiResult.resultInfo("1", "成功",
				wsellCoverService.selectJGType( moduleid, queryType, userid));
	}

	/**报警时段统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param alarmtype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectJGTimeAlarmNum")
	public ApiResult selectJGTimeAlarmNum(String starttime, String endtime, Integer moduleid, Integer alarmtype,
			Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功",
				wsellCoverService.selectJGTimeAlarmNum(starttime, endtime, moduleid, alarmtype, userid));
	}

	/**维修原因统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param pepairtype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectReasonNum")
	public ApiResult selectReasonNum(String starttime, String endtime, Integer moduleid, Integer pepairtype,
			Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功",
				wsellCoverService.selectReasonNum(starttime, endtime, moduleid, pepairtype, userid));
	}

	/**分类统计井盖设备地图
	 * @param jGTypequery
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/GroupListDev")
	public ApiResult GroupListDev(JGTypequery jGTypequery) throws MyException {
		return ApiResult.resultInfo("1", "成功", wsellCoverService.GroupListDev(jGTypequery));
	}

	
	/**井盖设备地图分布
	 * @param jGTypequery
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/wellCoverSpread")
	public ApiResult wellCoverSpread(Integer moduleid, Integer userid,Integer groupid,String code) throws MyException {
		return ApiResult.resultInfo("1", "成功", wsellCoverService.wellCoverSpread(moduleid,userid,groupid,code));
	}
	
	

	/**区域地图分布
	 * @param jGTypequery
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectArea")
	public ApiResult selectArea(Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", wsellCoverService.selectArea(moduleid,userid));
	}
	
	/**查询项目组
	 * @param jGTypequery
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/selectGroup")
	public ApiResult selectGroup(Integer moduleid,Integer areaid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", wsellCoverService.selectGroup(moduleid,areaid,userid));
	}
	
	
}
