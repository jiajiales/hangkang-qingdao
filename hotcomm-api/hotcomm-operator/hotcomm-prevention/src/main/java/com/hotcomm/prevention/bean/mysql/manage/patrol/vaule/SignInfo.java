package com.hotcomm.prevention.bean.mysql.manage.patrol.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignInfo {

	private Integer userid;

	private Integer signid;

	private String devnum;

	private String lat;

	private String lng;

	private String addtime;

	private Integer type;

	private String picaddr;

	private Integer signstate;
}
