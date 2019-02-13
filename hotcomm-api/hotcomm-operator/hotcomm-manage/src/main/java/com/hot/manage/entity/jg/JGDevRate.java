package com.hot.manage.entity.jg;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class JGDevRate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer moduleid;
	
	private String lat;
	
	private String lng;
	
	private Integer devid;
	

}
