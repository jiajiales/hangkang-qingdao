package com.hotcomm.data.bean.enums;

public class QueueEnum {

	public enum QueueType {
		UPSTREAM(1, "上行"), 
		DOWNSTREAM(2, "下行");

		private Integer value;
		private String name;

		QueueType(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static QueueType getByValue(Integer value) {
			for (QueueType obj : QueueType.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}

	}

	public enum QueueStatus {
		QUEUE_RUN(1, "队列运行"), 
		QUEUE_PAUSE(2, "队列暂停");

		private Integer value;
		private String name;

		QueueStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static QueueStatus getByValue(Integer value) {
			for (QueueStatus obj : QueueStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}

	}

	public enum QueueDeleteStatus {
		NO(1, "否"), 
		YES(2, "是");

		private Integer value;
		private String name;

		QueueDeleteStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static QueueDeleteStatus getByValue(Integer value) {
			for (QueueDeleteStatus obj : QueueDeleteStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}

	}

}
