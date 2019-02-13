package com.hot.manage.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class RequestMsg {
	private Integer userid;//发送人
	private String message;//消息内容
	private String receiveid;//接收人的id，可以是多个，以逗号分割

}
