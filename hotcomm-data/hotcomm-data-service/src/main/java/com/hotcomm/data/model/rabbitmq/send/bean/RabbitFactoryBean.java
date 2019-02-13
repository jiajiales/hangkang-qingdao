package com.hotcomm.data.model.rabbitmq.send.bean;

import java.util.Map;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RabbitFactoryBean {

	private String facotryName;

	private Class<? extends ConnectionFactory> factoryClass;

	private Map<String, Object> facotryProperites;

}
