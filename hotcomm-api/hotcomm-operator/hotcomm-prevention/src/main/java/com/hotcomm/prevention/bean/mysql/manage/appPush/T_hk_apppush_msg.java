package com.hotcomm.prevention.bean.mysql.manage.appPush;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_hk_apppush_msg")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_apppush_msg implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // id

	@Column(name = "title")
	private String title;// 标题

	@Column(name = "content")
	private String content;// 消息内容

	@Column(name = "message")
	private String message;// 附带消息

	@Column(name = "regids")
	private String regids;// regid

	@Column(name = "timeToLive")
	private String timeToLive;// 消息保存时间

	@Column(name = "userid")
	private int userid;// 用户id
}
