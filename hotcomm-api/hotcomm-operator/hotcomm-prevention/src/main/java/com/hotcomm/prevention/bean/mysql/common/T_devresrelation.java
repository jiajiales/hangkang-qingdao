package com.hotcomm.prevention.bean.mysql.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "t_devresrelation")
public class T_devresrelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "devid")
	private Integer devid;

	@Column(name = "moduleid")
	private Integer moduleid;

	@Column(name = "url")
	private String url;

	@Column(name = "addtime")
	private String addtime;
}
