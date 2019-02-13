package com.hot.manage.entity.yg;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSelectGroupWorkFather implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "工单id")
	private Integer woid;

	//(value = "工单编号")
	private String code;

	//(value = "发布时间")
	private String addtime;

	//(value = "工单标题")
	private String title;

	//(value = "责任人id")
	private Integer assignid;

	//(value = "责任人")
	private String contacts;
	
	//(value = "要求处理时间")
	private Integer complete_time;

	//(value = "派单人id")
	private Integer adduserid;

	//(value = "派单人")
	private String adduser;
	
	//(value = "派单人电话1")
	private String telephone;

	//(value = "工单处理状态")
	private Integer state;

	//(value = "审核状态")
	private Integer audit;

	//(value = "实体类集合")
	private List<LKTSelectGroupWork> son;
}
