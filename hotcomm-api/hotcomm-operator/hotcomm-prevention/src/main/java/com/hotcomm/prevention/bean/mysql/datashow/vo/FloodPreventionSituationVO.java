package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FloodPreventionSituationVO implements Serializable {

//	防汛态势输出类
	
	private static final long serialVersionUID = 1L;

	private String DRP;

	private String timeInfo;
	
	private String Z;

}
