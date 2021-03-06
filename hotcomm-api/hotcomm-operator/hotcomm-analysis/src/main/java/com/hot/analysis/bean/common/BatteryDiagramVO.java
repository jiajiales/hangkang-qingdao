package com.hot.analysis.bean.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BatteryDiagramVO {

	private Integer moduleid;
	private Integer lbcount;
	private Integer count;

}
