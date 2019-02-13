package com.hot.analysis.bean.dc;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DCDevInfo implements Serializable { 
	private static final long serialVersionUID = 1L;
	private Integer devid;
	private Integer parkingCount;
	private Integer rownum;
//	private Integer Moneydevid;
	private Integer parkingMoneyCount;
	private Integer MoneyRownum;
	private Integer moduleid;
	private Integer DAY;
	private String ADDTIME;
	private String devnum;
	private List<Integer> devSixMonthIncome;
	private List<String> allDevSixMonthaverageIncome;
	private List<Integer> devSixMonthTimes;
	private List<String> allDevSixMonthaverageTimes;
}
