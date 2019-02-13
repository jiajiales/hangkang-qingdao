package com.hotcomm.data.bean.enums;

public class DeviceGroupEnum {

	public enum DeviceGroupStatus {
		ENABLE(1, "有效"), 
		DISABLE(2, "无效");

		private Integer value;
		private String name;

		DeviceGroupStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceGroupStatus getByValue(Integer value) {
			for (DeviceGroupStatus obj : DeviceGroupStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum DeviceGroupDeleteStatus {
		NO(1, "否"), 
		YES(2, "是");

		private Integer value;
		private String name;

		DeviceGroupDeleteStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static DeviceGroupDeleteStatus getByValue(Integer value) {
			for (DeviceGroupDeleteStatus obj : DeviceGroupDeleteStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
