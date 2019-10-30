package com.laughing.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

/**
 * @ClassName: SqlExecutor
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2017年5月24日 上午11:50:26
 *
 */
public interface SqlExecutor {
	Object execute(SqlMapClientCallback action);

	Object queryForObject(String statementName);

	Object queryForObject(String statementName, Object parameterObject);

	@SuppressWarnings("rawtypes")
	List queryForList(String statementName);

	@SuppressWarnings("rawtypes")
	List queryForList(String statementName, Object parameterObject);

	Object insert(String statementName);

	Object insert(String statementName, Object parameterObject);

	int update(String statementName);

	int update(String statementName, Object parameterObject);

	int delete(String statementName);

	int delete(String statementName, Object parameterObject);
}
