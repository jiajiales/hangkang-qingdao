package com.hotcomm.prevention.bean.mysql.common.params;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SystemNoticeParams {
	private Integer pageSize;
	private Integer pageNum;
	private String starttime;
	private String endtime;
	private String context;//关键字：标题或来源
}
