package com.laughing.dal.ibatis;

/**
* @ClassName: LoadBalancePolicy 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:50:55 
*
 */
public interface LoadBalancePolicy {

    int getNext();

}
