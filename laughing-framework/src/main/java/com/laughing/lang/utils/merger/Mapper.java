package com.laughing.lang.utils.merger;

import java.util.List;

/**
* @ClassName: Mapper 
* @Description:Map工具类
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:51:21 
 */
public interface Mapper<S, K, D> {

    /**
     * 从源业务对象中获取key
     * @param source
     * @return
     */
    K toKey(S source);

    /**
     * 通过一个keys批量转成一个目标对象pool
     * @param keys
     * @return
     */
    DestPool<K, D> toDestPool(List<K> keys);

    /**
     * 把目标对象回写到源业务对象
     * @param source
     * @param dest
     */
    void setDest(S source, D dest);

}
