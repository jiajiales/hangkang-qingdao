package com.hot.manage.entity.common.event;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppEventInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer state;
	private String state_name;
	private Integer level;
	private String addtime;
	private Integer isdispatch;
	private Integer devid;
	private String devnum;
	private String code;
	private String lat;
	private String lng;
	private String pictureUrl;
	private String instructions;
	private String voiceUrl;
}
