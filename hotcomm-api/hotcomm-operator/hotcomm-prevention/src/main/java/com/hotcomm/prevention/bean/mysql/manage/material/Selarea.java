package com.hotcomm.prevention.bean.mysql.manage.material;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Selarea implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String areacode;

	private String addvb;
}
