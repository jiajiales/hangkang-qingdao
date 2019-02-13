package com.hot.analysis.bean.dc;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevStateNum implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// (value="模块id")
	private Integer moduleid;// 模块id

	// (value="终端设备数量")
	private Integer devcount;// 终端设备数量

	// (value="已停车数量")
	private Integer parkingcount;// 已停车数量

	// (value="空闲位置数量")
	private Integer freecount;// 空闲位置数量

//	// (value="报警数量")
//	private Integer alarmcount;// 报警数量
//
//	// (value="异常停车数量")
//	private Integer unormalcount;// 异常停车数量

	// (value="故障设备数量")
	private Integer faultcount;// 故障设备数量
}
