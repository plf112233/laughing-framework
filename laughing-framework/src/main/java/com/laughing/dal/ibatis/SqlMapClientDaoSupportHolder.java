package com.laughing.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
* @ClassName: SqlMapClientDaoSupportHolder 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:50:03 
*
 */
public class SqlMapClientDaoSupportHolder {

    private static final ThreadLocal<SqlMapClientDaoSupport> sqlMapClientDaoSupportHolder = new ThreadLocal<SqlMapClientDaoSupport>();

    public static void set(SqlMapClientDaoSupport sqlMapClientDaoSupport) {
        sqlMapClientDaoSupportHolder.set(sqlMapClientDaoSupport);
    }

    public static SqlMapClientDaoSupport get() {
       return sqlMapClientDaoSupportHolder.get();
    }



}
