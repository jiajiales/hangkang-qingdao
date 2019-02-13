package com.hotcomm.data.model.rabbitmq.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueueCustomer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1087141385207130926L;
	
	private Integer messages;
	private Integer consumers;

	public QueueCustomer(Integer messages, Integer consumers) {
		super();
		this.messages = messages;
		this.consumers = consumers;
	}
	
}
