package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LogDc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String queueid;

	private String macaddr;

	private String data;

	private Boolean stmcflag;

	private Boolean mcflag;

	private Boolean lbflag;

	private Boolean keepalive;

	private Boolean carflag;

	private String voltage;

	private String magnetic;

	private Integer temperature;

	private String addtime;

	private String recv;

	private String commsystype;

	private Double rssi;

	private Double snr;

	private Double framecnt;

	private String gwid;

	private String gwip;

	private String channel;

	private Integer sf;

	private Integer fport;

	private String pub;

}