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

@Table(name = "t_hk_material")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_material implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 物资点名称
	@Column(name = "materialname")
	private String materialname;
	// 物资点编号
	@Column(name = "num")
	private String num;
	// 物资点地址
	@Column(name = "code")
	private String code;
	// 物资点内容
	@Column(name = "msg")
	private String msg;
	// 纬度
	@Column(name = "lat")
	private String lat;
	// 经度
	@Column(name = "lng")
	private String lng;
	// 添加时间
	@Column(name = "addtime")
	private String addtime;
	// 更新时间
	@Column(name = "updatetime")
	private String updatetime;

	@Column(name = "isenable")
	private Integer isenable;

	@Column(name = "isdelete")
	private Integer isdelete;
	// 责任人1
	@Column(name = "usermaterialid1")
	private Integer usermaterialid1;
	// 责任人2
	@Column(name = "usermaterialid2")
	private Integer usermaterialid2;
	// 责任人3
	@Column(name = "usermaterialid3")
	private Integer usermaterialid3;
	// 行政编码
	@Column(name = "areacode")
	private String areacode;
}
