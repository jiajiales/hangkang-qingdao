package com.hotcomm.data.bean.vo.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleResourceVO {

	private Integer roleId;

	private String resourceIds;

}
