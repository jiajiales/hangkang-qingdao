package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignLogPageInfoVO {
	private Integer id;
	private String devnum;
	private String groupname;
	private String address;
	private String patName;
	private String addtime;
	private Integer signstate;
	private String stateDetail;
	private String stateName;
	private String picaddr;
	private Integer signID;
}
