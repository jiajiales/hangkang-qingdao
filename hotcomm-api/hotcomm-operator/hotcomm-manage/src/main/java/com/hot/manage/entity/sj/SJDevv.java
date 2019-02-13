package com.hot.manage.entity.sj;

import com.hot.manage.entity.PageParam;

@lombok.Getter
@lombok.Setter
public class SJDevv extends PageParam {
    private Integer userid;
    private Integer groupid;
    private String starttime;
    private String  endtime;
    private String keywords;
    private Integer batterytype;

}
