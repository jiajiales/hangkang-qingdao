package com.hotcomm.prevention.bean.mysql.manage.patrol.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_hk_patdevrelationdevice")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THkPatdevrelationdevice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	@Column(name="userpatid")
    private Integer userpatid;
	@Column(name="signdeviceid")
    private Integer signdeviceid;
	@Column(name="isdelete")
    private Boolean isdelete;

}