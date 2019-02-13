package com.hotcomm.prevention.bean.mysql.datashow.dc;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DCParkingSlotsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer inNum;

	private Integer outNum;

	private String timeInfo;

}
