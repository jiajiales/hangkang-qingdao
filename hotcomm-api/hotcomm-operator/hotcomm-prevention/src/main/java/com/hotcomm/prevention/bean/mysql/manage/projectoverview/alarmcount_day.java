package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

//返回数据
@lombok.Getter
@lombok.Setter
public class alarmcount_day {
    private String  day;
    private Integer alarmcount_total;
    private Integer alarmcount_state0;
    private Integer alarmcount_state1;
}
