package com.hotcomm.prevention.bean.mysql.manage.alarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_dev_handle_state")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmState implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//(value = "[必填]类型ID")
	private Integer id;

	@Column(name="state_name")
	//(value = "[必填]状态名称")
	private String state_name;
	
	@Column(name="module_id")
	//(value = "模块ID")
	private Integer module_id;

	@Column(name="isdelete")
	//(value = "[必填]是否删除，1：删除；0：不删除")
	private Integer isdelete;
	
	@Column(name="addtime")
	//(value = "添加时间")
	private String addtime;
	
	@Column(name="type")
	//(value = "[必填]类型，1：报警；2：故障; 3：其他")
	private Integer type;

	@Column(name="`level`")
	//(value = "[必填]紧急状态程度，1-5")
	private Integer level;
}
