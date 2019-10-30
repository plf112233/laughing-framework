package com.laughing.help.tools.gen.common;

public class GenDaoInfo {
    public static final String DATA_FRAMEWORK_NAME_MYBATIS = "mybatis";
    public static final String DATA_FRAMEWORK_NAME_IBATIS = "ibatis";
    private String frameWorkName = "mybatis";
    private String daoClassSuffix = "Ibtais";
    private String sqlMapClientName = "sqlMapClient";

    private String actionContext;
    private String tableName;
    /**
     * 模板文件路径名
     */
    private String templateFilePath;
    /**
     * 是否是逻辑删除
     */
    private boolean logicDeleted;

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public boolean isLogicDeleted() {
        return logicDeleted;
    }

    public void setLogicDeleted(boolean logicDeleted) {
        this.logicDeleted = logicDeleted;
    }

    public String getDaoClassSuffix() {
        return daoClassSuffix;
    }

    public void setDaoClassSuffix(String daoClassSuffix) {
        this.daoClassSuffix = daoClassSuffix;
    }

    public String getSqlMapClientName() {
        return sqlMapClientName;
    }

    public void setSqlMapClientName(String sqlMapClientName) {
        this.sqlMapClientName = sqlMapClientName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFrameWorkName() {
        return frameWorkName;
    }

    public void setFrameWorkName(String frameWorkName) {
        this.frameWorkName = frameWorkName;
    }

    public String getActionContext() {
        return actionContext;
    }

    public void setActionContext(String actionContext) {
        this.actionContext = actionContext;
    }

    @Override
    public String toString() {
        return "GenDaoInfo{" +
                "frameWorkName='" + frameWorkName + '\'' +
                ", daoClassSuffix='" + daoClassSuffix + '\'' +
                ", sqlMapClientName='" + sqlMapClientName + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
