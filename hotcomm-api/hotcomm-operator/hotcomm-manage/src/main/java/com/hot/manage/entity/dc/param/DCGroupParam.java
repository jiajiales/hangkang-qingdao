package com.hot.manage.entity.dc.param;

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
@Table(name = "t_device_group")
public class DCGroupParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 项目组id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 模块id
	@Column(name = "moduleid")
	private Integer moduleid;

	// 项目名称
	@Column(name = "groupname")
	private String groupname;

	// 父类id
	@Column(name = "fatherid")
	private Integer fatherid;

	// 项目地址
	@Column(name = "groupcode")
	private String groupcode;

	// 经度
	@Column(name = "x")
	private String x;

	// 纬度
	@Column(name = "y")
	private String y;

	// 坐标类型
	@Column(name = "coordinate")
	private Integer coordinate;

	// 是否能用
	@Column(name = "isenable")
	private Integer isenable;

	// 是否删除
	@Column(name = "isdelete")
	private Integer isdelete;

	// 添加时间
	@Column(name = "addtime")
	private String addtime;

	// 添加人id
	@Column(name = "adduserid")
	private Integer adduserid;

	// 责任人
	@Column(name = "managerid")
	private Integer managerid;

	// 城市id
	@Column(name = "cityid")
	private Integer cityid;

	// 电话
	@Column(name = "telephone")
	private String telephone;

	// 设备数量
	@Column(name = "count")
	private Integer count;

	// 图片路径
	@Column(name = "imgpath")
	private String imgpath;

	// 项目编号
	@Column(name = "itemnum")
	private String itemnum;

}
