package com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChangeOwn {

	private List<Integer> devid;

	private Integer moduleid;

	private Integer ownid;

}
