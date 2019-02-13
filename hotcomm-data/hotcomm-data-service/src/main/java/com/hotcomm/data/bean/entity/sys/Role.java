package com.hotcomm.data.bean.entity.sys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "sys_role")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "description")
	private String desc;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "status")
	private Integer status;

}
