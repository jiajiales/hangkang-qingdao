package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTPatrols implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "巡检人员id")
	private Integer id;
	
	//(value = "用户编号")
	private String userNum;
	
	//(value = "用户姓名")
	private String contacts;
	
	//(value = "用户添加时间")
	private String useraddtime;
	
	//(value = "设备总数")
	private Integer deviceidnum;
	
	//(value = "项目总数")
	private Integer groupidnum;
	
	//(value = "最近签到时间")
	private String addtime;
	
	//(value = "账号状态：未冻结：账号为正常状态； 已冻结：账号为冻结状态")
	private String Isenable;
}
