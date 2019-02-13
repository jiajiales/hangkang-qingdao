package com.hotcomm.prevention.bean.mysql.datashow.sj;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SeleteMap implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String picpath;
	private String site;
	private Integer itemid;
}
