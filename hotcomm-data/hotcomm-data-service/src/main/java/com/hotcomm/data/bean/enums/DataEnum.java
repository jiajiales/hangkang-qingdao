package com.hotcomm.data.bean.enums;

public class DataEnum {

	public enum SendStatus {
		UNSENT(1, "未发送"), 
		SEND_WAI(2, "待发送"), 
		SEND_SUCCESS(3, "发送成功"), 
		SEND_FAIL(4, "发送失败");

		private Integer value;
		private String name;

		SendStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static SendStatus getByValue(Integer value) {
			for (SendStatus obj : SendStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}

	}

	public enum DataSource {
		RABBITMQ(1, "RabbitMQ"), 
		TCP(2, "TCP/IP"), 
		MQTT(3, "MQTT");

		private Integer value;
		private String name;

		DataSource(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DataSource getByValue(Integer value) {
			for (DataSource obj : DataSource.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
