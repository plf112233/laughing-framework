package com.laughing.help.tools.thead;

import com.laughing.help.tools.gen.common.GenDaoInfo;

/**
 * 请求账单库日志管理上下文
 */
public class GenContext {
    private static final ThreadLocal<GenContext> LOCAL = new ThreadLocal<GenContext>() {
        @Override
        protected GenContext initialValue() {
            return new GenContext();
        }
    };


    public static GenContext getReqLogContext() {
        return LOCAL.get();
    }


    public static void removeReqLogContext() {
        LOCAL.remove();
    }

    protected GenContext() {
    }

    GenDaoInfo genDaoInfo = new GenDaoInfo();

    public GenDaoInfo getGenDaoInfo() {
        return genDaoInfo;
    }

    public void setGenDaoInfo(GenDaoInfo genDaoInfo) {
        this.genDaoInfo = genDaoInfo;
    }
}
