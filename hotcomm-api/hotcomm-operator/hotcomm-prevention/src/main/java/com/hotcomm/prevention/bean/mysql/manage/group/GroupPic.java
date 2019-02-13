package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_item_pic")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupPic implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="picnum")
	private String picnum;//图片编号
	
	@Column(name="picpath")
	private String picpath;//图片路径
	
	@Column(name="site")
	private String site;//所在楼层具体位置
	
	@Column(name="itemid")
	private Integer itemid;//项目ID
	
	@Column(name="addtime")
	private String addtime;
	
	@Column(name="isdelete")
	private Boolean isdelete;
	
	@Column(name="isenable")
	private Boolean isenable;
	
	@Column(name="updatetime")
	private String updatetime;
}
