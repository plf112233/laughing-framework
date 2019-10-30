package com.laughing.help.tools.gen.common;

/**
 * @Author : lifei.pan
 * @Desc :
 * Date : Created in 2019-01-14
 */
public class MybatisSourceGenerator extends AbstractSourceGenerator {
    private String sqlMapMybatisTemplateName = "gen-sqlmap-mybatis-template-mysql.vm";
    private String genMybatisDaoTemplateName = "gen-mybatis-dao.vm";
    @Override
    public String genPropertyName(String propertyName) {
        return "#{" + propertyName + "}";
    }

    @Override
    public String getSqlMapTemplateName() {
        return sqlMapMybatisTemplateName;
    }

    @Override
    public String getGenDaoTemplateName() {
        return genMybatisDaoTemplateName;
    }
}
