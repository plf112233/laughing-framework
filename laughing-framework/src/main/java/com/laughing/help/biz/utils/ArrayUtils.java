package com.laughing.help.biz.utils;

import java.lang.reflect.Array;

/**
* @ClassName: ArrayUtils 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 下午3:21:09 
*
 */
public class ArrayUtils {
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	
	public static Object[] clone(Object[] array) {
        if (array == null) {
            return null;
        }
        return (Object[]) array.clone();
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[] addAll(Object[] array1, Object[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        Object[] joinedArray = (Object[]) Array.newInstance(array1.getClass().getComponentType(),
                                                            array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class type1 = array1.getClass().getComponentType();
            final Class type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "+type1.getName());
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }
}

