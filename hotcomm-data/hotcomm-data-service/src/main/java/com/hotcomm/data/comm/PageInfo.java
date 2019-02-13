package com.hotcomm.data.comm;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PageInfo<T> {

	private Long total;
	private List<T> rows;

	public PageInfo(Long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

}
