package com.hot.manage.entity.common.group;

import java.util.List;

@lombok.Getter
@lombok.Setter
public class Groups {
    private  Integer id;
    private  Integer userid;
    private  Integer managerid;
    private  Integer moduleid;
    private  String imgpath;
    private  String groupname;
    private  String groupcode;
    private  Double x;
    private  Double y;
    private  String itemnum;
    private List<Groupmaps> sitelist;
}
