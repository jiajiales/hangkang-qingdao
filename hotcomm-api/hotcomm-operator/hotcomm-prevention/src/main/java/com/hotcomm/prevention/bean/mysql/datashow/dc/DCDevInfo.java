package com.hotcomm.prevention.bean.mysql.datashow.dc;

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
	private Integer parkingCount; //停车次数
	private Integer rownum; //停车次数排名
	private Integer parkingMoneyCount; //停车收入
	private Integer MoneyRownum; //收入排名
	private Integer moduleid; 
	private Integer DAY; //运行天数
	private String ADDTIME; //添加时间
	private String devnum; 
	private List<Integer> devSixMonthIncome; //当前设备六个月收入
	private List<String> allDevSixMonthaverageIncome; //所有设备六个月平均收入
	private List<Integer> devSixMonthTimes; //当前设备六个月停车次数
	private List<String> allDevSixMonthaverageTimes; //所有设备六个月平均停车次数
}
