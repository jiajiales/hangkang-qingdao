package com.hotcomm.prevention.bean.mysql.datashow.sj;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmTypeList implements Serializable{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String statename;

	private Integer num;
}
