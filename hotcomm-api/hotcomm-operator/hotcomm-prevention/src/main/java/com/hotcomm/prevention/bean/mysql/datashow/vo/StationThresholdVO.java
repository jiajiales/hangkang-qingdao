package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class StationThresholdVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String PP_AlarmValue;

	private String PP_YJValue;
	
	private String ZZ_AlarmValue;
	
	private String ZZ_YJValue;

}
