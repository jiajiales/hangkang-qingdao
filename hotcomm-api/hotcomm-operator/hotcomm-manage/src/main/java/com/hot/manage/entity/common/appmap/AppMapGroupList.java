package com.hot.manage.entity.common.appmap;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppMapGroupList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer groupid;

	private String groupname;

	private Integer moduleid;

	private String name;

	private String x;

	private String y;
}
