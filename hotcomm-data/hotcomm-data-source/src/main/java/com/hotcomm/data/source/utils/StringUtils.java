package com.hotcomm.data.source.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(str2HexStr("0 产品型号描述"));
		System.out.println(hexStr2Str("3020E4BAA7E59381E59E8BE58FB7E68F8FE8BFB0"));
	}

	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	private static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

}
