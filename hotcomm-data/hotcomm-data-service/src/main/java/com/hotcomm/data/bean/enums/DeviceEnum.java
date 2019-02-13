package com.hotcomm.data.bean.enums;

public class DeviceEnum {

	public enum DeviceStatus {
		ENABLE(1, "启用"), 
		DISABLE(2, "禁用");

		private Integer value;
		private String name;

		DeviceStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceStatus getByValue(Integer value) {
			for (DeviceStatus obj : DeviceStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum DeviceDeleteStatus {
		NO(1, "否"), 
		YES(2, "是");

		private Integer value;
		private String name;

		DeviceDeleteStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceDeleteStatus getByValue(Integer value) {
			for (DeviceDeleteStatus obj : DeviceDeleteStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum DeviceIotTech {
		LOAR(1, "LORA"), 
		NB_IOT(2, "NB-Iot"), 
		GPRS(3, "GPRS");

		private Integer value;
		private String name;

		DeviceIotTech(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceIotTech getByValue(Integer value) {
			for (DeviceIotTech obj : DeviceIotTech.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum DeviceProtocol {
		AMQP(1, "AMQP"), 
		MQTT(2, "MQTT"), 
		TCP_IP(3, "TCP/IP"), 
		HTTP(4, "HTTP");

		private Integer value;
		private String name;

		DeviceProtocol(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceProtocol getByValue(Integer value) {
			for (DeviceProtocol obj : DeviceProtocol.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
