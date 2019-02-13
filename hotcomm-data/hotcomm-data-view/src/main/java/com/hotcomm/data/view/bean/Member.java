package com.hotcomm.data.view.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Member  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String memberName;
	
	private Integer status;
	
	private String email;
	
	private String realName;
	
	private String telephone;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String createUser;
	
	private Integer userType;
	
	private Object roles;

}
