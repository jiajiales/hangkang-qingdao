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
@Table(name="t_chat_user_group")
public class TChatUserGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	@Column(name="groupid")
    private Integer groupid;//群ID
	@Column(name="userid")
    private Integer userid;//群成员ID
	@Column(name="addtime")
    private Date addtime;
	@Column(name="isdelete")
    private Boolean isdelete;
}