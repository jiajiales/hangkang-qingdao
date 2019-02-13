package com.hotcomm.data.bean.params.service.device;

import java.util.Date;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.comm.ParamsValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LoraOTAADeviceParam implements ParamsValidate {

	@FieldValidate(limit = "1,11", type = ParamType.NUMBER)
	private Integer deviceId;

	@FieldValidate(limit = "1,16", type = ParamType.ENGLISH_NUMBER)
	private String code;

	@FieldValidate(limit = "1,11", type = ParamType.NUMBER)
	private Integer type;

	@FieldValidate(limit = "1,150", type = ParamType.STRING)
	private String desc;

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer groupId;

	private Integer receiveNum;

	private Integer isDelete;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer status;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer protocol;

	private Integer iotTech;

	@FieldValidate(limit = "1,20", type = ParamType.CUSTOM, pattern = "^[a-zA-Z0-9-:]+$")
	private String mac;

	@FieldValidate(limit = "1,32", type = ParamType.ENGLISH_NUMBER)
	private String AppSKey;

	@FieldValidate(limit = "1,16", type = ParamType.ENGLISH_NUMBER)
	private String AppEUI;

}
