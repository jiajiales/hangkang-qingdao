package com.hot.parse.entity.ywj;

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
@Table(name = "log_ywj")
public class Log_ywj implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(name = "IMEI")
	public String IMEI;

	@Column(name = "Name")
	public String Name;

	@Column(name = "MsgType")
	public String MsgType;

	@Column(name = "MsgLen")
	public String MsgLen;

	@Column(name = "MsgBody")
	public String MsgBody;

	@Column(name = "LastValue")
	public String LastValue;

	@Column(name = "DataStr")
	public String DataStr;

	@Column(name = "addtime")
	public String addtime;

}
