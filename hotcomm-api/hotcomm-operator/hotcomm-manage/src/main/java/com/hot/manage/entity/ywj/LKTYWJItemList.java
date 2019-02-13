package com.hot.manage.entity.ywj;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJItemList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String imgpath;
	private String itemnum;
	private String groupname;
	private String groupcode;
	private Integer devnum;
	private String contacts;
	private String addtime;
	private double x;
	private double y;
}
