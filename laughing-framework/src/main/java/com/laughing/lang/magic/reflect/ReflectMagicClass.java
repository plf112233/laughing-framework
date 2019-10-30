package com.laughing.lang.magic.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

import com.laughing.lang.exceptions.CanNotFindMethodException;
import com.laughing.lang.exceptions.CanNotInstanceException;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicMethod;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.magic.Property;
import com.laughing.lang.utils.ArrayUtil;
/**
* @ClassName: ReflectMagicClass 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:20:32 
 */
public class ReflectMagicClass extends MagicClass {

	private static final long serialVersionUID = 3729825268816208769L;

	public ReflectMagicClass(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public Map<String, Property> getProperties() {
		return findPropertiesFromClass();
	}

	@Override
	public MagicObject newInstance() {
		return newInstance(ArrayUtil.EMPTY_CLASS_ARRAY, null);
	}

	@Override
	public MagicObject newInstance(final Class<?>[] parameterTypes, final Object[] constructorArguments) {
		return AccessController.doPrivileged(new PrivilegedAction<MagicObject>() {
			public MagicObject run() {
				try {
					Constructor<?> constructor = targetClass.getConstructor(parameterTypes);
					return new ReflectMagicObject(constructor.newInstance(constructorArguments));
				} catch (NoSuchMethodException e) {
					throw new CanNotInstanceException(e);
				} catch (IllegalArgumentException e) {
					throw new CanNotInstanceException(e);
				} catch (InstantiationException e) {
					throw new CanNotInstanceException(e);
				} catch (IllegalAccessException e) {
					throw new CanNotInstanceException(e);
				} catch (InvocationTargetException e) {
					throw new CanNotInstanceException(e.getTargetException());
				}
			}
		});
	}

	@Override
	public MagicMethod getMethod(String methodName) {
		Method method = getMethodByName(methodName);
		return null;
	}

	@Override
	public MagicMethod getMethod(final String methodName, final Class<?>[] argumentTypes) {
		return AccessController.doPrivileged(new PrivilegedAction<MagicMethod>() {
			public MagicMethod run() {
				try {
					Method method = targetClass.getMethod(methodName, argumentTypes);
					return new ReflectMagicMethod(method);
				} catch (NoSuchMethodException e) {
					throw new CanNotFindMethodException(e);
				}
			}
		});
	}

	@Override
	public String toString() {
		return "ReflectMagicClass [" + targetClass + "]";
	}

}
