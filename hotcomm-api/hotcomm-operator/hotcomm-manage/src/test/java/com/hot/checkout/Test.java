package com.hot.checkout;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.utils.JSONUtil;
import com.hot.manage.utils.PushUtil;
public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
//		getJson(TDeviceGroup.class);
		PushUtil.sendAllsetNotification("123", "321", "333", "123", 1024);
	}

	private static <T> void getJson(Class<T> t) {
		Field[] fields = t.getDeclaredFields();
		Map<String, Object> map = new HashMap<>();
		for (Field field : fields) {
			//System.out.println(field.getType().getTypeName().toString());
			map.put(field.getName().toString(), 1);
		}
		System.out.println(JSONUtil.toJson(map));
	}

}
