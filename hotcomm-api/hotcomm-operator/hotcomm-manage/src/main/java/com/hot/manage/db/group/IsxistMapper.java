package com.hot.manage.db.group;


import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.group.Isexist;

public interface IsxistMapper {
    public Isexist countdg(@Param("dgid") Integer gdid, @Param("keywords1") String keywords1, @Param("keywords2") String keywords2, @Param("devid") Integer devid);

    public Isexist delgroup_before(@Param("id") Integer id);

    public Isexist selectsiterelation(@Param("devid") Integer devid,@Param("moduleid") Integer moduleid);

    public Isexist selectsiterelation2(@Param("mapimgid") Integer mapimgid,@Param("moduleid") Integer moduleid);

}
