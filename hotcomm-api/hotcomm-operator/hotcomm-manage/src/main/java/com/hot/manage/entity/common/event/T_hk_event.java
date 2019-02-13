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

@Table(name = "t_hk_event")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// id
	private Integer id;

	@Column(name = "`code`")
	// 事件编号
	private String code;

	@Column(name = "isdispatch")
	// 是否派工(0:不需要;1:需要)
	private Integer isdispatch;

	@Column(name = "state")
	// 事件处理状态(0:未处理;1:已派单;2:挂起;3:已处理)
	private Integer state;

	@Column(name = "`describe`")
	// 状态id(对应t_alarm_state的id)
	private Integer describe;

	@Column(name = "`level`")
	// 紧急情况(1,无明确要求时间;2,3天内处理;3,1天内处理;4,8个小时内处理;5,必须立即处理)
	private Integer level;

	@Column(name = "instructions")
	// 详细说明
	private String instructions;

	@Column(name = "adduserid")
	// 添加人id
	private Integer adduserid;

	@Column(name = "addtime")
	// 添加时间
	private String addtime;

	@Column(name = "moduleid")
	// 添加模块id
	private Integer moduleid;
}
