package com.hotcomm.prevention.bean.mysql.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportDeviceVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numberOfSuccessful;
	
	private Integer numberOfUnsuccessful;

	private List<String> mac;
	
}
