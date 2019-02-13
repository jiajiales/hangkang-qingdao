package com.hotcomm.prevention.bean.mysql.datashow.camera;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "t_device_video")
public class TDeviceVideo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String devnum;

	private String mac;

	private String code;

	private String lng;

	private String lat;

	private Integer state;

	private Double x;

	private Double y;

	private Date addtime;

	private Integer adduserid;

	private Boolean isenable;

	private Boolean isdelete;

	private Integer ownid;

	private String videopath;

	private String factory;

	private String username;

	private String password;

	private String nvrip;

	private String nvrchannel;
	
	private String ip;

	private String port;

	private Integer type;

}