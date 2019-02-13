package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SiteInfoByTypeVO implements Serializable {

//	站点类型输出类
	
	private static final long serialVersionUID = 1L;

	private String sttp;

	private String stcd;
	
	private String stnm;

}
