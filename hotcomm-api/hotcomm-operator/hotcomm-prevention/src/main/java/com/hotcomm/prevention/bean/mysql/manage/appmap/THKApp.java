package com.hotcomm.prevention.bean.mysql.manage.appmap;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_hk_app")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THKApp {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="vsersion")
	private Integer vsersion;//版本号
	@Column(name="url")
	private String url;//存放路径
	@Column(name="versionName")
	private String versionname;//版本名称

}
