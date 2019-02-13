package com.hotcomm.prevention.bean.mysql.manage.patrol.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignLogPageInfoParam {
	private Integer pageSize;
	private Integer pageNum;
	private String starttime;
	private String endtime;
	private String contextid;
	private String context;// 关键字：人员编号或人员名称
	private Integer userid;
	private Integer state;
	private Integer signID;
}
