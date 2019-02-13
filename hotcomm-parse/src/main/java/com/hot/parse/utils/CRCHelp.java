package com.hot.parse.utils;

public class CRCHelp {
	public static String getCRC16_CCITT(String Source) {
		int crc = 0x1E50; // initial value
		int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)
		String tmp = "";
		byte[] bytes = new byte[Source.length() / 2];
		for (int i = 0; i < Source.length() - 1; i++) {
			if (i % 2 == 0) {
				tmp = Source.substring(i, i + 2);
				// Integer.parseUnsignedInt16进制转int
				bytes[i / 2] = (byte) Integer.parseUnsignedInt(tmp, 16);
			}
		}
		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		// Integer.toHexString16进制转字符串方法
		String strDest = Integer.toHexString(crc);
		return strDest;
	}

	public static String getCrc16Code(String crcString) {

		// 转换成字节数组
		byte[] creBytes = HexString2Bytes(crcString);

		// 开始crc16校验码计算
		CRC16Util crc16 = new CRC16Util();
		crc16.reset();
		crc16.update(creBytes);
		int crc = crc16.getCrcValue();
		// 16进制的CRC码
		String crcCode = String.valueOf(Integer.parseInt(String.valueOf(crc), 16)).toUpperCase();
		// 补足到4位
		if (crcCode.length() < 4) {
			// crcCode = StringUtil.lefgPadding(crcCode, '0', 4);
			crcCode = padLeft(crcCode, 4, '0');
		}
		return crcCode;
	}

	public static String RealHexToStr(String str) {
		String hText = "0123456789ABCDEF";
		StringBuilder bin = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			bin.append(hText.charAt(str.charAt(i) / 16)).append(hText.charAt(str.charAt(i) % 16)).append(' ');
		}
		return bin.toString();
	}

	/**
	 * 十六进制字符串转换成字节数组
	 * 
	 * @param hexstr
	 * @return
	 */
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 16进制char转换成整型
	 * 
	 * @param c
	 * @return
	 */
	public static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 字节数组转为十六进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String ByteArrayToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 3);
		for (byte b : data)
			sb.append(padRight(padLeft(String.valueOf(b), 2, '0'), 3, ' '));
		return sb.toString().toUpperCase();
	}

	/**
	 * 
	 * @功能 String左对齐
	 */
	public static String padLeft(String src, int len, char ch) {
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] charr = new char[len];
		System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
		for (int i = src.length(); i < len; i++) {
			charr[i] = ch;
		}
		return new String(charr);
	}

	/**
	 * @功能 String右对齐
	 */
	public static String padRight(String src, int len, char ch) {
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] charr = new char[len];
		System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
		for (int i = 0; i < diff; i++) {
			charr[i] = ch;
		}
		return new String(charr);
	}
}
