package com.hot.manage.entity.common.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PowerModel {
	private Integer id;
	private String description;
	private Integer moduleid;
	private Integer type;
	private String url;
	private Integer fatherid;
	private Integer order;
	private String ico;
	private String annotation;

}
