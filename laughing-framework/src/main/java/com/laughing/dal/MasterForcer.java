package com.laughing.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.laughing.dal.ibatis.ReadWriteSqlMapClientSource;
import com.laughing.dal.ibatis.SqlMapClientTarget;

/**
* @ClassName: MasterForcer 
* @Description:
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:49:30 
*
 */
public class MasterForcer {

    private static final Logger log = LoggerFactory.getLogger(MasterForcer.class);

    static final ThreadLocal<SqlMapClientTemplate> masterSqlMapClientTemplate = new ThreadLocal<SqlMapClientTemplate>();

    private static ReadWriteSqlMapClientSource readWriteSqlMapClientSource;

    public static void beginForce() {
        if (log.isDebugEnabled()) {
              log.debug("begin force master .");
        }
        SqlMapClientTemplate master = readWriteSqlMapClientSource.getSqlMapClient(SqlMapClientTarget.MASTER);
        masterSqlMapClientTemplate.set(master);
    }

    public static void endForce() {
        if (log.isDebugEnabled()) {
            log.debug("end force master .");
        }
        masterSqlMapClientTemplate.remove();
    }

    public static SqlMapClientTemplate getSqlMapClientTemplate() {
        return masterSqlMapClientTemplate.get();
    }

    public static void setReadWriteSqlMapClientSource(ReadWriteSqlMapClientSource readWriteSqlMapClientSource) {
        MasterForcer.readWriteSqlMapClientSource = readWriteSqlMapClientSource;
    }
}
