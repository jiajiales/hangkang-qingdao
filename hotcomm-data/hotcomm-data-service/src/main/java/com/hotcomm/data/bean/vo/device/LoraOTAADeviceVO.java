package com.hotcomm.data.bean.vo.device;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LoraOTAADeviceVO {
	
	private Integer deviceId;

	private String code;

	private Integer type;

	private String desc;

	private Integer groupId;

	private Integer receiveNum;

	private Integer isDelete;

	private Integer status;

	private Date createTime;

	private String createUser;

	private Date updateTime;
	
	private Integer protocol;
	
	private Integer iotTech;
	
	private String AppSKey;
	
	private String AppEUI;
	
	private String networkType;
	
	private String mac;
	
	private String lora;
	
	private String groupName;
	
	private String typeName;
	
}
