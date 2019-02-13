package com.hot.parse.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class extra {
	public String commsysType;

	public double rssi;

	public double snr;

	public double frameCnt;

	public String gwid;

	public String gwip;

	public String channel;

	public Integer sf;
}
