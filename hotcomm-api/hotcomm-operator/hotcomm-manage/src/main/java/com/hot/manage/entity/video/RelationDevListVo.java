package com.hot.manage.entity.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RelationDevListVo {

	private Integer devId;
	private String devnum;
	private String devGroupName;
	private String groupcode;
	private Integer ownId;
	private String devLat;
	private String devLng;
	private Integer devBattery;
	private String videoNum;
	private String videoMac;
	private Integer videoCount;

}
