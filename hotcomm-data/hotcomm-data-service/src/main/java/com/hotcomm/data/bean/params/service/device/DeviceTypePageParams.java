package com.hotcomm.data.bean.params.service.device;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceTypePageParams extends PageParams {

	private Integer typeId; // 设备类型-编号

	private String typeName; // 类型名称

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime; // 创建时间

	private String createUser; // 创建人员

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime; // 修改时间

}
