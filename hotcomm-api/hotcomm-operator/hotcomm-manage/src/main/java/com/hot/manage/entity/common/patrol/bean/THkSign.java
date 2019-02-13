package com.hot.manage.entity.common.patrol.bean;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_hk_sign")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THkSign {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;	
	@Column(name="itemid")
    private Integer itemid;//项目ID
	@Column(name="address")
    private String address;
	@Column(name="lng")
    private String lng;
	@Column(name="lat")
    private String lat;
	@Column(name="devnum")
    private String devnum;
	@Column(name="isdelete")
    private Boolean isdelete;
	@Column(name="lastsigntime")
    private String lastsigntime;
	@Column(name="addtime")
    private String addtime;

}