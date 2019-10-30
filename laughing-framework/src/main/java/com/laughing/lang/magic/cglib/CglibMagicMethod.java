package com.laughing.lang.magic.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import com.laughing.lang.exceptions.MethodInvokeException;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicList;
import com.laughing.lang.magic.MagicMethod;

/**
* @ClassName: CglibMagicMethod 
* @Description: 通过cglib实现的方法
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:21:10 
*
 */
public class CglibMagicMethod extends MagicMethod {

	private FastMethod fastMethod;
	
	public CglibMagicMethod(Method targetMethod, FastClass fastClass) {
		super(targetMethod);
		fastMethod = fastClass.getMethod(targetMethod.getName(), targetMethod.getParameterTypes());
	}
	public MagicList<MagicClass> getParameterTypes() {
		Class<?>[] types = targetMethod.getParameterTypes();
		MagicList<MagicClass> ret = MagicList.newList();
		for (Class<?> t : types) {
			//ret.add(CglibMagicClass.fromClass(t));
		}
		return ret;
	}
	
	@Override
	public Object invoke(Object object, Object[] arguments) {
		try {
			return fastMethod.invoke(object, arguments);
		} catch (InvocationTargetException e) {
			throw new MethodInvokeException(e.getTargetException());
		}
	}

}
