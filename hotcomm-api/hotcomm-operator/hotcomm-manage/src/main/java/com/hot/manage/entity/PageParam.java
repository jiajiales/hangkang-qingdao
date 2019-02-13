package com.hot.manage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PageParam {
	
	private Integer pageNum;
	private Integer pageSize;
	private Integer userid;
	
	
	

}
