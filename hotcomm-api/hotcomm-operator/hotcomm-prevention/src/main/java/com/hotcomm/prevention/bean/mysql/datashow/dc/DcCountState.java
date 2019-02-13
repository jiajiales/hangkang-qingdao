package com.hotcomm.prevention.bean.mysql.datashow.dc;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DcCountState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String state;

	private Integer stateCount;

}
