package com.hot.manage.entity.mc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDeviceMcVo {

	private Integer id;

	private String devnum;

	private String mac;

	private String lng;

	private String lat;

	private Integer state;

	private Integer battery;

	private String addtime;

	private Integer adduserid;
	
	private Boolean isenable;
	
	private Boolean isdelete;
	
	private String code;

	private String x;

	private String y;
	
	private Integer groupid;
	
	private String groupname;
	
	private Integer own_id;
	
	private String username;//联系人
	
	private Integer moduleid;
	
	private Integer itemPicId;// 项目图片ID
	
	private String site;//所在楼层具体位置
	
	private String picpath;//图片路径
	
	private Integer videoid;// 项目关联摄像头ID
	
	private Integer relationVideoCount; //项目关联摄像头数量
	
	private String ownName;//负责人姓名
	
	private String videos;//多个摄像头

}
