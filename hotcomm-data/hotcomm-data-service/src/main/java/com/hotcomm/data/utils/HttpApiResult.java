package com.hotcomm.data.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HttpApiResult {
	
	private Integer statusCode;
	private String responseStr;
	
}
