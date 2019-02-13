package com.hotcomm.data.bean.params.service.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataUpdatePrams {

	private Integer modifyStatus;

	private Integer targetStatus;

	private String targetUcode;

}
