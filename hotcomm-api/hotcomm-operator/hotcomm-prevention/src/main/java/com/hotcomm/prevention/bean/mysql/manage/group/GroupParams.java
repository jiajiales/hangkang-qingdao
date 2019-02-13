package com.hotcomm.prevention.bean.mysql.manage.group;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

public class GroupParams {
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
    private  Integer areaid;
    private List<GroupMaps> sitelist;
}
