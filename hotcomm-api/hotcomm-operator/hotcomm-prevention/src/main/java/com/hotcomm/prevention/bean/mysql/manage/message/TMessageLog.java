package com.hotcomm.prevention.bean.mysql.manage.message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TMessageLog {

	private Integer id;
	// 发送人ID
	private Integer userid;
	// 消息内容
	private Object message;
	// 接收人员
	private String receiverid;//多个人以逗号分割
	// 发送时间
	private String sendtime;
	// 接收时间
	private String receivetime;
	// 发送状态，0：未发，1:已发
	private Integer state;
}