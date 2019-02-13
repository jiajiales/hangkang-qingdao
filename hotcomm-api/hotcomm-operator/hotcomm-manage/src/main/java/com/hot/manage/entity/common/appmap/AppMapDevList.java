package com.hot.manage.entity.common.appmap;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppMapDevList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer state;

	private double lat;

	private double lng;

	private Integer wonum;

	private Integer moduleid;
}
