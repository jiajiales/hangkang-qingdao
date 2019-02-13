package com.hot.manage.entity.common.patrol.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_hk_signdevice")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THkSigndevice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	@Column(name="moduleid")
    private Integer moduleid;
	@Column(name="deviceid")
    private Integer deviceid;
	@Column(name="isdelete")
    private Boolean isdelete;
	@Column(name="lastsigntime")
    private String lastsigntime;//最后签到时间
	@Column(name="addtime")
    private String addtime;//添加为签到设备时间

}