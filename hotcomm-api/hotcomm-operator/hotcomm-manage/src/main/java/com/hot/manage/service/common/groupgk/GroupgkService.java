package com.hot.manage.service.common.groupgk;

import java.util.List;

import com.hot.manage.entity.common.groupgk.Groupgkcount;
import com.hot.manage.entity.common.groupgk.alarmcount_class;
import com.hot.manage.entity.common.groupgk.alarmcount_day;
import com.hot.manage.entity.common.groupgk.alarmcount_month;
import com.hot.manage.exception.MyException;


public interface GroupgkService {
    public Groupgkcount groupgkcount(Integer userid, Integer moduleid) throws MyException;

    public List<alarmcount_month> select_alarm_count_month(Integer userid,Integer moduleid, Integer yyyy) throws MyException;

    public List<alarmcount_day> select_alarm_count_day(Integer userid, Integer moduleid) throws MyException;

    public List<alarmcount_class> select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy) throws MyException;

}
