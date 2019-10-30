package com.laughing.help.tools.gen.dao;

import com.laughing.lang.utils.MapUtil;
import com.laughing.lang.utils.StringUtil;

import java.util.Map;

public class EntityTypes {

    static Map<Class<?>, String> types = MapUtil.newHashMap();
    static {
        types.put(Boolean.TYPE, "Boolean");
        types.put(Boolean.class, "Boolean");

        types.put(Byte.TYPE, "Byte");
        types.put(Byte.class, "Byte");

        types.put(Short.TYPE, "Short");
        types.put(Short.class, "Short");

        types.put(Character.TYPE, "Character");
        types.put(Character.class, "Character");

        types.put(Integer.TYPE, "Integer");
        types.put(Integer.class, "Integer");

        types.put(Long.TYPE, "Long");
        types.put(Long.class, "Long");

        types.put(Float.TYPE, "Float");
        types.put(Float.class, "Float");

        types.put(Double.TYPE, "Double");
        types.put(Double.class, "Double");

        types.put(String.class, "String");

        types.put(java.sql.Date.class, "Date");
        types.put(java.sql.Timestamp.class, "Date");
        types.put(java.util.Date.class, "Date");


    }


    public static String getByClass(Class<?> clazz) {
        String ret = types.get(clazz);
        if (StringUtil.isEmpty(ret)) {
            return "位置类型";
        }
        return ret;
    }

}
