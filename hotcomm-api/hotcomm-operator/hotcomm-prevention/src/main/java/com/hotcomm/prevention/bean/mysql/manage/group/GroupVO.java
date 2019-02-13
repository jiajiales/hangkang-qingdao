package com.hotcomm.prevention.bean.mysql.manage.group;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupVO {
   
	
	private Integer moduleid;
	
	private String groupname;
	
	private String   groupcode;
	
}
