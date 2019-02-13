package com.hotcomm.data.bean.vo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReceiveDataVO {

	private String receiveData;

	public ReceiveDataVO(String receiveData) {
		super();
		this.receiveData = receiveData;
	}

}
