package com.laughing.lang.magic;

import com.ibatis.common.beans.Invoker;

/**
* @ClassName: Interceptor 
* @Description:代理对象的拦截器
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:19:49 
*
 */
public interface Interceptor {

	Object invoke(Object target, Invoker invoker, Object[] arguments) throws Throwable;

}
