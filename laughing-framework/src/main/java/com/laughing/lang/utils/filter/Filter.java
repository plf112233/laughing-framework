package com.laughing.lang.utils.filter;

/**
* @ClassName: Filter 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:40:45 
 */
public interface Filter<T> {

    boolean accept(T t);

}
