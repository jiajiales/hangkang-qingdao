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
@Table(name="t_chat_group")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TChatGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	@Column(name="group_userid")
    private Integer groupUserid;//群主ID
	@Column(name="groupname")
    private String groupname;//群名称
	@Column(name="createtime")
    private Date createtime;
	@Column(name="isdelete")
    private Boolean isdelete;
}