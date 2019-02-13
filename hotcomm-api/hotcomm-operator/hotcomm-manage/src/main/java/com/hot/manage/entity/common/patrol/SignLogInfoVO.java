package com.hot.manage.entity.common.patrol;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignLogInfoVO {

	// 表头
	private List<String> titles;

	// 数据
	private List<SignLogPageInfoVO> info;
//	private List<List<Object>> rows;

	// 页签名称
	private String name;

}
