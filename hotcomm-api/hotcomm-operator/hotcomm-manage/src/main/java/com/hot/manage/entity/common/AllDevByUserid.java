package com.hot.manage.entity.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AllDevByUserid implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mac;

	private Integer deviceid;
	
	private String relationGroupName;

	private Integer relationGroupid;

}
