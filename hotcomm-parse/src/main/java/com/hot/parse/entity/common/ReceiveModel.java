package com.hot.parse.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReceiveModel {
	public String id;
	public String macAddr;
	public String data;
	public String recv;
	public extra extra;
	public String pub;
	public boolean IsSucess;
}
