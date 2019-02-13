package com.hot.manage.entity.item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_dev_item_pic")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDevItemPic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "item_pic_id")
	private Integer itemPicId;// 项目图片ID

	@Column(name = "dev_id")
	private Integer devId;// 设备ID

	@Column(name = "addtime")
	private String addtime;// 添加时间

	@Column(name = "isenable")
	private Boolean isenable;

	@Column(name = "isdelete")
	private Boolean isdelete;

	@Column(name = "moduleid")
	private Integer moduleid;
}