package com.hotcomm.prevention.bean.mysql.manage.material.vaule;

import java.io.Serializable;
import com.hotcomm.prevention.bean.mysql.manage.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SelmaterialListVaule extends PageParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value="开始时间,选填，不填默认搜索结束时间以前的所有参数，均不填则搜索全部数据")
	private String starttime;

	// (value="结束时间,选填，不填默认搜索开始时间至今的所有参数，均不填则搜索全部数据")
	private String endtime;

	// (value="编号，地址")
	private String numorcode;

	// (value="行政编号")
	private String areacode;
}
