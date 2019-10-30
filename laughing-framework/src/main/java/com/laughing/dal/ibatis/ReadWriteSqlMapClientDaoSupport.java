package com.laughing.dal.ibatis;

import org.springframework.beans.factory.InitializingBean;

import com.laughing.dal.MasterForcer;

/**
* @ClassName: ReadWriteSqlMapClientDaoSupport 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:50:44 
*
 */
public class ReadWriteSqlMapClientDaoSupport implements InitializingBean {

    private ReadWriteSqlMapClientSource readWriteSqlMapClientSource;

    protected SqlExecutor sqlMapExecutor;

    public void afterPropertiesSet() throws Exception {
        sqlMapExecutor = new DefaultSqlExecutor(readWriteSqlMapClientSource);
    }

    public SqlExecutor getSqlExecutor() {
        return  sqlMapExecutor;
    }

    public SqlExecutor getSqlMapClientTemplate() {
        return getSqlExecutor();
    }

    public void setReadWriteSqlMapClientSource(ReadWriteSqlMapClientSource readWriteSqlMapClientSource) {
        this.readWriteSqlMapClientSource = readWriteSqlMapClientSource;
        MasterForcer.setReadWriteSqlMapClientSource(readWriteSqlMapClientSource);
    }
}
