package com.hot.analysis.bean.dc;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DCIncome implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String DateDay;

	private int TotalMoney;
}
