package com.hot.manage.entity.item;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDeviceGroupVo {

	private Integer id;

	private Integer moduleid;

	private String groupname;

	private Integer fatherid;

	private String groupcode;

	private String x;

	private String y;

	private Integer coordinate;

	private Boolean isenable;

	private Boolean isdelete;

	private String addtime;

	private Integer adduserid;

	private Integer managerid;
	
	private String contacts;

	private Integer cityid;

	private String telephone;

	private String imgpath;

	private String itemnum;

	private Integer devtotal;

	private Integer alarmCount;

	private Integer faultCount;
	
	private List<TItemPic> pics;

}
