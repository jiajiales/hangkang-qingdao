package com.hot.manage.entity.jg.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTJgAddGroupVaule {
	private Integer id;
	private String itemnum;
	private String groupname;
	private String groupcode;
	private String x;
	private String y;
	private Integer managerid;
	private Integer cityid;
	private Integer moduleid;
	private Integer userid;
	private String imgpath;
	private String addtime;
	private Integer count;
	private Integer fatherid;
	private List<LKTJgAddGroupVauleSite> sitelist;
}
