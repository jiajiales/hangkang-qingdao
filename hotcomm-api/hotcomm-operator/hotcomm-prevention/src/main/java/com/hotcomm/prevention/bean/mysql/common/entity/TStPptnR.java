package com.hotcomm.prevention.bean.mysql.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "ST_PPTN_R")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TStPptnR implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "STCD")
	private String STCD;

	@Column(name = "TM")
	private String TM;

	@Column(name = "DRP")
	private double DRP;

	@Column(name = "INTV")
	private double INTV;

	@Column(name = "PDR")
	private double PDR;

	@Column(name = "DYP")
	private double DYP;

	@Column(name = "WTH")
	private String WTH;

	@Column(name = "syn")
	private String syn;

	@Column(name = "_MASK_FROM_V2")
	private String _MASK_FROM_V2;

}
