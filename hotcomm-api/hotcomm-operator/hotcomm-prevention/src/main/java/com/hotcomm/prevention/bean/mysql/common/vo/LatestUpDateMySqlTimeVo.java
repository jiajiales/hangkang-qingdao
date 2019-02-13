package com.hotcomm.prevention.bean.mysql.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LatestUpDateMySqlTimeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String get_PPTN_LatestTime;

	private String get_RIVER_LatestTime;

}
