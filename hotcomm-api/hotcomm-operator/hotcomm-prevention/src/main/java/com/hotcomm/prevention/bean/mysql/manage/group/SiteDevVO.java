package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

public class SiteDevVO implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	    private Double x;
	    private Double y ;
	    private Integer state;
	    private String code;
	    private String devnum;
	    private String battery;
	    private String contacts;
	    private String lng;
	    private String lat;
}
