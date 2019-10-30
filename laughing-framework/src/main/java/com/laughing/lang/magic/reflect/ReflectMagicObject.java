package com.laughing.lang.magic.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;

import com.laughing.lang.magic.Interceptor;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.utils.ClassUtil;
import com.laughing.lang.utils.CollectionUtil;

/**
* @ClassName: ReflectMagicObject 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:20:04 
 */
public class ReflectMagicObject extends MagicObject {

	public ReflectMagicObject(Object targetObject) {
		super(targetObject, new ReflectMagicClass(targetObject.getClass()));
	}

	@Override
	public MagicObject asProxyObject(final List<? extends Interceptor> interceptors) {
		if (CollectionUtil.isEmpty(interceptors)) {
			return this;
		}
		Set<Class<?>> interfaces = ClassUtil.getAllInterfaces(magicClass.getTargetClass());
		if (CollectionUtil.isEmpty(interfaces)) {
			return this;
		}

		return new ReflectMagicObject(Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
				interfaces.toArray(new Class<?>[0]), new InvocationHandler() {

					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				}));
	}

}
