package com.laughing.lang.magic.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import com.laughing.lang.LaughingException;
import com.laughing.lang.exceptions.CanNotFindMethodException;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicMethod;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.magic.Property;
import com.laughing.lang.magic.PropertyUtil;
import com.laughing.lang.magic.reflect.ReflectMagicClass;
import com.laughing.lang.utils.MapUtil;

/**
* @ClassName: CglibMagicClass 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:21:20 
*
 */
public class CglibMagicClass extends MagicClass {

	private static final long serialVersionUID = -3133806326517090028L;

	private FastClass fastClass;
	
	protected CglibMagicClass(Class<?> inputClass) {
		super(CglibUtil.getJavaClass(inputClass));
		fastClass = FastClass.create(this.targetClass);
	}
	
	public static MagicClass fromClass(Class<?> inputClass) {
		if (inputClass.isPrimitive()) {
			return new ReflectMagicClass(inputClass);
		}
       
		return new CglibMagicClass(inputClass);
	}
	
	@Override
	public MagicMethod getMethod(String methodName) {
		return null;
	}

	@Override
    public MagicMethod getMethod(final String methodName, final Class<?>[] argumentTypes) {
        return AccessController.doPrivileged(new PrivilegedAction<MagicMethod>() {
            public MagicMethod run() {
                try {
                    Method method = targetClass.getMethod(methodName, argumentTypes);
                    return new CglibMagicMethod(method, fastClass);
                } catch (NoSuchMethodException e) {
                    throw new CanNotFindMethodException(e);
                }
            }
        });
    }

    @Override
	public Map<String, Property> getProperties() {
		return findPropertiesFromClass();
	}

	@Override
	public MagicObject newInstance() {
		try {
			return new CglibMagicObject(fastClass.newInstance());
		} catch (InvocationTargetException e) {
			throw new LaughingException(e.getTargetException());
		}
	}

	@Override
	public MagicObject newInstance(Class<?>[] parameterTypes, Object[] constructorArguments) {
		try {
			FastConstructor fastConstructor = fastClass.getConstructor(parameterTypes);
			return new CglibMagicObject(fastConstructor.newInstance(constructorArguments));
		} catch (InvocationTargetException e) {
			throw new LaughingException(e.getTargetException());
		} catch (Exception e) {
			throw new LaughingException(e);
		}
	}
	
	protected Map<String, Property> findPropertiesFromClass() {
		Method[] methods = this.targetClass.getMethods();
		Map<String, Method> readableMethods = MapUtil.newHashMap();
		Map<String, Method> writableMethods = MapUtil.newHashMap();
		for (Method method : methods) {
			if (PropertyUtil.isReadableMethod(method)) {
				String propertyName = PropertyUtil.getPropertyName(method.getName());
				readableMethods.put(propertyName, method);
			} else if (PropertyUtil.isWritableMethod(method)) {
				String propertyName = PropertyUtil.getPropertyName(method.getName());
				writableMethods.put(propertyName, method);
			}
		}
		
		Map<String, Property> ret = MapUtil.newHashMap();
		for (Map.Entry<String, Method> entry : readableMethods.entrySet()) {
			String name = entry.getKey();
			Method readMethod = entry.getValue();
			Method writeMethod = writableMethods.remove(name);
			Class<?> type = readMethod.getReturnType();
            /*
			if (type.isArray()) {
			    continue;
			}
			*/
            MagicClass propertyClass = CglibMagicClass.fromClass(type);
			Property property = new Property(name, propertyClass, new CglibMagicMethod(readMethod, fastClass), (writeMethod != null ? new CglibMagicMethod(writeMethod, fastClass) : null));
			ret.put(name, property);
		}
		for (Map.Entry<String, Method> entry : writableMethods.entrySet()) {
			String name = entry.getKey();
			Method writeMethod = entry.getValue();
			Class<?> type = writeMethod.getParameterTypes()[0];
            /*
			if (type.isArray()) {
			    continue;
			}
			*/
            MagicClass propertyClass = CglibMagicClass.fromClass(type);
			Property property = new Property(name, propertyClass, null, new CglibMagicMethod(writeMethod, fastClass));
			ret.put(name, property);
		}
		return ret;
	}

    public FastClass getFastClass() {
        return fastClass;
    }
}
