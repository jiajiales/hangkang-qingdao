package com.hotcomm.prevention.bean.mysql.manage.workorder.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkMaintenanceAppVaule {
	// (value = "当前登录用户id")
	private Integer userid;

	// (value = "模块id")
	private Integer moduleid;

	// (value = "派单人id")
	private Integer pdid;

	// (value = "工单id")
	private Integer woid;

	// (value = "设备id")
	private Integer devid;

	// (value = "处理描述")
	private String problemdesc;

	// (value = "选填，图片资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3")
	private String pictureurl;

	// (value = "选填，语音资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3")
	private String voiceurl;

	// (value = "备注")
	private String remark;

	// (value = "1：维修，2：挂起，3：更换")
	private Integer handleType;
}
