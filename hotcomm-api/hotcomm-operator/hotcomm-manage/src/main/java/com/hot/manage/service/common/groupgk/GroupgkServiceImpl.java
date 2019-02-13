package com.hot.manage.service.common.groupgk;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.common.groupgk.GroupgkMapper;
import com.hot.manage.entity.common.groupgk.Groupgkcount;
import com.hot.manage.entity.common.groupgk.alarmcount_class;
import com.hot.manage.entity.common.groupgk.alarmcount_day;
import com.hot.manage.entity.common.groupgk.alarmcount_month;


@Transactional
@Service
public class GroupgkServiceImpl implements GroupgkService {
    @Autowired
    private GroupgkMapper groupgkMapper;


    @Override
    public Groupgkcount groupgkcount(Integer userid,Integer moduleid){
        return groupgkMapper.groupgkcount(userid,moduleid);
    }

    @Override
    public List<alarmcount_month> select_alarm_count_month(Integer userid, Integer moduleid, Integer yyyy){
        return groupgkMapper.select_alarm_count_month(userid,moduleid,yyyy);
    }

    @Override
    public List<alarmcount_day> select_alarm_count_day(Integer userid, Integer moduleid){
        return groupgkMapper.select_alarm_count_day(userid,moduleid);
    }

    @Override
    public List<alarmcount_class> select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy){
        return groupgkMapper.select_alarm_count_class(userid,moduleid,yyyy);
    }

}
