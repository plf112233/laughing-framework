package com.laughing.dal.ibatis;

import java.util.Random;

/**
* @ClassName: RandomLoadBalancePolicy 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:50:50 
*
 */
public class RandomLoadBalancePolicy implements LoadBalancePolicy {

    private int size;

    private Random rand = new Random(System.currentTimeMillis());

    public RandomLoadBalancePolicy(int size) {
        this.size = size;
    }

    public int getNext() {
        return rand.nextInt(size);
    }
}
