package com.hot.manage.db.common.groupgk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.groupgk.Groupgkcount;
import com.hot.manage.entity.common.groupgk.alarmcount_class;
import com.hot.manage.entity.common.groupgk.alarmcount_day;
import com.hot.manage.entity.common.groupgk.alarmcount_month;

public interface GroupgkMapper {
    public Groupgkcount groupgkcount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

    public List<alarmcount_month> select_alarm_count_month(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,@Param("yyyy") Integer yyyy);

    public List<alarmcount_day> select_alarm_count_day(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

    public List<alarmcount_class> select_alarm_count_class(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,@Param("yyyy") Integer yyyy);



}
