package com.hotcomm.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Component
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ApplicationEnvironment {
	
	/**
	 * 数据库
	 */
	@Value("${spring.datasource.username}")
	public String 	dbName;
	@Value("${spring.datasource.password}")
	public String 	dbPwd;
	@Value("${spring.datasource.url}")
	public String 	dbUrl;
	
	/**
	 * AMPQ 接收 
	 */
	@Value("${spring.rabbitmq.receive.host}")
	public String 	amqpRHost;
	@Value("${spring.rabbitmq.receive.port}")
	public Integer  amqpRPort;
	@Value("${spring.rabbitmq.receive.username}")
	public String 	amqpRName;
	@Value("${spring.rabbitmq.receive.password}")
	public String 	amqpRPwd;
	@Value("${spring.rabbitmq.receive.publisher-confirms}")
	public Boolean  amqpRConfirm;
	@Value("${spring.rabbitmq.receive.virtual-host}")
	public String 	amqpRVhost;
	@Value("${spring.rabbitmq.receive.listener.queue}")
	public String 	amqpRQueue;
	
	/**
	 * AMQP 发送
	 */
	@Value("${spring.rabbitmq.send.host}")
	public String  	amqpSHost;
	@Value("${spring.rabbitmq.send.port}")
	public Integer 	amqpSPort;
	@Value("${spring.rabbitmq.send.username}")
	public String 	amqpSName;
	@Value("${spring.rabbitmq.send.password}")
	public String 	amqpSPwd;
	@Value("${spring.rabbitmq.send.publisher-confirms}")
	public Boolean 	amqpSConfirm;
	@Value("${spring.rabbitmq.send.address}")
	public String 	amqpSAddress;	
	
	/**
	 * REDIS 
	 */
	@Value("${spring.redis.database}")
	public Integer redisIndex;
	
	
}
