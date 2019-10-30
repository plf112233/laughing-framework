package com.laughing.help.tools.gen.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ColumnNameContext {

	public static Map<String, String> COLUMNNAMEINFOMAP = new ConcurrentHashMap<String, String>();
	static{
		COLUMNNAMEINFOMAP.put("pageSize", "pageSize");
		COLUMNNAMEINFOMAP.put("startRow", "startRow");
		COLUMNNAMEINFOMAP.put("sortName", "sortName");
		COLUMNNAMEINFOMAP.put("sortOrder", "sortOrder");
		COLUMNNAMEINFOMAP.put("tableSuffix", "tableSuffix");
	}
}
