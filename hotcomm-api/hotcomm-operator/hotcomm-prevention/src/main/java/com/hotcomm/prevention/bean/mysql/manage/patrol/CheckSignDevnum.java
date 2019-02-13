package com.hotcomm.prevention.bean.mysql.manage.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CheckSignDevnum {
	private String devnum;
	private Integer id;
}
