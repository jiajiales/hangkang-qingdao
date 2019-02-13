package com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule;

import com.hotcomm.prevention.bean.mysql.manage.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGLogParam extends PageParam {
	private String starttime;
	private String endtime;
	private String mac;

}
