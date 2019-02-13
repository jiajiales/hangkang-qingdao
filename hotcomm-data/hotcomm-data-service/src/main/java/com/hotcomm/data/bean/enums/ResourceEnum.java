package com.hotcomm.data.bean.enums;

public class ResourceEnum {

	public enum ResourceType {
		MENU(1, "菜单"), 
		API(2, "API"), 
		API_BUTON(3, "API和按钮组合"), 
		BUTTON(4, "按钮");

		private Integer value;
		private String name;

		ResourceType(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static ResourceType getByValue(Integer value) {
			for (ResourceType obj : ResourceType.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

	public enum ResourceStatus {
		ENABLE(1, "启用"), 
		DISABLE(2, "禁用");

		private Integer value;
		private String name;

		ResourceStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static ResourceStatus getByValue(Integer value) {
			for (ResourceStatus obj : ResourceStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
