package com.hotcomm.prevention.bean.mysql.datashow.sj;

import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TGroup {

	private Integer id;//组id

	private Integer moduleid;//模块ID

	private String groupname;//组名

	private Integer fatherid;//父ID

	private String groupcode;//项目组地址

	private String x;//经度

	private String y;//维度

	private Integer coordinate;//坐标类型

	private Integer isenable;//是否可用；1：可用；0：不可用

	private Integer isdelete;//是否删除，1：删除；0：不删除   dd

	private Date addtime;//添加时间
	
	private Integer adduserid;//添加人ID
	
	private Integer managerid;//管理人（责任人）ID
	
	private Integer cityid;//城市ID
	
	private String imgpath;//图片路径
	
	private String itemnum;//项目编号
	
	private String  mname;//责任人名字
	
	private String mtelephone;//电话号码
	
	private Integer dnum;//设备总数
	
	private Integer normalcount;//正常数量
	
	private Integer alarmcount;//报警数量
	
	private Integer failurecount;//故障数量

	private Integer othercount;//其他数量
	

}
