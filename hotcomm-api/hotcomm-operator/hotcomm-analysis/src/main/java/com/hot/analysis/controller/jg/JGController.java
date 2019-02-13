package com.hot.analysis.controller.jg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.bean.jg.FlipNum;
import com.hot.analysis.bean.jg.JGTimeAlarmNum;
import com.hot.analysis.bean.jg.JGType;
import com.hot.analysis.bean.jg.JGTypequery;
import com.hot.analysis.bean.jg.ReasonNum;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.jg.JgService;
import com.hot.analysis.utils.SimpleDateFormatUtils;




@RestController
public class JGController {
	@Autowired
	private JgService jgService;
	
	//翻盖频率统计
	@PostMapping("/jg/selectFlipNum")
	public List<FlipNum> selectFlipNum(String starttime,  String endtime,  Integer moduleid, Integer querytype,  Integer userid) {
		Date startTime = SimpleDateFormatUtils.SimpleDateFormatTools(starttime);
		Date endTime = SimpleDateFormatUtils.SimpleDateFormatTools(endtime);
		List<FlipNum> list = jgService.selectFlipNum(startTime, endTime, moduleid, querytype, userid);
		return list;
	}
	
	//井盖分类统计
		@PostMapping("/jg/selectJGType")
		public Map<Integer, List<JGType>> selectJGType(@RequestParam("starttime") String starttime, @RequestParam("endtime") String endtime, @RequestParam("moduleid") Integer moduleid,
				@RequestParam("userid") Integer userid) {
			Date startTime = SimpleDateFormatUtils.SimpleDateFormatTools(starttime);
			Date endTime = SimpleDateFormatUtils.SimpleDateFormatTools(endtime);
			Map<Integer, List<JGType>> map = jgService.selectJGType(startTime, endTime, moduleid, userid);
			return map;
		}
		
	
		//报警时段统计
		@PostMapping("/jg/selectJGTimeAlarmNum")
		public List<JGTimeAlarmNum> selectJGTimeAlarmNum(@RequestParam("starttime") String starttime,
				@RequestParam("endtime") String endtime, @RequestParam("moduleid") Integer moduleid,
				@RequestParam("alarmtype") Integer alarmtype, @RequestParam("userid") Integer userid) {
			
			Date startTime = SimpleDateFormatUtils.SimpleDateFormatTools(starttime);
			Date endTime = SimpleDateFormatUtils.SimpleDateFormatTools(endtime);
			List<JGTimeAlarmNum> list = jgService.selectJGTimeAlarmNum(startTime, endTime, moduleid, alarmtype,
					userid);
			return list;
		}
		
		@PostMapping("/jg/selectReasonNum")
		//维修原因统计
		public List<ReasonNum> selectReasonNum(@RequestParam("starttime") String starttime,
				@RequestParam("endtime") String endtime, @RequestParam("moduleid") Integer moduleid,
				@RequestParam("pepairtype") Integer pepairtype, @RequestParam("userid") Integer userid) {
			
			Date startTime = SimpleDateFormatUtils.SimpleDateFormatTools(starttime);
			Date endTime = SimpleDateFormatUtils.SimpleDateFormatTools(endtime);
			List<ReasonNum> map = jgService.selectReasonNum(startTime, endTime, moduleid, pepairtype, userid);
			return map;
		
		}
		//
		@PostMapping("/jg/GroupListDev")
		public List<GroupListDev> GroupListDev(JGTypequery  jGTypequery) throws MyException {
			return jgService.GroupListDev(jGTypequery); 
		}
		
		
		
}
