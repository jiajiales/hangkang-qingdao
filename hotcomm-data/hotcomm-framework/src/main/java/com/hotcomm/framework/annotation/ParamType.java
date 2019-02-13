package com.hotcomm.framework.annotation;

public enum ParamType {

	NUMBER, // 纯数字
	STRING, // 不包含特殊字符
	ENGLISH_NUMBER, // 纯英文、纯数字或英文数字混合
	NAME, // 中文姓名或英文姓名
	EMAIL, // 邮箱
	CUSTOM; // 自定义

}
