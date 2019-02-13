package com.hotcomm.prevention.bean.mysql.manage.material;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SelmaterialList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String materialname;

	private String num;

	private String areaid;

	private String addvb;

	private String code;

	private String msg;

	private String lat;

	private String lng;

	private String updatetime;

	private int contactsid1;

	private String contacts1;

	private String telephone1;

	private int contactsid2;

	private String contacts2;

	private String telephone2;

	private int contactsid3;

	private String contacts3;

	private String telephone3;

}
