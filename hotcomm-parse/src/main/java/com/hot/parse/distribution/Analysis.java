package com.hot.parse.distribution;

import com.hot.parse.entity.common.ReceiveModel;

public class Analysis {
	// 模块数据判断
	public static int GetBelongFun(ReceiveModel model) {
		int i = 0;
		String mac = model.getMacAddr().substring(8, 10);
		switch (mac) {
		case "24":
			// 地磁
			i = 1;
			break;
		case "22":
			// 烟感
			i = 2;
			break;
		case "03":
			i = 3;
			// 井盖
			break;
		case "25":
			i = 4;
			// 暂定甲烷监测////pm2.5
			break;
		// case "01":
		case "14":
			// i = 99;
			break;
		case "31":
			i = 8;
			// 红外
			break;
		case "2D":
		case "59":
			i = 9;
			// 垃圾桶
			break;
		case "35":
			i = 10;
			// 水浸
			break;
		case "34":
			i = 11;
			// 门磁
			break;
		case "29":
			i = 12;
			// 可燃气
			break;
		case "38":
			i = 15;
			// 电压
			break;
		case "39":
			i = 14;
			// 电流
			break;
		case "40":
			i = 17;
			// 剩余电流
			break;
		case "41":
		case "45":
			i = 16;
			// 水压
			break;
		}
		return i;
	}
}
