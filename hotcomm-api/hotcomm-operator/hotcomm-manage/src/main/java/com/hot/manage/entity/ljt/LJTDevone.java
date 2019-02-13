package com.hot.manage.entity.ljt;

@lombok.Getter
@lombok.Setter
public class LJTDevone {
    Integer id;
    private String devnum;
    private String mac;
    private String code;
    private double lng;
    private double lat;
    private double x;
    private double y;
    private Integer own_id;
    private Integer groupid;
    private Integer item_pic_id;
    private String videos;
}
