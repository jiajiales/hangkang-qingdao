package com.hot.parse.entity.common;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "t_dev_alarm")
public class DevAlarm implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(name = "deviceid")
	public Integer deviceid;

	@Column(name = "mac")
	public String mac;

	@Column(name = "alarmstateid")
	public Integer alarmstateid;

	@Column(name = "recvtime")
	public String recvtime;

	@Column(name = "addtime")
	public String addtime;

	@Column(name = "handler")
	public Integer handler;

	@Column(name = "handlestate")
	public Integer handlestate;

	@Column(name = "isdispatch")
	public Integer isdispatch;

	@Column(name = "handleresult")
	public String handleresult;

	@Column(name = "handleTime")
	public String handleTime;

	@Column(name = "remark")
	public String remark;

	@Column(name = "moduleid")
	public Integer moduleid;
}
