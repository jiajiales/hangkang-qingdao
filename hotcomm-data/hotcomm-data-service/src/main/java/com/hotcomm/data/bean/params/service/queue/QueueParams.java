package com.hotcomm.data.bean.params.service.queue;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.comm.ParamsValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueueParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer queueId;

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer memberId;

	@FieldValidate(limit = "2,50", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9._]+$")
	private String queueName;

	@FieldValidate(limit = "1,2", type = ParamType.NUMBER)
	private Integer type;

	@FieldValidate(limit = "2,30", type = ParamType.STRING)
	private String memberName;

	@FieldValidate(limit = "5,16", type = ParamType.STRING)
	private String password;

	@FieldValidate(limit = "1,30", type = ParamType.STRING)
	private String realName;

	@FieldValidate(limit = "11", type = ParamType.NUMBER)
	private String telephone;

	@FieldValidate(limit = "5,30", type = ParamType.EMAIL)
	private String email;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date holeTime;

	@FieldValidate(limit = "1,19", type = ParamType.NUMBER)
	private Long sendFilterInterval;

	@FieldValidate(limit = "2,35", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9./_]+$")
	private String vhost;

	@FieldValidate(limit = "2,30", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9./_]+$")
	private String vhostAccount;

	@FieldValidate(limit = "5,30", type = ParamType.ENGLISH_NUMBER)
	private String vhostPassword;

	private Date createTime;

	private Date updateTime;

	private String createUser;

	@FieldValidate(limit = "1,100", type = ParamType.STRING)
	private String remark;

}
