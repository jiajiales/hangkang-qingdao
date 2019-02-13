package com.hotcomm.framework.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月26日 下午3:57:02
 */
public class DateUtils {
	
	public static final byte YEAR = 0x01;
	public static final byte MONTH = 0x02;
	public static final byte DAY = 0x03;
	public static final byte HOUR = 0x04;
	public static final byte MINUTE = 0x05;
	public static final byte SECOND = 0x06;
	
	public static final byte ADD = 0X01; 
	public static final byte DEL = 0X02;
	
	/**
	 * 单个日期加减- 年|月|日|时|分|秒 
	 * @param date
	 * @param timeUnit
	 * @param methodUnit
	 * @param amount
	 * @return
	 */
	public static Date converTime(Date date,byte timeUnit,byte methodUnit,int amount) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		amount = methodUnit==DateUtils.ADD ?amount:-amount;
		switch (timeUnit) {
		case 0x1:
			cal.add(Calendar.YEAR, amount);
			break;
		case 0x02:
			cal.add(Calendar.MONTH, amount);
			break;
		case 0x03:
			cal.add(Calendar.DAY_OF_MONTH, amount);
			break;
		case 0x04:
			cal.add(Calendar.HOUR, amount);
			break;
		case 0x05:
			cal.add(Calendar.MINUTE, amount);
			break;
		default:
			cal.add(Calendar.SECOND, amount);
			break;
		}
		date = cal.getTime();
		return date;
	}
	
}
