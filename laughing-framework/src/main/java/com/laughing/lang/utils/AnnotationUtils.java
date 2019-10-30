package com.laughing.lang.utils;

import com.laughing.lang.annotation.DBQueryValueAttribute;
import com.laughing.lang.annotation.EditValueAttribute;
import com.laughing.lang.annotation.ViewValueAttribute;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {
	public static Map<String, String> getViewValues(Class<?> c) {
		Map<String, String> viewValue = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			boolean fieldHasAnno = field.isAnnotationPresent(ViewValueAttribute.class);
			if (fieldHasAnno) {
				ViewValueAttribute fieldAnno = field.getAnnotation(ViewValueAttribute.class);
				// 输出注解属性
				String viewAlias = fieldAnno.name();
				viewValue.put(field.getName(), viewAlias);
			} else {
				viewValue.put(field.getName(), null);
			}
		}
		return viewValue;
	}
	public static Map<String, String> getDBQueryValues(Class<?> c) {
		Map<String, String> dbQueryValue = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			boolean fieldHasAnno = field.isAnnotationPresent(DBQueryValueAttribute.class);
			if (fieldHasAnno) {
				DBQueryValueAttribute fieldAnno = field.getAnnotation(DBQueryValueAttribute.class);
				// 输出注解属性
				String editAlias = fieldAnno.name();
				dbQueryValue.put(field.getName(), editAlias);
			} else {
				dbQueryValue.put(field.getName(), null);
			}
		}
		return dbQueryValue;
	}

	public static Map<String, String> getEditValues(Class<?> c) {
		Map<String, String> editValue = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			boolean fieldHasAnno = field.isAnnotationPresent(EditValueAttribute.class);
			if (fieldHasAnno) {
				EditValueAttribute fieldAnno = field.getAnnotation(EditValueAttribute.class);
				// 输出注解属性
				String editAlias = fieldAnno.name();
				editValue.put(field.getName(), editAlias);
			} else {
				editValue.put(field.getName(), null);
			}
		}
		return editValue;
	}
	public static boolean needShowView(Class<?> c, String name) {
		Map<String, String> viewValue = getViewValues(c);
		String value = viewValue.get(name);
		if (StringUtil.isNotBlank(value)) {
			return true;
		}
		return false;
	}
	public static String getDBQueryValue(Class<?> c, String name) {
		Map<String, String> dbQueryValue = getDBQueryValues(c);
		String value = dbQueryValue.get(name);
		return value;
	}
	public static String getViewValue(Class<?> c, String name) {
		Map<String, String> viewValue = getViewValues(c);
		String value = viewValue.get(name);
		return value;
	}

	public static String getEditValue(Class<?> c, String name) {
		Map<String, String> editValue = getEditValues(c);
		String value = editValue.get(name);
		return value;
	}
}
