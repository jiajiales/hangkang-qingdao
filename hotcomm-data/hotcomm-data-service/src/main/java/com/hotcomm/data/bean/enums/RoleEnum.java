package com.hotcomm.data.bean.enums;

public class RoleEnum {

	public enum RoleType {
		SUPER_ADMIN(1, "超级管理员"), 
		ENTERPRISE_ADMIN(2, "企业管理员"),
		SUPPORT_TECHNICIAN(3, "技术支持员"),
		SERVICE_SALESMAN(4, "业务销售员");

		private Integer value;
		private String name;

		RoleType(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static RoleType getByValue(Integer value) {
			for (RoleType obj : RoleType.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
