package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTTureWork implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "工单id")
	private Integer workid;
	
	//(value = "处理方式，1：维修，2：挂起")
	private Integer handleType;
	
	//(value = "处理描述")
	private String problemdesc;
	
	//(value = "备注")
	private String remark;
	
	//(value = "处理结束时间")
	private String endtime;
	
	//(value = "资源类型，1：图片；2：音频；3：视频")
	private Integer resourcetype;
	
	//(value = "资源路径")
	private String url;
}
