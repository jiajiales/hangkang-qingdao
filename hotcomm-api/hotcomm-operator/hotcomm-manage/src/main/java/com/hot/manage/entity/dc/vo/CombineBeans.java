package com.hot.manage.entity.dc.vo;

import java.lang.reflect.Field;

public class CombineBeans {

	/**
	 * 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，那么sourceBean中的值会覆盖tagetBean重点的值
	 * 
	 * @param sourceBean
	 *            被提取的对象bean
	 * @param targetBean
	 *            用于合并的对象bean
	 * @return targetBean,合并后的对象
	 */
	public DCDeviceList combineSydwCore(DCDeviceList sourceBean, DCDeviceList targetBean) {
		Class sourceBeanClass = sourceBean.getClass();
		Class targetBeanClass = targetBean.getClass();

		Field[] sourceFields = sourceBeanClass.getDeclaredFields();
		Field[] targetFields = sourceBeanClass.getDeclaredFields();
		for (int i = 0; i < sourceFields.length; i++) {
			Field sourceField = sourceFields[i];
			Field targetField = targetFields[i];
			sourceField.setAccessible(true);
			targetField.setAccessible(true);
			try {
				if (!(sourceField.get(sourceBean) == null)
						&& !"serialVersionUID".equals(sourceField.getName().toString())) {
					targetField.set(targetBean, sourceField.get(sourceBean));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return targetBean;
	}

}