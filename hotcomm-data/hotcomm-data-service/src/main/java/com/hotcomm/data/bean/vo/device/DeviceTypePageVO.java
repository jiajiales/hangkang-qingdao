package com.hotcomm.data.bean.vo.device;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceTypePageVO {

	private Integer typeId; // 设备类型-编号

	private String typeName; // 类型名称

	private Date createTime; // 创建时间

	private String createUser; // 创建人员

	private Date updateTime; // 修改时间

	private String code;

}
