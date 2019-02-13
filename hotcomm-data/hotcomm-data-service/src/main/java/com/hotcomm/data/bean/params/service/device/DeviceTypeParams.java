package com.hotcomm.data.bean.params.service.device;

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
public class DeviceTypeParams implements ParamsValidate {

	private Integer typeId; // 设备类型-编号

	@FieldValidate(limit = "1,100", type = ParamType.CUSTOM, pattern="^[\\u4e00-\\u9fa5a-zA-Z0-9.()（）]+$")
	private String typeName; // 类型名称

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime; // 创建时间

	@FieldValidate(limit = "1,50", type = ParamType.STRING)
	private String createUser; // 创建人员

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime; // 修改时间

	@FieldValidate(limit = "1,20", type = ParamType.ENGLISH_NUMBER)
	private String code;

}
