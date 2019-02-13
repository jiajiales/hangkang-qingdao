package com.hotcomm.prevention.bean.mysql.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserItemParam {

	private Integer id;//被绑定人ID
	private String groupids;
	private Integer adduserid;
	private String addtime;
	private Boolean isenable;
	private Boolean isdelete;
}
