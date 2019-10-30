package com.laughing.help.biz.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* @ClassName: MediaGsonUtil 
* @Description:Gson转换,支持范型对象
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 下午3:15:33 
*
 */
public class MediaGsonUtil {
	
	/**
	 * 范型对象转json字符串
	 * @param obj 范型对象
	 * @param clazz 原始对象
	 * @param paradigmClazz 范型对象
	 */
	public static <T> String toJson(Object obj, Class<?> clazz, Class<T> paradigmClazz) {
		Gson gson = createGson("yyyy-MM-dd HH:mm:ss");
		Type objectType = type(clazz, paradigmClazz);
		return gson.toJson(obj, objectType);
	}

	private static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}

	private static Gson createGson(String datePattern) {
		return new GsonBuilder().setDateFormat(datePattern).disableHtmlEscaping().create();
	}

}
