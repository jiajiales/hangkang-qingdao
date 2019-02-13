package com.hotcomm.prevention.bean.mysql.manage.material.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_hk_usermaterial")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_usermaterial implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "userid")
	private Integer adduserid;
	
	@Column(name = "addtime")
	private String addtime;
	
	@Column(name = "isenable")
	private Integer isenable;

	@Column(name = "isdelete")
	private Integer isdelete;
}
