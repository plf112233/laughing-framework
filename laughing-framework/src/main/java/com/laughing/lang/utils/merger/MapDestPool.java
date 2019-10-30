package com.laughing.lang.utils.merger;

import java.util.Map;

/**
* @ClassName: MapDestPool 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:51:44 
 */
public class MapDestPool<K, D> implements DestPool<K, D> {

    private Map<K, D> map;

    public MapDestPool(Map<K, D> map) {
        this.map = map;
    }

	public Map<K, D> toMap() {
        return map;
    }
}
