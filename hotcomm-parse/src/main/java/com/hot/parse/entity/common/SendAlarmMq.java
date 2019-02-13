package com.hot.parse.entity.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SendAlarmMq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer deviceid;
	private Integer id;
	private String mac;
	private Integer alarmstateid;
	private String recvtime;
	private String addtime;
	private Integer moduleid;
}
