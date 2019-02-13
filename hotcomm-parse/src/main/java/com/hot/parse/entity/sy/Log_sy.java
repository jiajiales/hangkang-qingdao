package com.hot.parse.entity.sy;

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
@Table(name = "log_sy")
public class Log_sy implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "lb_alarm")
	private String lb_alarm;

	@Column(name = "channel")
	private String channel;

	@Column(name = "commsys_type")
	private String commsys_type;

	@Column(name = "df_id")
	private String df_id;

	@Column(name = "fault_alarm")
	private String fault_alarm;

	@Column(name = "fire_alarm")
	private String fire_alarm;

	@Column(name = "frame_cnt")
	private String frame_cnt;

	@Column(name = "gwid")
	private String gwid;

	@Column(name = "gwip")
	private String gwip;

	@Column(name = "input_time")
	private String input_time;

	@Column(name = "mac_addr")
	private String mac_addr;

	@Column(name = "msg_id")
	private String msg_id;

	@Column(name = "place_id")
	private Integer place_id;

	@Column(name = "repeater")
	private String repeater;

	@Column(name = "reserved")
	private String reserved;

	@Column(name = "rssi")
	private double rssi;

	@Column(name = "sf")
	private Integer sf;

	@Column(name = "snr")
	private Integer snr;

	@Column(name = "snr_max")
	private Integer snr_max;

	@Column(name = "snr_min")
	private Integer snr_min;

	@Column(name = "status")
	private String status;

	@Column(name = "sys_type")
	private Integer sys_type;

	@Column(name = "temp_alarm")
	private boolean temp_alarm;

	@Column(name = "temp_value")
	private float temp_value;

	@Column(name = "update_time")
	private String update_time;

}
