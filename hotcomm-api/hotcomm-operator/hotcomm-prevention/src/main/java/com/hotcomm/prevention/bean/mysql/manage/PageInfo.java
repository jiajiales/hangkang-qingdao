package com.hotcomm.prevention.bean.mysql.manage;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PageInfo<E>{
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	private long total;
	
	private List<E> rows;

	public PageInfo(Integer pageNum, Integer pageSize, long l, List<E> rows) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = l;
		this.rows = rows;
	}
}
