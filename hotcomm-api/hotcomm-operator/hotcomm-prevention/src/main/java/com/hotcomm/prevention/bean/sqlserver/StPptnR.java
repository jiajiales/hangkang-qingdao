package com.hotcomm.prevention.bean.sqlserver;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Table(name="")
public class StPptnR implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="STCD")
	private String stcd;
	@Column(name="TM")
	private String tm;
	@Column(name="DRP")
	private BigDecimal drp;
	@Column(name="INTV")
    private BigDecimal intv;
	@Column(name="PDR")
    private BigDecimal pdr;
	@Column(name="DYP")
    private BigDecimal dyp;
	@Column(name="WTH")
    private String wth;
	@Column(name="syn")
    private String syn;
	@Column(name="_MASK_FROM_V2")
    private long maskFromV2;
}