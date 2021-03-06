package com.hot.manage.entity.mc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_device_mc")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDeviceMc implements Serializable {
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

	@Column(name = "lng")
	private String lng;

	@Column(name = "lat")
	private String lat;

	@Column(name = "state")
	private Integer state;

	@Column(name = "battery")
	private Integer battery;

	@Column(name = "addtime")
	private String addtime;

	@Column(name = "isenable")
	private Boolean isenable;

	@Column(name = "isdelete")
	private Boolean isdelete;

	@Column(name = "adduserid")
	private Integer adduserid;

	@Column(name = "lastalarmtime")
	private String lastalarmtime;

	@Column(name = "code")
	private String code;

	@Column(name = "x")
	private String x;

	@Column(name = "y")
	private String y;
	
	@Column(name = "own_id")
	private Integer ownId;

}