package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmHandleParam implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer alarmid;
	
	private Integer handleresult;
	
	private String remark;
	
	private Integer isdispatch;
	
	private String pictureUrl;
	
	private String voiceUrl;
}
