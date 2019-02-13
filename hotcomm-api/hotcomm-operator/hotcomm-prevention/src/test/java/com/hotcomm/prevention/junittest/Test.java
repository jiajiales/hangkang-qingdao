package com.hotcomm.prevention.junittest;

public class Test {

	public static void main(String[] args) {
		String name = Test.class.getName();
		System.out.println(name);
		String name2 = new Test().getClass().getName();
		String nam3 = new Test().getClass().getCanonicalName();
		Object[] signers = new Test().getClass().getSigners();
		System.out.println(signers);
		System.out.println(name2);
		System.out.println(nam3);

	}

}
