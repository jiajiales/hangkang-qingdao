package com.hotcomm.prevention.bean.mysql.common.params;
import com.hotcomm.prevention.bean.mysql.manage.PageParam;
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
