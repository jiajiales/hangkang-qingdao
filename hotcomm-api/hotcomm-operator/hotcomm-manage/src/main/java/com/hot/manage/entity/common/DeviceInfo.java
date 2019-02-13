package com.hot.manage.entity.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer deviceid;
	private Integer moduleid;
	private String devnum;
	private String modulename;
	private String groupname;
	private String managername;
	private String addtime;
	private String addname;
	private String videoPath;
	private List<DeviceHandleTime> handleTime;

}
