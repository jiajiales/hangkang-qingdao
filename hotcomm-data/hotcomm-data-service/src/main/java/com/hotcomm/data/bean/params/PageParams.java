package com.hotcomm.data.bean.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PageParams {

	private int page; // 分页-页码

	private int rows; // 分页-页数

}
