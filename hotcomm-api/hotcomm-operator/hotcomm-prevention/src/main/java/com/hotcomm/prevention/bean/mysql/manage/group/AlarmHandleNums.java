package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AlarmHandleNums implements Serializable{
private static final long serialVersionUID=1L;
	
	private  String TheDate;

	private Integer unhandlecount;
	
    private Integer handlingcount;
	
	private Integer handlecount;
	
	private Integer num;
}
