package com.laughing.lang.utils.merger;

import java.util.Collection;
import java.util.Map;

import com.laughing.lang.magic.Transformer;

/**
* @ClassName: DestPools 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:51:56 
*
 */
public class DestPools {

    public static <K, D> DestPool<K, D> toDestPool(Map<K, D> map) {
        return new MapDestPool<K, D>(map);
    }

    public static <K, D> DestPool<K, D> toDestPool(Collection<D> collection, Transformer<D, K> objectToKey) {
        return new CollectionDestPool<K, D>(collection, objectToKey);
    }

}
