package com.hot.manage.entity.yg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TDeviceYg {
    private Integer id;

    private String devnum;

    private String mac;

    private String code;

    private String addtime;

    private Boolean isdelete;

    private Boolean isenable;

    private Integer adduserid;

    private Double lat;

    private Double lng;

    private Integer state;

    private String lastalarmtime;

    private Integer battery;

    private Double x;

    private Double y;

    private Integer ownId;
}