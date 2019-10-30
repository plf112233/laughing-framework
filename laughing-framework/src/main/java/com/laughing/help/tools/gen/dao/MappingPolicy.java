package com.laughing.help.tools.gen.dao;

import com.laughing.lang.magic.Property;

public interface MappingPolicy {

	IbatisResult toResult(Property property);

	IbatisResult toDBQueryResult(Class<?> c, Property property);

	IbatisResult toViewResult(Class<?> c, Property property);

	String toTableName(Class<?> clazz);

	public String getTablePrefix();

	public void setTablePrefix(String tablePrefix);
}
