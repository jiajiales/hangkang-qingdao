package com.hotcomm.data.bean.enums;

public class MemberVhostEnum {

	public enum MemberVhostStatus {
		UNALLOCATED(1, "未分配"), 
		ALLOCATED(2, "已分配"), 
		AUTHORIZE_PASS(3, "授权通过");

		private Integer value;
		private String name;

		MemberVhostStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static MemberVhostStatus getByValue(Integer value) {
			for (MemberVhostStatus obj : MemberVhostStatus.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
	}

}
