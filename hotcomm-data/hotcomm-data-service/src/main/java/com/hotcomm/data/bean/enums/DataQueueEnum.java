package com.hotcomm.data.bean.enums;

public class DataQueueEnum {

	public enum SendStatus {
		UNSENT(1, "未发送"), 
		SEND_WAI(2, "等待发送"), 
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

}
