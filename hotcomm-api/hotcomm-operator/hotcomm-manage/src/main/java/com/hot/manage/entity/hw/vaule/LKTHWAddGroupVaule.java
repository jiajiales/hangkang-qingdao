package com.hot.manage.entity.hw.vaule;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWAddGroupVaule {
	private Integer id;
	private String groupname;
	private String groupcode;
	private double x;
	private double y;
	private Integer managerid;
	private Integer cityid;
	private String imgpath;
	private String itemnum;
	private Integer moduleid;
	private Integer userid;
	private String addtime;
	private Integer fatherid;
	private List<LKTHWAddGroupVauleSite> sitelist;
}
