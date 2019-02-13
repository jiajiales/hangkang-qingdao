package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupListVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String groupname;
    private String groupcode;
    private Double x;
    private Double y;
    private String itemnum;
    private String addtime;
    private String imgpath;
    private String contacts;
    private Integer devcount;
    private Integer areaid;
}
