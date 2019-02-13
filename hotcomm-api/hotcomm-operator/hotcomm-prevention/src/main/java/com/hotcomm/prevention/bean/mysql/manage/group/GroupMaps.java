package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupMaps implements Serializable {
	private static final long serialVersionUID = -2791553944087024149L;
	private String picnum;
	private String picpath;
	private String site;
	private Integer id;;
}
