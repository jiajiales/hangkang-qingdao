package com.hot.manage.entity.common.appmap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignlogList {
	
	private Integer signstate;

	private String addtime;
	
	private Integer devnum;

	private String address;

	private String picaddr;
}
