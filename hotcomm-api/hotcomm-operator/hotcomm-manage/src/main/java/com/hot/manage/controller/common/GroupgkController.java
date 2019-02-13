package com.hot.manage.controller.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.common.groupgk.Groupgkcount;
import com.hot.manage.entity.common.groupgk.alarmcount_class;
import com.hot.manage.entity.common.groupgk.alarmcount_day;
import com.hot.manage.entity.common.groupgk.alarmcount_month;
import com.hot.manage.service.common.groupgk.GroupgkService;
import com.hot.manage.utils.ApiResult;

@Controller
@RequestMapping("/groupgk")
public class GroupgkController {

    @Autowired
    private GroupgkService groupgkService;


    @Value(value = "${groupgk.systime}")
    private String systime;
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressWarnings("unused")
	private String etime= sdf.format(d);

    /**
     *项目概况计数
     * @param userid
     * @param moduleid
     * @return
     */
    @ResponseBody
    @Permissions("groupgk:count:query")
    @PostMapping("/count")
    public ApiResult groupgkcount(Integer userid, Integer moduleid){
        Groupgkcount list= groupgkService.groupgkcount(userid,moduleid);
        int t1=(int)(System.currentTimeMillis()/1000);
        int t2=1525104000;
       list.setSyscount((t1-t2)/3600/24);

        return ApiResult.resultInfo("1","成功",list);
    }

    /**
     * 每月报警数量
     * @param userid
     * @param moduleid
     * @param yyyy
     * @return
     */
    @ResponseBody
    @Permissions("groupgk:alarmcounttotalmonth:query")
    @PostMapping("/alarmcounttotalmonth")
    public ApiResult select_alarm_count_month(Integer userid, Integer moduleid, Integer yyyy){
        List<alarmcount_month> list= groupgkService.select_alarm_count_month(userid,moduleid,yyyy);
        return ApiResult.resultInfo("1","成功",list);

    }

    /**
     * 近7天的报警
     * @param userid
     * @param moduleid
     * @return
     */
    @ResponseBody
    @Permissions("groupgk:alarmcounttotalday:query")
    @PostMapping("/alarmcounttotalday")
    public ApiResult select_alarm_count_day(Integer userid, Integer moduleid){
        List<alarmcount_day> list= groupgkService.select_alarm_count_day(userid,moduleid);
        return ApiResult.resultInfo("1","成功",list);
    }

    /**
     * 报警类型统计
     * @param userid
     * @param moduleid
     * @param yyyy
     * @return
     */
    @ResponseBody
    @Permissions("groupgk:alarmcountclass:query")
    @PostMapping("/alarmcountclass")
    public ApiResult select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy){
        List<alarmcount_class> list= groupgkService.select_alarm_count_class(userid,moduleid,yyyy);
        return ApiResult.resultInfo("1","成功",list);
    }
}