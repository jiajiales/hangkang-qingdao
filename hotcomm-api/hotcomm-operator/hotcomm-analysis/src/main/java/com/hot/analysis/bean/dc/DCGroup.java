package com.hot.analysis.bean.dc;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DCGroup {

	private Integer id;//组id

	private Integer moduleid;//模块ID

	private String groupname;//组名

	private Integer fatherid;//父ID

	private String groupcode;//项目组地址

	private String x;//经度

	private String y;//维度

	private Integer coordinate;//坐标类型

	private Integer isenable;//是否可用；1：可用；0：不可用

	private Integer isdelete;//是否删除，1：删除；0：不删除

	private Date addtime;//添加时间
	
	private Integer adduserid;//添加人ID
	
	private Integer managerid;//管理人（责任人）ID
	
	private Integer cityid;//城市ID
	
	private String imgpath;//图片路径
	
	private String itemnum;//项目编号
	
	private String  mname;//责任人名字
	
	private Integer dnum;//车位总数
	
	private Integer nunum;//空闲车位数
	
	private Integer failurecount;//故障数量
	
	private String telephone;//电话号码
	
}
