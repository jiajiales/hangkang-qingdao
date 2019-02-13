package com.hot.manage.entity.item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_device_group")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TDeviceGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="moduleid")
    private Integer moduleid;
	
	@Column(name="groupname")
    private String groupname;
	
	@Column(name="fatherid")
    private Integer fatherid;
	
	@Column(name="groupcode")
    private String groupcode;
	
	@Column(name="x")
    private String x;
	
	@Column(name="y")
    private String y;
	
	@Column(name="coordinate")
    private Integer coordinate;
	
	@Column(name="isenable")
    private Boolean isenable;
	
	@Column(name="isdelete")
    private Boolean isdelete;
	
	@Column(name="addtime")
    private String addtime;
	
	@Column(name="adduserid")
    private Integer adduserid;
	
	@Column(name="managerid")
	private Integer managerid;
	
	@Column(name="cityid")
    private Integer cityid;
	
	@Column(name="telephone")
    private String telephone;
	
	@Column(name="count")
    private Integer count;
	
	@Column(name="imgpath")
	private String imgpath;
	
	@Column(name="itemnum")
    private String itemnum;
}