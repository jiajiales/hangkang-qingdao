package com.hot.manage.entity.ljt;

import com.hot.manage.entity.PageParam;

@lombok.Getter
@lombok.Setter
public class LJTDevv extends PageParam {
    private Integer userid;
    private Integer groupid;
    private String starttime;
    private String  endtime;
    private String keywords;
    private Integer batterytype;

}
