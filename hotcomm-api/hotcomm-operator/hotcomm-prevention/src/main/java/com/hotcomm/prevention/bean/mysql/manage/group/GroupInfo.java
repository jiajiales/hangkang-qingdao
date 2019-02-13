package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupInfo implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	    private Double x;
	    private Double y;
	    private String groupname;
	    private String contacts;
	    private Integer devcount;
	    private Integer armcount;
	    private Integer faultcount;
}
