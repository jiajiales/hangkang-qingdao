package com.hotcomm.prevention.bean.mysql.manage.alarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_dev_alarm")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_dev_alarm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 报警id

	@Column(name = "deviceid")
	private Integer deviceid;// 设备ID

	@Column(name = "mac")
	private String mac;// 设备Mac

	@Column(name = "alarmstateid")
	private Integer alarmstateid;// 报警状态表关联ID

	@Column(name = "recvtime")
	private String recvtime;// 基站接收时间

	@Column(name = "addtime")
	private String addtime; // 数据添加时间

	@Column(name = "handler")
	private Integer handler;// 处理人ID

	@Column(name = "handlestate")
	private Integer handlestate; // 处理状态，0：未处理；1：处理中；2：已处理

	@Column(name = "isdispatch")
	private Integer isdispatch; // 是否确要派工，0：不需要；1：需要

	@Column(name = "handleresult")
	private Integer handleresult;// 处理结果(对应t_dev_handle_state)

	@Column(name = "handleTime")
	private String handleTime;// 处理时间

	@Column(name = "remark")
	private String remark;// 备注信息

	@Column(name = "moduleid")
	private Integer moduleid; // 模块id

}
