package com.hot.manage.entity.common.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name="t_hk_evdevrelation")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_evdevrelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// id
	private Integer id;

	@Column(name = "eventid")
	// 事件id
	private Integer eventid;

	@Column(name = "devid")
	// 设备id
	private Integer devid;

}
