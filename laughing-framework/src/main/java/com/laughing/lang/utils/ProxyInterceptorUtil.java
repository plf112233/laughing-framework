package com.laughing.lang.utils;

import httl.spi.Interceptor;

import java.util.Iterator;
import java.util.List;

public class ProxyInterceptorUtil {

	public static Object invokeByInterceptors(List<? extends Interceptor> interceptors, Object obj, Invoker invoker,
			Object[] args) throws Throwable {
		Iterator<? extends Interceptor> it = interceptors.iterator();
		if (!it.hasNext()) {
			return invoker.invoke(obj, args);
		}
		return null;
	}

	public static interface Invoker {

		String getName();

		Object invoke(Object obj, Object[] args) throws Throwable;

	}

}
