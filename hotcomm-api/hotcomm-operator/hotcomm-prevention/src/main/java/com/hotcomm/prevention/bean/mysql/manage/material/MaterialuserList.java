package com.hotcomm.prevention.bean.mysql.manage.material;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MaterialuserList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// 物资管理人员id
	private Integer id;
	// 用户编号
	private String userNum;
	// 用户名称
	private String contacts;
	// 物资管理人员添加时间
	private String addtime;
	// 管理物资点个数
	private Integer materialnum;
	// 用户联系电话
	private String telephone;
}
