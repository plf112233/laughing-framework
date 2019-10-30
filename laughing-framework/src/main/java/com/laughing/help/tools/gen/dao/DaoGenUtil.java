package com.laughing.help.tools.gen.dao;

import java.io.IOException;
import java.io.Writer;

import com.laughing.lang.magic.cglib.CglibUtil;
import com.laughing.lang.utils.ClassUtil;
import com.laughing.lang.utils.StringUtil;
import com.laughing.lang.utils.SystemUtil;

public class DaoGenUtil {

    public static String getDoAlias(Class<?> clazz) {
        clazz = CglibUtil.getJavaClass(clazz);
        String name = ClassUtil.getShortClassName(clazz);
        name = StringUtil.lowercaseFirstLetter(name);
        if (name.endsWith("DO") || name.endsWith("Do")) {
            return name.substring(0, name.length() - 2);
        } else {
            return name;
        }
    }

    public static void writeLine(Writer out) {
        try {
            out.write(SystemUtil.LINE_SEPARATOR);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
