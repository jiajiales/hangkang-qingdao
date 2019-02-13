package com.hotcomm.prevention.bean.mysql.manage.group;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AlarmNums implements Serializable {
	private static final long serialVersionUID=1L;
	
	private  String stateName;
	
	private Integer Num;
}
