package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LogSydl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String lb_alarm;
	private String channel;
	private String commsys_type;
	private String df_id;
	private String fault_alarm;
	private String fire_alarm;
	private String frame_cnt;
	private String gwid;
	private String gwip;
	private String input_time;
	private String mac_addr;
	private String msg_id;
	private Integer place_id;
	private String repeater;
	private String reserved;
	private Double rssi;
	private Integer sf;
	private Integer snr;
	private Integer snr_max;
	private Integer snr_min;
	private Integer status;
	private Integer sys_type;
	private Integer temp_alarm;
	private Double temp_value;
	private String update_time;
}
