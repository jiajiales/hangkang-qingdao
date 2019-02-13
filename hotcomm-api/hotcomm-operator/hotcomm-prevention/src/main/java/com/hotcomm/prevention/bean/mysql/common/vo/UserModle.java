package com.hotcomm.prevention.bean.mysql.common.vo;
import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.entity.TPower;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserModle {
	private Integer id;
	private String username;
	private String token;
	private Integer roleid;//角色ID
	private List<TPower> powers;

}
