package com.hot.parse.entity.yg;

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
@Table(name = "log_yg")
public class Log_yg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(name = "lb_alarm")
	public String lb_alarm;

	@Column(name = "channel")
	public String channel;

	@Column(name = "commsys_type")
	public String commsys_type;

	@Column(name = "df_id")
	public String df_id;

	@Column(name = "fault_alarm")
	public String fault_alarm;

	@Column(name = "fire_alarm")
	public String fire_alarm;

	@Column(name = "frame_cnt")
	public short frame_cnt;

	@Column(name = "gwid")
	public String gwid;

	@Column(name = "gwip")
	public String gwip;

	@Column(name = "input_time")
	public String input_time;

	@Column(name = "mac_addr")
	public String mac_addr;

	@Column(name = "msg_id")
	public String msg_id;

	@Column(name = "place_id")
	public Integer place_id;

	@Column(name = "repeater")
	public String repeater;

	@Column(name = "reserved")
	public String reserved;

	@Column(name = "rssi")
	public Integer rssi;

	@Column(name = "sf")
	public Integer sf;

	@Column(name = "snr")
	public Integer snr;

	@Column(name = "snr_max")
	public Integer snr_max;

	@Column(name = "snr_min")
	public Integer snr_min;

	@Column(name = "status")
	public short status;

	@Column(name = "sys_type")
	public Integer sys_type;

	@Column(name = "temp_alarm")
	public boolean temp_alarm;

	@Column(name = "temp_value")
	public float temp_value;

	@Column(name = "update_time")
	public String update_time;
}
