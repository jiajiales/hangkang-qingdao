package com.hot.parse.entity.jg;

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
@Table(name = "log_jg")
public class Log_jg implements Serializable {
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

	@Column(name = "state")
	public boolean state;

	@Column(name = "lbflag")
	public boolean lbflag;

	@Column(name = "keepalive")
	public boolean keepalive;

	@Column(name = "calibration")
	public boolean calibration;

	@Column(name = "voltagestate")
	public String voltagestate;

	@Column(name = "voltage")
	public String voltage;

	@Column(name = "gsensor")
	public String gsensor;

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
