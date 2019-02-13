package com.hot.manage.entity.hw.vaule;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWAddGroupVauleSite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String picnum;
	private String picpath;
	private String site;
}
