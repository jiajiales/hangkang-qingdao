package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupMap  implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	    private String groupname;
	    private Double x;
	    private Double y;
	    private String contacts;
	    private String telephone;
	    private Integer devcount;
	    private Integer devcount0;
	    private Integer devcount1;
	    private Integer devcount2;
}
