package com.hot.manage.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_power")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TPower implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "description")
	private String description;

	@Column(name = "moduleid")
	private Integer moduleid;

	@Column(name = "isenable")
	private Boolean isenable;

	@Column(name = "isdelete")
	private Boolean isdelete;

	@Column(name = "addtime")
	private String addtime;

	@Column(name = "adduserid")
	private Integer adduserid;

	@Column(name = "type")
	private Integer type;

	@Column(name = "url")
	private String url;

	@Column(name = "ico")
	private String ico;

	@Column(name = "fatherid")
	private Integer fatherid;

	@Column(name = "order")
	private Integer order;

	@Column(name = "annotation")
	private String annotation;
}