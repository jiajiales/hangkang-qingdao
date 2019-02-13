package com.hot.manage.entity.item;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ItemParam extends PageParam{
	
	private Integer moduleid;

	private String starttime;

	private String endtime;
	
	private String  context;//关键字

}
