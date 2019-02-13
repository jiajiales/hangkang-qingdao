package com.hotcomm.framework.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author yuanyuanxing 979319462@qq.com
 * @date 2018年4月19日 上午8:46:30
 */
public class RegexUtils {

	// 纯数字
	public static final String NUMBER = "^\\d+$";

	// 纯英文、纯数字或英文数字混合
	public static final String ENGLISH_NUMBER = "^[A-Za-z0-9]+$";

	// 中文姓名或英文姓名（英文姓名可包含空格或字符“·”）
	public static final String NAME = "(^[\\u4E00-\\u9FA5]+$)|(^[a-zA-Z·\\s]+$)";

	// 邮箱
	public static final String EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

	// 特殊字符
	public static final String SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	/**
	 * 验证是否为纯数字
	 */
	public static boolean isNumber(String num) {
		return Pattern.matches(NUMBER, num);
	}

	/**
	 * 验证是否为纯英文、纯数字或英文数字混合
	 */
	public static boolean isEnglishNumber(String englishNumber) {
		return Pattern.matches(ENGLISH_NUMBER, englishNumber);
	}

	/**
	 * 验证是否为中英文姓名
	 */
	public static boolean isName(String name) {
		return Pattern.matches(NAME, name);
	}

	/**
	 * 验证是否为邮箱
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(EMAIL, email);
	}

	/**
	 * 验证是否包含特殊字符
	 */
	public static boolean isContainSpecial(String str) {
		return Pattern.compile(SPECIAL).matcher(str).find();
	}

}
