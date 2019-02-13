package com.hotcomm.prevention.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Entityforbyte {
	/**
	 * 对象转Byte数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();

		} catch (IOException e) {
			System.out.println("error");
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					System.out.println("error");
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					System.out.println("error");
				}
			}

		}
		return bytes;
	}
}
