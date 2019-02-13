package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;
import java.util.HashMap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MaterialInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 区域id
	 */
	private Integer areaid;
	/**
	 * 区域名称
	 */
	private String areaname;
	/**
	 * 物资id
	 */
	private Integer materialid;
	/**
	 * 物资名称
	 */
	private String materialname;
	/**
	 * 物资地址
	 */
	private String code;
	/**
	 * 物资联系人1
	 */
	private String user1;
	
	private String telephone1;
	/**
	 * 物资联系人2
	 */
	private String user2;
	
	private String telephone2;
	/**
	 * 物资联系人3
	 */
	private String user3;
	
	private String telephone3;
	
	/**
	 * 物资内容
	 */
	private String msg;
	/**
	 * 物资内容(数组)
	 */
	private HashMap<String, String> msgList;
}
