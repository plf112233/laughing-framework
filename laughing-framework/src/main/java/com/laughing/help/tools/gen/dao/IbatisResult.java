package com.laughing.help.tools.gen.dao;

public class IbatisResult {

	private String column;

	private String property;

	private String dbQueryAliasName;

	private String viewAliasName;

	private String editAliasName;

	private Class<?> type;

	private String extend;

	private String sqlType;

	private String entityType;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getEditAliasName() {
		return editAliasName;
	}

	public void setEditAliasName(String editAliasName) {
		this.editAliasName = editAliasName;
	}

	public String getExtend() {
		return extend;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getViewAliasName() {
		return viewAliasName;
	}

	public void setViewAliasName(String viewAliasName) {
		this.viewAliasName = viewAliasName;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getSqlType() {
		return sqlType;
	}

	public String getDbQueryAliasName() {
		return dbQueryAliasName;
	}

	public void setDbQueryAliasName(String dbQueryAliasName) {
		this.dbQueryAliasName = dbQueryAliasName;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
}
