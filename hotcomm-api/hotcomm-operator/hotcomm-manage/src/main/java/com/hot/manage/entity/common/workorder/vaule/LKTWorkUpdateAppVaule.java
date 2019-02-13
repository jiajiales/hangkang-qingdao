package com.hot.manage.entity.common.workorder.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkUpdateAppVaule {

	private Integer woid;

	private Integer own_id;

	private Integer devid;

	private String devnum;
	
	private Integer handleType;

	private String pictureurl;

	private String voiceurl;

	private String remark;

	private Integer userid;
	
	private Integer devtype;

	private Integer moduleid;
}
