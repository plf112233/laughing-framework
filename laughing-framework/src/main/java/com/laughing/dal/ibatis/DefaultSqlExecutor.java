package com.laughing.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.laughing.dal.MasterForcer;

/**
* @ClassName: DefaultSqlExecutor 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 上午11:51:02 
*
 */
@Component
public class DefaultSqlExecutor implements SqlExecutor {
    private ReadWriteSqlMapClientSource readWriteSqlMapClientSource;

    public DefaultSqlExecutor(ReadWriteSqlMapClientSource readWriteSqlMapClientSource) {
        this.readWriteSqlMapClientSource = readWriteSqlMapClientSource;
    }

    public void setReadWriteSqlMapClientSource(ReadWriteSqlMapClientSource readWriteSqlMapClientSource) {
		this.readWriteSqlMapClientSource = readWriteSqlMapClientSource;
	}

	protected SqlMapClientTemplate getWriteSqlMapClientTemplate() {
        SqlMapClientTemplate sqlMapClientTemplate = MasterForcer.getSqlMapClientTemplate();
        if (sqlMapClientTemplate != null) {
            return sqlMapClientTemplate;
        }
        return readWriteSqlMapClientSource.getSqlMapClient(SqlMapClientTarget.MASTER);
    }

    protected SqlMapClientTemplate getReadSqlMapClientTemplate() {
        SqlMapClientTemplate sqlMapClientTemplate = MasterForcer.getSqlMapClientTemplate();
        if (sqlMapClientTemplate != null) {
            return sqlMapClientTemplate;
        }
        return readWriteSqlMapClientSource.getSqlMapClient(SqlMapClientTarget.SLAVE);
    }

    public Object execute(SqlMapClientCallback action) {
        return getWriteSqlMapClientTemplate().execute(action);
    }

    public Object queryForObject(String statementName) {
        return getReadSqlMapClientTemplate().queryForObject(statementName);
    }

    public Object queryForObject(String statementName, Object parameterObject) {
        return getReadSqlMapClientTemplate().queryForObject(statementName, parameterObject);
    }

    @SuppressWarnings("rawtypes")
	public List queryForList(String statementName) {
        return getReadSqlMapClientTemplate().queryForList(statementName);
    }

    @SuppressWarnings("rawtypes")
	public List queryForList(String statementName, Object parameterObject) {
        return getReadSqlMapClientTemplate().queryForList(statementName, parameterObject);
    }

    public Object insert(String statementName) {
        return getWriteSqlMapClientTemplate().insert(statementName);
    }

    public Object insert(String statementName, Object parameterObject) {
        return getWriteSqlMapClientTemplate().insert(statementName, parameterObject);
    }

    public int update(String statementName) {
        return getWriteSqlMapClientTemplate().update(statementName);
    }

    public int update(String statementName, Object parameterObject) {
        return getWriteSqlMapClientTemplate().update(statementName, parameterObject);
    }

    public int delete(String statementName) {
        return getWriteSqlMapClientTemplate().delete(statementName);
    }

    public int delete(String statementName, Object parameterObject) {
        return getWriteSqlMapClientTemplate().delete(statementName, parameterObject);
    }

}
