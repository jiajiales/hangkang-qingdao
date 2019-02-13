package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkReplaceAppVaule {
	
	//(value = "当前登录用户id")
	private Integer userid;
	
	//(value = "派单人id")
	private Integer pdid;
	
	//(value = "工单id")
	private Integer woid;

	//(value = "设备id")
	private Integer devid;
	
	//(value = "设备编号")
	private String devnum;

	//(value = "选填，图片资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3")
	private String pictureurl;

	//(value = "选填，语音资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3")
	private String voiceurl;

	//(value = "备注")
	private String remark;
	
}
