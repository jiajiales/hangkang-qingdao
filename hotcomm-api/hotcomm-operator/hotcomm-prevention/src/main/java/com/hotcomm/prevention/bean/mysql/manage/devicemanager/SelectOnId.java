package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SelectOnId extends T_device_all implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String contacts;
	private Integer groupid;
	private String groupname;
	private Integer itempicid;
	private String picpath;
	private String site;
	private String videos;
}
