package com.hot.manage.entity.Chat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Table(name="t_chat_group_log")
public class TChatGroupLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	@Column(name="senderid")
    private String senderid;
	@Column(name="groupid")
    private String groupid;
	@Column(name="addtime")
    private Date addtime;
	@Column(name="isdelete")
    private Boolean isdelete;
	@Column(name="textcontent")
    private String textcontent;
	@Column(name="imagecontent")
	private String imagecontent;
	@Column(name="imgurl")
	private String imgurl;
	@Column(name="duration")
	private String duration;
	@Column(name="videocontent")
	private String videocontent;
	@Column(name="type")
	private Integer type;
}