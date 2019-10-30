package com.laughing.lang.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 
 * @ClassName: ValuePairAttribute
 * @Description:DB查询标签
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2017年7月5日 上午9:26:52
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DBQueryValueAttribute {
	String name() default "";
}
