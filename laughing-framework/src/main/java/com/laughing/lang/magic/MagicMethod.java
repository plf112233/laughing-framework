package com.laughing.lang.magic;

import java.lang.reflect.Method;

/**
* @ClassName: MagicMethod 
* @Description:方法包装类
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:18:53 
 */
public abstract class MagicMethod {

	protected final Method targetMethod;

	public MagicMethod(Method targetMethod) {
		super();
		this.targetMethod = targetMethod;
	}

	/**
	 * 方法调用
	 * 
	 * @param object
	 *            目标对象
	 * @param arguments
	 *            参数
	 * @return
	 */
	public abstract Object invoke(Object object, Object[] arguments);

	/**
	 * 方法调用
	 * 
	 * @param magicObject
	 * @param arguments
	 * @return
	 */
	public Object invoke(MagicObject magicObject, Object[] arguments) {
		return invoke(magicObject.getObject(), arguments);
	}

	/**
	 * 获取参数类型
	 * 
	 * @return
	 */
	public abstract MagicList<MagicClass> getParameterTypes();

	/**
	 * 获取目标方法
	 * 
	 * @return
	 */
	public Method getTargetMethod() {
		return targetMethod;
	}

	/**
	 * 获取方法名称
	 * 
	 * @return
	 */
	public String getName() {
		return targetMethod.getName();
	}

}
