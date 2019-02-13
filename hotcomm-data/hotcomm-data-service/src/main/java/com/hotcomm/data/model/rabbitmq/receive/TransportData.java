package com.hotcomm.data.model.rabbitmq.receive;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TransportData implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deviceCode;
	private Integer deviceId;
	private String sourceData;
	private Integer source;
	private String coreData;
	private Integer parseType;
	
	public enum TransportDataSource{
		
		AMQP(1,"AMQP"),MQTT(2,"MQTT"),TCP(3,"TCP"),HTTP(4,"HTTP");
		
		private Integer value;
		private String name;

		TransportDataSource(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static TransportDataSource getByValue(Integer value) {
			for (TransportDataSource obj : TransportDataSource.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
		
	}
	
public enum TransportParseType{
		
		ZHENGWEN(1,"正文");
		
		private Integer value;
		private String name;

		TransportParseType(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static TransportParseType getByValue(Integer value) {
			for (TransportParseType obj : TransportParseType.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
		
	}
}
