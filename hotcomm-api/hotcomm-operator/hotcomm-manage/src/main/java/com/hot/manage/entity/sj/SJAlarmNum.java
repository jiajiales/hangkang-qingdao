package com.hot.manage.entity.sj;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SJAlarmNum  implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String stateName;
	
	private Integer Num;
}
