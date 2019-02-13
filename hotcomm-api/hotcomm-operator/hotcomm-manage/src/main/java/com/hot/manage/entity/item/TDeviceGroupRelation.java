package com.hot.manage.entity.item;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_device_group_relation")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TDeviceGroupRelation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="deviceid")
    private Integer deviceid;
	
	@Column(name="groupid")
    private Integer groupid;
	
	@Column(name="moduleid")
    private Integer moduleid;
	
	@Column(name="addtime")
    private String addtime;
    
	@Column(name="adduserid")
    private Integer adduserid;
    
	@Column(name="isenable")
    private Boolean isenable;
    
	@Column(name="isdelete")
    private Boolean isdelete;
}