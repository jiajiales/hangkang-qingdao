package com.hot.manage.entity;

import java.util.List;

import com.hot.manage.entity.item.TDeviceGroup;

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
