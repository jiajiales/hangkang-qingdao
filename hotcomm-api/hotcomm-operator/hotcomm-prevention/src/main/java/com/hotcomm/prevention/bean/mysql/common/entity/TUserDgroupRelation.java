package com.hotcomm.prevention.bean.mysql.common.entity;

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
@Table(name = "t_user_dgroup_relation")
public class TUserDgroupRelation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "userid")
	private Integer userid;
	@Column(name = "devicegroupid")
	private Integer devicegroupid;
	@Column(name = "isenable")
	private Boolean isenable;
	@Column(name = "isdelete")
	private Boolean isdelete;
	@Column(name = "addtime")
	private String addtime;
	@Column(name = "adduserid")
	private Integer adduserid;
}