package com.hotcomm.prevention.bean.mysql.datashow.dc;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevStateNum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer moduleid;// 模块id

	private Integer devcount;// 终端设备数量

	private Integer parkingcount;// 已停车数量

	private Integer freecount;// 空闲位置数量

	private Integer faultcount;// 故障设备数量
}
