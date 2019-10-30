package com.laughing.lang.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: ValuePairAttribute
 * @Description:数据库查询
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2017年7月5日 上午9:26:52
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DBValueAttribute {
	String name() default "";
}
