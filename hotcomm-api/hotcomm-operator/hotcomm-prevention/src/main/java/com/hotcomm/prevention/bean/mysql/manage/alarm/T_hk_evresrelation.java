package com.hotcomm.prevention.bean.mysql.manage.alarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_hk_evresrelation")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_evresrelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// id
	private Integer id;

	@Column(name = "eventid")
	// 事件id
	private Integer eventid;

	@Column(name = "resourcestype")
	// 资源类型(1：图片；2：音频；3：视频)
	private Integer resourcestype;

	@Column(name = "type")
	// 类型(1事件，2报警)
	private Integer type;

	@Column(name = "url")
	// 资源路径
	private String url;

	@Column(name = "moduleid")
	// 模块id
	private Integer moduleid;
}
