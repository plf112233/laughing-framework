package com.laughing.lang.utils.merger;

import java.util.Collection;
import java.util.Map;

import com.laughing.lang.magic.Transformer;
import com.laughing.lang.utils.CollectionUtil;
import com.laughing.lang.utils.MapUtil;

/**
* @ClassName: CollectionDestPool 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月19日 下午6:14:54 
 */
public class CollectionDestPool<K, D> implements DestPool<K, D> {

	private Map<K, D> map;

	public CollectionDestPool(Collection<D> collection, Transformer<D, K> objectToKey) {
		map = MapUtil.newHashMap();
		if (CollectionUtil.isEmpty(collection)) {
			return;
		}
		for (D d : collection) {
			K key = objectToKey.transform(d);
			map.put(key, d);
		}
	}

	public Map<K, D> toMap() {
		// TODO Auto-generated method stub
		return null;
	}
}
