package com.hotcomm.prevention.bean.mysql.datashow.jg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WellCoverType {
	private Integer moduleID;
	private String type;
	private Integer Count;
	
}
