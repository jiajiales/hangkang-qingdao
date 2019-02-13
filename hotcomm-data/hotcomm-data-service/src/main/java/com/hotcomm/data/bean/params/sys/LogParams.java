package com.hotcomm.data.bean.params.sys;

import com.hotcomm.data.bean.params.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LogParams extends PageParams {

	private String startTime;

	private String endTime;

	private String recordUser;

}
