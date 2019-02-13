package com.hot.manage.entity.common.appmap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignInfo {
	
	private Integer userid;

	private Integer signid;
 
	private String devnum; 
	
	private String lat;

	private String lng;

	private String addtime;

	private Integer type;
	
	private String picaddr;
	
	private Integer signstate;
}
