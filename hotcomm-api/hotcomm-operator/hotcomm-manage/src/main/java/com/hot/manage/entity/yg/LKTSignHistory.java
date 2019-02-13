package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignHistory implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "签到记录id")
	private Integer id;
	
	//(value = "签到设备编号")
	private String devnum;
	
	//(value = "关联项目名称")
	private String groupname;
	
	//(value = "签到用户")
	private String contacts;
	
	//(value = "签到时间")
	private String addtime;
	
	//(value = "签到状态")
	private String signstate;
	
}
