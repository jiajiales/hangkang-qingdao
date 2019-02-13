package com.hotcomm.prevention.bean.mysql.common.vo;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.manage.group.entity.TDeviceGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ModuleItemNode {
	
	private Integer id;//模块ID
	private String groupname;//模块名称
	private String code;//模块编码
	private List<TDeviceGroup> children;
	

}
