package com.hotcomm.prevention.bean.mysql.datashow.jg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WellCoverSpreadVO {
    private Integer id; 
	private double lat;
	private double lng;
	private Integer state;
	private String  contacts;
	private String  code;
	private String   telephone;
    private String	 devnum;
}
