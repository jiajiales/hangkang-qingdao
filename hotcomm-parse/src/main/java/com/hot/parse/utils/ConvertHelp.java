package com.hot.parse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertHelp {
	private static String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
			"1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	/**
	 * 字符串转16进制字节数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] strToToHexByte(String hexString) {
		hexString = hexString.replace(" ", "");
		if ((hexString.length() % 2) != 0)
			hexString += " ";
		byte[] returnBytes = new byte[hexString.length() / 2];
		for (int i = 0; i < returnBytes.length; i++)
			returnBytes[i] = (byte) Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
		return returnBytes;
	}

	/**
	 * 取整数的某一位 <param name="_Resource">要取某一位的整数</param>
	 * <param name="_Mask">要取的位置索引，自右至左为0-7</param>
	 * <returns>返回某一位的值（0或者1）</returns>
	 */
	public static int getIntegerSomeBit(int _Resource, int _Mask) {
		return _Resource >> _Mask & 1;
	}

	/**
	 * 将整数的某位置为0或1
	 * 
	 * @param _Mask整数的某位
	 * @param a整数
	 * @param flag是否置1，TURE表示置1，FALSE表示置0
	 * @return返回修改过的值
	 */
	public static int setIntegerSomeBit(int _Mask, int a, boolean flag) {
		if (flag) {
			a |= (0x1 << _Mask);
		} else {
			a &= ~(0x1 << _Mask);
		}
		return a;
	}

	/**
	 * 字节数组转16进制字符串 --无空格
	 * 
	 * @param bytes
	 * @return
	 */
	public static String StringParseByte(byte[] bytes) {
		String returnStr = "";
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				returnStr += String.format("%02x", new Integer(bytes[i] & 0xff));
			}
		}
		return returnStr;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getOnTime() {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(day);
	}

	/**
	 * 时区时间格式转换为yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param UTCString
	 * @return
	 */
	public static String UTCStringtODefaultString(String UTCString) {
		UTCString = UTCString.replace("Z", " UTC");
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = utcFormat.parse(UTCString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultFormat.format(date);
	}

	public static byte ConvertBCD(String b) {
		// 高四位
		byte b1 = (byte) (Byte.parseByte(b) / 10);
		// 低四位
		byte b2 = (byte) (Byte.parseByte(b) % 10);

		return (byte) ((b1 << 4) | b2);
	}

	/**
	 * 字符串转化成为16进制字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String strTo16(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 16进制字符串转10进制字符串 返回值以逗号分割
	 * 
	 * @param s
	 * @return
	 */
	public static String s16To10str(String s) {
		String str = "";
		for (int i = 0; i < (s.length() / 2); i++) {
			if ((i + 1) == (s.length() / 2)) {
				str = str + String.valueOf(Integer.parseInt(s.substring(i * 2), 16));
			} else {
				str = str + String.valueOf(Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16)) + ",";
			}
		}
		return str;
	}

	/**
	 * 字符串转ASCII码
	 * 
	 * @param value
	 * @return
	 */
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	/**
	 * ASCII码转字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	/**
	 * 16进制转二进制
	 * 
	 * @param src
	 * @return
	 */
	public static String bytes2BinaryStr(byte[] bArray) {
		String outStr = "";
		int pos = 0;
		for (byte b : bArray) {
			// 高四位
			pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// 低四位
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;
	}
}