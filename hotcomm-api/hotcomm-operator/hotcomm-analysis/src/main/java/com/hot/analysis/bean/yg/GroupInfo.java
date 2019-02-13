package com.hot.analysis.bean.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;//设备组id
	
	private Integer moduleid;//模块Id
	
	private String groupname;//组名
	
	private String x;//经度
	
	private String y;//维度
	
	private String contactname;
	
	private String telephone;//电话
	
	private Integer devcount;//当前组的设备数量
	
	private Integer alarmcount;//地磁（停车数量）烟感（报警数量）井盖（报警数量）
	
	private Integer freecount;// 地磁（空闲车位数量）烟感井盖（正常设备数量）
	
	private Integer faultcount;//故障设备数量

}
