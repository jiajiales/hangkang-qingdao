package com.hotcomm.data.view.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;

public class SocketMessage {

	private String tag;
	private Object data;

	public SocketMessage(String tag, Object data) {
		this.tag = tag;
		this.data = data;
	}

	/**
	 * 转换成TextMessage进行发送
	 *
	 * @return
	 */
	public TextMessage toTextMessage() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			return new TextMessage(mapper.writeValueAsString(this));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

}
