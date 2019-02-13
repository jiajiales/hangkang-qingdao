package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkUntreatedcondition {

	//(value = "当前登录用户id")
	private Integer userid;

	//(value = "0：查询全部；1：查询近一个月")
	private Integer readtype;
	
	//(value = "选填；输入设备编号关键字或地址模糊查询")
	private String dvenumorcode;
}
