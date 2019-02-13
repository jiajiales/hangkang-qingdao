package com.hotcomm.prevention.bean.mysql.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "ST_STBPRP_B")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TStStbprpB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "STCD")
	private String STCD;

	@Column(name = "STNM")
	private String STNM;

	@Column(name = "RVNM")
	private String RVNM;

	@Column(name = "HNNM")
	private String HNNM;

	@Column(name = "BSNM")
	private String BSNM;

	@Column(name = "STLC")
	private String STLC;

	@Column(name = "ADCD")
	private String ADCD;

	@Column(name = "MDPR")
	private String MDPR;

	@Column(name = "DTMNM")
	private String DTMNM;

	@Column(name = "DTMEL")
	private String DTMEL;

	@Column(name = "STTP")
	private String STTP;

	@Column(name = "FRGRD")
	private String FRGRD;

	@Column(name = "BGFRYM")
	private String BGFRYM;

	@Column(name = "ADMAUTH")
	private String ADMAUTH;

	@Column(name = "STBK")
	private String STBK;

	@Column(name = "DRNA")
	private String DRNA;

	@Column(name = "PHCD")
	private String PHCD;

	@Column(name = "DTPR")
	private String DTPR;

	@Column(name = "EDFRYM")
	private String EDFRYM;
	
	@Column(name = "ESSTYM")
	private String ESSTYM;

	@Column(name = "ATCUNIT")
	private String ATCUNIT;

	@Column(name = "LOCALITY")
	private String LOCALITY;

	@Column(name = "STAZT")
	private String STAZT;
	
	@Column(name = "DSTRVM")
	private String DSTRVM;

	@Column(name = "USFL")
	private String USFL;
	
	@Column(name = "COMMENTS")
	private String COMMENTS;

	@Column(name = "MODITIME")
	private String MODITIME;
	
	@Column(name = "LGTD")
	private String LGTD;

	@Column(name = "LTTD")
	private String LTTD;
	
	@Column(name = "IMGURL")
	private String IMGURL;

	@Column(name = "_MASK_FROM_V2")
	private String _MASK_FROM_V2;
	
	@Column(name = "PP_AlarmValue")
	private double PP_AlarmValue;

	@Column(name = "ZZ_AlarmValue")
	private double ZZ_AlarmValue;
	
	@Column(name = "PP_YJValue")
	private double PP_YJValue;

	@Column(name = "ZZ_YJValue")
	private double ZZ_YJValue;
	
}
