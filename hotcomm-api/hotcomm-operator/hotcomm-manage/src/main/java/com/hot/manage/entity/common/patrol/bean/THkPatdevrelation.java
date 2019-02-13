package com.hot.manage.entity.common.patrol.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_hk_patdevrelation")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class THkPatdevrelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "userpatid")
	private Integer userpatid;

	@Column(name = "signid")
	private Integer signid;
}