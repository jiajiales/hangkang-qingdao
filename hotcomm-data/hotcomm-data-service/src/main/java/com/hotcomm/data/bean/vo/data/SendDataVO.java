package com.hotcomm.data.bean.vo.data;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SendDataVO {

	private String deviceCode;

	private String deviceType;

	private String ucode;

	private String vhostCode;

	private String queueName;

	private String deviceData;

	public String getPushMessage(){
		Map<String, Object> data = new HashMap<>();
		data.put("deviceCode", this.deviceCode);
		data.put("deviceType", this.deviceType);
		data.put("ucode", this.ucode);
		data.put("deviceData", this.deviceData);
		return JSON.toJSON(data).toString();
	}

}
