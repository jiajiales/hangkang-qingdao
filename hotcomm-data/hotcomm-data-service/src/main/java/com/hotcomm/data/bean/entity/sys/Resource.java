package com.hotcomm.data.bean.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "sys_resource")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "res_path")
	private String path;

	@Column(name = "res_type")
	private Integer type;

	@Column(name = "res_name")
	private String name;

	@Column(name = "res_weight")
	private Integer weight;

	@Column(name = "res_status")
	private Integer status;

	@Column(name = "res_pid")
	private Integer pid;

	@Column(name = "res_key")
	private String key;

}
