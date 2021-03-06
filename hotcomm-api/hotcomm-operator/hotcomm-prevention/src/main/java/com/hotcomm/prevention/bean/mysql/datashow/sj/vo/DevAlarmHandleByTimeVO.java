package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevAlarmHandleByTimeVO {

	private Integer moduleid;
	private String date;
	private Integer alarmcount;
	private Integer falsecount;
	private Integer handlecount;
}
