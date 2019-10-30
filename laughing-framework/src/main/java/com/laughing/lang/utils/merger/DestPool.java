package com.laughing.lang.utils.merger;

import java.util.Map;

/**
 * @ClassName: DestPool
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2016年10月20日 下午5:52:50
 */
public interface DestPool<K, D> {

	Map<K, D> toMap();

}
