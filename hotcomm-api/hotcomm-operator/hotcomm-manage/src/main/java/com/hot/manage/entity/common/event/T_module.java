package com.hot.manage.entity.common.event;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer moduleid;

	private String modulename;

}
