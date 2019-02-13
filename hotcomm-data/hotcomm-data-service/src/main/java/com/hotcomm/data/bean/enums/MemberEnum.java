package com.hotcomm.data.bean.enums;

public class MemberEnum {

	public enum MemberStatus {
		ENABLE(1, "有效"), 
		DISABLE(2, "无效");

		private Integer value;
		private String name;

		MemberStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static MemberStatus getByValue(Integer value) {
			for (MemberStatus obj : MemberStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum MemberType {
		SYSTEM(1, "系统"), 
		CUSTOMER(2, "客户");

		private Integer value;
		private String name;

		MemberType(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static MemberType getByValue(Integer value) {
			for (MemberType obj : MemberType.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum MemberDeleteStatus {
		NO(1, "否"), 
		YES(2, "是");

		private Integer value;
		private String name;

		MemberDeleteStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static MemberDeleteStatus getByValue(Integer value) {
			for (MemberDeleteStatus obj : MemberDeleteStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
