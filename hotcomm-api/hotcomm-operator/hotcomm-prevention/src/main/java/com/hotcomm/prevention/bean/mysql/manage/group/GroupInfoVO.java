package com.hotcomm.prevention.bean.mysql.manage.group;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupInfoVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String groupname;
    private Double x;
    private Double y;
    private String groupcode;
    private String itemnum;
    private Integer managerid;
    private String imgpath;
    private Integer areaid;
}
