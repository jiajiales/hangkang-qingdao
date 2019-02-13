package com.hotcomm.prevention.bean.mysql.datashow.jg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupInfoVO {
	
    private Integer groupid;//模块id
	
	private String groupname;//报警类型
	
	private double x;//报警时段
	
	private double y;//数量
	
	private Integer state;
}
