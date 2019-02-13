package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupListDev implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private double x;
	private double y;
	private Integer state;
	private String site;
	private String picpath;

}
