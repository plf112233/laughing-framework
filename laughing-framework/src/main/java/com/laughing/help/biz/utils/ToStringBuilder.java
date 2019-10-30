package com.laughing.help.biz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
* @ClassName: ToStringBuilder 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 下午3:21:37 
*
 */
public class ToStringBuilder {
	
	public static final int STYPE_PRINT_NULL = 1;		//打印空对象
    public static final int STYPE_PRINT_NOT_NULL = 2;	//不打印空对象
    
    public static String toString(Object object, int style) {
    	return toString(object, style, null);
    }
    
    public static String toString(Object object, int style, String[] excludeNames) {
        try {
            if (object == null) {
                return "";
            }
            
            Class<?> clazz = object.getClass();
            Field[] fields = FieldUtils.getAllFieldsOfClass(clazz);
            String className = clazz.getSimpleName();
            
            return className+"["+printFieldsInStyle(object, fields, style, excludeNames)+"]";
        } catch (Exception e) {
            //toString方法不能抛出异常，否则可能会影响正常业务
        }    
        
        return "toString failed!";
    }
    
    private static String printFieldsInStyle(Object obj, Field[] fields, int style, String[] excludeNames) {
        if (fields == null || fields.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        
        int cnt = 0;
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                // Reject static fields.
                continue;
            }
            
            if (Modifier.isTransient(field.getModifiers())) {
                // Reject transient fields.
                continue;
            }
            
            if (Modifier.isFinal(field.getModifiers())) {
                // Reject final fields.
                continue;
            }
            
            boolean isExcludeName = false;
			if (excludeNames != null && excludeNames.length > 0) {
		    	for (String excludeName : excludeNames) {
					if (excludeName.equals(field.getName())) {
						isExcludeName = true;
						break;
					}
				}
			}
			if (isExcludeName) {
				continue;
			}
            
            cnt++;
            
            Object object;
            try {
                field.setAccessible(true);
                object = field.get(obj);
                
                if (object == null && style == STYPE_PRINT_NOT_NULL) {
                    //为空不打印
                    continue;
                }
                
                if (cnt > 1) {
                    sb.append(", ");
                }
                
                if (object instanceof Collection) {
                    Collection<?> list = (Collection<?>)object;
                    sb.append(field.getName()).append("(SIZE=").append(list.size()).append(")=").append(object);
                }else {
                    sb.append(field.getName()).append("=").append(object);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return sb.toString();
    }
}
