package com.hot.manage.entity;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MenuCategoryNode {
	
	private Integer id;
	private String label;//描述名字
	private String addtime;
	private Integer adduserid;
	private Integer type;
	private String url;
	private String ico;
	private Integer fatherid;
	private Integer order;
	private String annotation;
	private String name;
	private Boolean isenable;
	private List<?> children;
}
