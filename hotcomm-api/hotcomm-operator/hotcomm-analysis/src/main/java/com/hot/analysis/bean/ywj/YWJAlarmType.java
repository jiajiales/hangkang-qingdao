package com.hot.analysis.bean.ywj;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YWJAlarmType implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// (value = "设备类型")
	private Integer moduleid;

	// (value = "总报警数量")
	private Integer allalarmcount;

	// (value = "水位报警数量")
	private Integer alarmcount;

	// (value = "故障数量")
	private Integer failurecount;

	// (value = "其他数量")
	private Integer othercount;

	// (value = "水位上涨数量")
	private Integer risingwaterscount;

	// (value = "水位下降数量")
	private Integer fallingwaterscount;

}
