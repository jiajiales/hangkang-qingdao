package com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_device_all")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_device_all implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "devnum")
	private String devnum;

	@Column(name = "mac")
	private String mac;

	@Column(name = "code")
	private String code;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;

	@Column(name = "coordinate")
	private Integer coordinate;

	@Column(name = "installtime")
	private String installtime;

	@Column(name = "isenable")
	private Integer isenable;

	@Column(name = "isdelete")
	private Integer isdelete;

	@Column(name = "addtime")
	private String addtime;

	@Column(name = "adduserid")
	private Integer userid;

	@Column(name = "lastalarmtime")
	private String lastalarmtime;

	@Column(name = "battery")
	private Integer battery;

	@Column(name = "x")
	private double x;

	@Column(name = "y")
	private double y;

	@Column(name = "own_id")
	private Integer own_id;

	@Column(name = "devtype")
	private Integer devtype;

	@Column(name = "state")
	private Integer state;

	@Column(name = "alarmstate")
	private Integer alarmstate;

	@Column(name = "lastvalue")
	private String lastvalue;

	@Column(name = "max_alarmvalue")
	private String max_alarmvalue;

	@Column(name = "min_alarmvalue")
	private String min_alarmvalue;

	@Column(name = "jg_coverName")
	private String jg_coverName;

	@Column(name = "jg_manu")
	private String jg_manu;

	@Column(name = "jg_material")
	private String jg_material;

	@Column(name = "jg_purpose")
	private Integer jg_purpose;

	@Column(name = "jg_loadbear")
	private Integer jg_loadbear;

	@Column(name = "ljt_height")
	private double ljt_height;

	@Column(name = "ljt_alarmvalue")
	private double ljt_alarmvalue;

	@Column(name = "pm")
	private String pm;

	@Column(name = "pm_one")
	private String pm_one;

	@Column(name = "pm_noiseval")
	private String pm_noiseval;

	@Column(name = "pm_temval")
	private String pm_temval;

	@Column(name = "pm_humval")
	private String pm_humval;

	@Column(name = "pm_light")
	private String pm_light;

	@Column(name = "sl_voltage")
	private String sl_voltage;

	@Column(name = "sl_ampere")
	private String sl_ampere;

	@Column(name = "sl_watt")
	private String sl_watt;

	@Column(name = "sl_lighteness")
	private String sl_lighteness;

	@Column(name = "sl_electricity")
	private String sl_electricity;

	@Column(name = "sl_offtime")
	private String sl_offtime;

	@Column(name = "sl_ontime")
	private String sl_ontime;

	@Column(name = "alarmset")
	private Integer alarmset;

	@Column(name = "wt_electricity")
	private String wt_electricity;

	@Column(name = "ywj_lastvalue1")
	private Integer ywj_lastvalue1;

	@Column(name = "ywj_lastvalue2")
	private String ywj_lastvalue2;

	@Column(name = "ywj_lastvalue3")
	private String ywj_lastvalue3;

	@Column(name = "ywj_lastvalue4")
	private String ywj_lastvalue4;

	@Column(name = "ywj_lastvalue5")
	private String ywj_lastvalue5;

	@Column(name = "ywj_lastvalue6")
	private String ywj_lastvalue6;

	@Column(name = "ywj_plusminus")
	private String ywj_plusminus;

	@Column(name = "moduleid")
	private Integer moduleid;
	
	@Column(name = "adduserid")
	private Integer adduserid;
}
