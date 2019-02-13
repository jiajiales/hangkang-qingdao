package com.hot.manage.entity.dc.param;

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
@Table(name = "t_device_dc")
public class DCDeviceParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 设备id
	private Integer id;

	// 设备编号
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
	private Integer adduserid;

	@Column(name = "state")
	private Integer state;

	@Column(name = "lastalarmtime")
	private String lastalarmtime;

	@Column(name = "Battery")
	private Integer Battery;

	@Column(name = "x")
	private String x;

	@Column(name = "y")
	private String y;

	@Column(name = "own_id")
	private Integer own_id;
     
	
}