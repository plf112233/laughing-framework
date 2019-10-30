package com.laughing.lang.magic.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import com.laughing.lang.exceptions.MethodInvokeException;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicList;
import com.laughing.lang.magic.MagicMethod;

/**
* @ClassName: ReflectMagicMethod 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:20:16 
 */
public class ReflectMagicMethod extends MagicMethod {

    public ReflectMagicMethod(Method method) {
        super(method);
    }

    @Override
    public Object invoke(final Object object, final Object[] arguments) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                targetMethod.setAccessible(true);
                try {
                    return targetMethod.invoke(object, arguments);
                } catch (IllegalAccessException e) {
                    throw new MethodInvokeException(e);
                } catch (InvocationTargetException e) {
                    throw new MethodInvokeException(e.getTargetException());
                }
            }
        });
    }

    public MagicList<MagicClass> getParameterTypes() {
        Class<?>[] types = targetMethod.getParameterTypes();
        MagicList<MagicClass> ret = MagicList.newList();
        for (Class<?> t : types) {
            ret.add(new ReflectMagicClass(t));
        }
        return ret;
    }

    @Override
    public String toString() {
        return "ReflectMagicMethod [" + targetMethod + "]";
    }

}