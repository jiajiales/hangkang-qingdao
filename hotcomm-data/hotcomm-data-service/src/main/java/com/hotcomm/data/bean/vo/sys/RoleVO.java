package com.hotcomm.data.bean.vo.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleVO {

	private Integer id;

	private String desc;

	private String roleName;

	private Integer status;

}
