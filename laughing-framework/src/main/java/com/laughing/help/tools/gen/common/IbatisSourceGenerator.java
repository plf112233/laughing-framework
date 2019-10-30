package com.laughing.help.tools.gen.common;

/**
 * @Author : lifei.pan
 * @Desc :
 * Date : Created in 2019-01-14
 */
public class IbatisSourceGenerator extends AbstractSourceGenerator {
    private String sqlMapTemplateName = "gen-sqlmap-template-mysql.vm";
    private String genDaoTemplateName = "gen-dao.vm";
    @Override
    public String genPropertyName(String propertyName) {
        return "#" + propertyName + "#";
    }

    @Override
    public String getSqlMapTemplateName() {
        return sqlMapTemplateName;
    }

    @Override
    public String getGenDaoTemplateName() {
        return genDaoTemplateName;
    }
}
