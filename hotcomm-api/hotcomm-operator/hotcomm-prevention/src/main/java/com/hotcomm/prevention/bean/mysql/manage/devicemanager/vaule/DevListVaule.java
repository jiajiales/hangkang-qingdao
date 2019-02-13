package com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule;

import com.hotcomm.prevention.bean.mysql.manage.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevListVaule extends PageParam {
	private String starttime;
	private String endtime;
	private String context;
	private Integer batterytype;
	private Integer moduleid;
	private Integer userid;
	private Integer groupid;
}
