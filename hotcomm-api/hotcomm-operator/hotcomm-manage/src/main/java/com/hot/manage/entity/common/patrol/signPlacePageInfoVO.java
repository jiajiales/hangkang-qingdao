package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class signPlacePageInfoVO {
	private Integer id;//摇一摇设备ID
	private String devnum;//设备编号
	private String groupid;//项目组ID
	private String groupname;//关联项目
	private String address;// 放置位置
	private String lng;//经度
	private String lat;//维度
	private String patID;//签到人员
	private String patName;//签到名称
	private String lastsigntime;//最后一次签到时间
	private String QRcodePicUrl;//签到地址二维码图片网址
}
