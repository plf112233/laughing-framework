package com.laughing.help.tools.gen.dao;

import com.laughing.lang.magic.Property;
import com.laughing.lang.utils.AnnotationUtils;
import com.laughing.lang.utils.StringUtil;

public class DefaultMappingPolicy implements MappingPolicy {

	private String tablePrefix = "";

	public DefaultMappingPolicy() {
		super();
	}

	public DefaultMappingPolicy(String tablePrefix) {
		super();
		this.tablePrefix = tablePrefix;
	}

	public IbatisResult toResult(Property property) {
		IbatisResult ret = new IbatisResult();
		ret.setProperty(property.getName());
		ret.setType(property.getPropertyClass().getTargetClass());
		ret.setColumn(StringUtil.camelToUnderLineString(property.getName()));
		return ret;
	}

	public IbatisResult toDBQueryResult(Class<?> c, Property property) {
		String propertyName = property.getName();
		String dbQueryAliasName = AnnotationUtils.getDBQueryValue(c, propertyName);
		IbatisResult ret = new IbatisResult();
		ret.setProperty(property.getName());
		ret.setDbQueryAliasName(dbQueryAliasName);
		ret.setType(property.getPropertyClass().getTargetClass());
		ret.setColumn(StringUtil.camelToUnderLineString(property.getName()));
		return ret;
	}

	public IbatisResult toViewResult(Class<?> c, Property property) {
		String propertyName = property.getName();
		String viewAliasName = AnnotationUtils.getViewValue(c, propertyName);
		String editAliasName = AnnotationUtils.getEditValue(c, propertyName);
		IbatisResult ret = new IbatisResult();
		ret.setProperty(property.getName());
		ret.setViewAliasName(viewAliasName);
		ret.setEditAliasName(editAliasName);
		ret.setType(property.getPropertyClass().getTargetClass());
		ret.setColumn(StringUtil.camelToUnderLineString(property.getName()));
		return ret;
	}

	public String toTableName(Class<?> clazz) {
		String className = clazz.getSimpleName();
		if (className.endsWith("DO")) {
			className = StringUtil.getLastBefore(className, "DO");
		}
		return tablePrefix + StringUtil.camelToUnderLineString(className);
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

}
