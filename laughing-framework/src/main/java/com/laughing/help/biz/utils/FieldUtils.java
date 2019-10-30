package com.laughing.help.biz.utils;

import java.lang.reflect.Field;

/**
* @ClassName: FieldUtils 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 下午3:21:29 
*
 */
public class FieldUtils {
    static public Field[] getAllFieldsOfClass(Class<?> cls) {
        Field[] fields = new Field[0];
        
        Class<?> itr = cls;
        while ( (null != itr) && !itr.equals(Object.class)) {
            fields = (Field[]) ArrayUtils.addAll(itr.getDeclaredFields(), fields);
            itr = itr.getSuperclass();
        }
        
        return	fields;
    }
}
