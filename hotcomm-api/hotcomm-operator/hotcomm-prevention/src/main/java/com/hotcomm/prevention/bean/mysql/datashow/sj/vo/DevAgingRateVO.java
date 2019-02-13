package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevAgingRateVO {

	private Integer moduleid;
	private Integer devcount;
	private Integer year;

}
