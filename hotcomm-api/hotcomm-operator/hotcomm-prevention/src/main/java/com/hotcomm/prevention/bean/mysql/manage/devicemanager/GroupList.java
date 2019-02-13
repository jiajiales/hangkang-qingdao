package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String itemnum;
	private String groupname;
	private String groupcode;
	private String contacts;
	private String addtime;
	private String lat;
	private String lng;
	private String itempicid;
	private String picpath;
	private String site;
	private String imgpath;

}
