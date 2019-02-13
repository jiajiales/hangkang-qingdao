package com.hotcomm.prevention.bean.mysql.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "ST_RIVER_R")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TStRiverR implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "STCD")
	private String STCD;

	@Column(name = "TM")
	private String TM;

	@Column(name = "Z")
	private double Z;

	@Column(name = "Q")
	private double Q;

	@Column(name = "XSA")
	private double XSA;

	@Column(name = "XSAVV")
	private double XSAVV;

	@Column(name = "XSMXV")
	private double XSMXV;

	@Column(name = "FLWCHRCD")
	private String FLWCHRCD;

	@Column(name = "WPTN")
	private String WPTN;

	@Column(name = "MSQMT")
	private String MSQMT;

	@Column(name = "MSAMT")
	private String MSAMT;

	@Column(name = "MSVMT")
	private String MSVMT;

	@Column(name = "syn")
	private String syn;

	@Column(name = "_MASK_FROM_V2")
	private String _MASK_FROM_V2;

}
