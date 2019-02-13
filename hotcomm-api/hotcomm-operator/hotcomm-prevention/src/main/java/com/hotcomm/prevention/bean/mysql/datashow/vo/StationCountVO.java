package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class StationCountVO implements Serializable {

	// 站点统计输出类

	private static final long serialVersionUID = 1L;

	private Integer ppCount;

	private Integer zzCount;

	private Integer pzCount;

	private Integer materialCount;

}
