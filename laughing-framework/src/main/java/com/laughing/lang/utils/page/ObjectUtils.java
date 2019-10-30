package com.laughing.lang.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象工具类
 * @author panlifei
 *
 */
public class ObjectUtils {

	/**
	 * 转换集合
	 * @param sourceList 源集合
	 * @param targetClazz 目标集合元素类型 
	 * @return 转换后的集合
	 */
	public static <T> List<T> convertList(
            List<? extends AbstractObject> sourceList, Class<T> targetClazz) throws Exception {
		if(sourceList == null) {
			return null;
		}
		
		List<T> targetList = new ArrayList<T>();
		for(AbstractObject sourceObject : sourceList) {
			targetList.add(sourceObject.clone(targetClazz));    
		}
		
		return targetList;
	}
	
	/**
	 * 转换集合-深度克隆
	 * @param sourceList 源集合
	 * @param targetClazz 目标集合元素类型 
	 * @return 转换后的集合
	 */
	public static <T> List<T> convertList(List<? extends AbstractObject> sourceList,
                                          Class<T> targetClazz, Integer cloneDirection) throws Exception {
		if(sourceList == null) {
			return null;
		}
		
		List<T> targetList = new ArrayList<T>();
		for(AbstractObject sourceObject : sourceList) {
			targetList.add(sourceObject.clone(targetClazz, cloneDirection));      
		}
		
		return targetList;
	}
	
}
