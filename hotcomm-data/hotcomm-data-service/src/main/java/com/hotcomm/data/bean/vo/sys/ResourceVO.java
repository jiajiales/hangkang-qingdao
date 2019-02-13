package com.hotcomm.data.bean.vo.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResourceVO {

	private Integer id;

	private String path;

	private Integer type;

	private String name;

	private Integer weight;

	private Integer status;

	private Integer pid;

	private String key;

	private String pname;

	private String text;

}
