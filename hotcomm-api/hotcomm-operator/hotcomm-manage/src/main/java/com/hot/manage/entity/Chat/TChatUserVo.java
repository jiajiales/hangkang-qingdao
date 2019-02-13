package com.hot.manage.entity.Chat;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "t_chat_user")
public class TChatUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userid;

	private String username;

	private String userpicpath;

}
