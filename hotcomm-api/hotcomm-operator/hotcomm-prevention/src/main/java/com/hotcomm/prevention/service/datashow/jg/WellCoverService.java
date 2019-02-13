package com.hotcomm.prevention.service.datashow.jg;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.datashow.jg.AreaVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.FlipNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.GroupInfoVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTimeAlarmNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTypequery;
import com.hotcomm.prevention.bean.mysql.datashow.jg.ReasonNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverSpreadVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverType;
import com.hotcomm.prevention.exception.MyException;

public interface WellCoverService {

	/**翻盖频率统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param querytype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<FlipNum> selectFlipNum(String starttime, String endtime, Integer moduleid, Integer querytype, Integer userid)
			throws MyException;

	/**井盖分类统计
	 * @param moduleid
	 * @param queryType
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<WellCoverType> selectJGType(Integer moduleid, Integer queryType,
			Integer userid) throws MyException;

	/**报警时段统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param alarmtype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<JGTimeAlarmNum> selectJGTimeAlarmNum(String starttime, String endtime, Integer moduleid, Integer alarmtype,
			Integer userid) throws MyException;

	/**维修原因统计
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param pepairtype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<ReasonNum> selectReasonNum(String starttime, String endstime, Integer moduleid, Integer pepairtype,
			Integer userid) throws MyException;

	/**分类统计井盖设备地图
	 * @param jGTypequery
	 * @return
	 * @throws MyException
	 */
	List<WellCoverSpreadVO> GroupListDev(JGTypequery jGTypequery) throws MyException;

	List<WellCoverSpreadVO> wellCoverSpread(Integer moduleid, Integer userid, Integer groupid, String code)throws MyException;

	List<AreaVO> selectArea(Integer moduleid, Integer userid)throws MyException;

	List<GroupInfoVO> selectGroup(Integer moduleid, Integer areaid, Integer userid)throws MyException;

}
