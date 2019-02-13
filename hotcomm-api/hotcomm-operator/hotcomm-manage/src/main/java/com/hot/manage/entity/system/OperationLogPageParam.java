package com.hot.manage.entity.system;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class OperationLogPageParam extends PageParam {
	
	private String starttime;
	private String endtime;
	private String loginname;//登陆账号

}
