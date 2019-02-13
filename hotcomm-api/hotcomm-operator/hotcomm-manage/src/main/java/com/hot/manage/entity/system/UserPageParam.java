package com.hot.manage.entity.system;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserPageParam extends PageParam {
	private String telephone;//电话
	private String usernum;//用户编号
	private Integer status;//1：可用；0：不可用
}
