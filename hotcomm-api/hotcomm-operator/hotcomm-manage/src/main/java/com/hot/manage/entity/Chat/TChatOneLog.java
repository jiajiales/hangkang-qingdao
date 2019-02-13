package com.hot.manage.entity.Chat;

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
@Table(name = "t_chat_one_log")
public class TChatOneLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "senderid")
	private String senderid;

	@Column(name = "targetid")
	private String targetid;

	@Column(name = "type")
	private Integer type;

	@Column(name = "txtcontent")
	private String txtcontent;

	@Column(name = "sendTime")
	private String sendTime;
	
	@Column(name="resoureurl")
	private String resoureurl;
	
	@Column(name="duration")
	private long duration;
}
