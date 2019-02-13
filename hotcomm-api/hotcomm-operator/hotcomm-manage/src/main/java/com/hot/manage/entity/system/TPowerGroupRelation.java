package com.hot.manage.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_power_group_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TPowerGroupRelation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "powerid")
	private Integer powerid;

	@Column(name = "groupid")
	private Integer groupid;
}