package com.hot.parse.entity.dc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "log_dc")
public class Log_dc implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "queueid")
	public String queueid;

	@Column(name = "macAddr")
	public String macAddr;

	@Column(name = "data")
	public String data;

	@Column(name = "stmcflag")
	public boolean stmcflag;

	@Column(name = "mcflag")
	public boolean mcflag;

	@Column(name = "lbflag")
	public boolean lbflag;

	@Column(name = "keepalive")
	public boolean keepalive;

	@Column(name = "carflag")
	public boolean carflag;

	@Column(name = "voltage")
	public String voltage;

	@Column(name = "magnetic")
	public String magnetic;

	@Column(name = "temperature")
	public Integer temperature;

	@Column(name = "addtime")
	public String addtime;

	@Column(name = "recv")
	public String recv;

	@Column(name = "commsysType")
	public String commsysType;

	@Column(name = "rssi")
	public double rssi;

	@Column(name = "snr")
	public double snr;

	@Column(name = "frameCnt")
	public double frameCnt;

	@Column(name = "gwid")
	public String gwid;

	@Column(name = "gwip")
	public String gwip;

	@Column(name = "channel")
	public String channel;

	@Column(name = "sf")
	public Integer sf;

	@Column(name = "fport")
	public Integer fport;

	@Column(name = "pub")
	public String pub;

}
