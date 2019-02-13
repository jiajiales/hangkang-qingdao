package com.hotcomm.prevention.bean.mysql.datashow.video;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Push implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private String username;
	private String password;
	private String nvrip;
	private String nvrchannel;
	private String ip;
	private String prod;
	private String devnum;
	private String ffmpeg;
}
