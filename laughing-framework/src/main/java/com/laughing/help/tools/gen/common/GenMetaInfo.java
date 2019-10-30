package com.laughing.help.tools.gen.common;

import com.laughing.help.tools.gen.dao.ColumnNameContants;
import com.laughing.help.tools.gen.dao.DaoGenUtil;
import com.laughing.help.tools.thead.GenContext;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.Property;
import com.laughing.lang.utils.ClassUtil;
import com.laughing.lang.utils.StringUtil;
import com.laughing.lang.utils.page.DomainType;

public class GenMetaInfo {

    private Class<?> targetDoClass;

    private String idName;

    private String voPackage;

    private String voSimpleClassName;

    private String voFullClassName;

    private String queryPackage;

    private String querySimpleClassName;

    private String queryFullClassName;

    private String doAlias;

    private String doPackage;

    private String doSimpleClassName;

    private String doFullClassName;

    private String baseDalPackage;

    private String idType;

    private String daoSimpleClassName;

    private String daoFullClassName;

    private String daoPackage;

    private String ibatisPackage;

    private String ibatisSimpleClassName;

    private String ibatisFullClassName;

    private String idTypeWrapper;

    private String testDaoSimpleClassName;

    private String testDaoPackage;

    private String testDaoFullClassName;

    private String implSuffix = "Ibatis";

    private Class<?> idClass;

    public GenMetaInfo(Class<?> targetDoClass, String idName) {
        this.targetDoClass = targetDoClass;
        this.idName = idName;
        if (StringUtil.isEmpty(idName)) {
            this.idName = "id";
        }
        init(targetDoClass);
    }

    public String getBaseBizPackage() {
        return StringUtil.getLastBefore(baseDalPackage, ".dal");
    }

    private void init(Class<?> targetDoClass) {
        GenDaoInfo genDaoInfo = GenContext.getReqLogContext().getGenDaoInfo();
        doSimpleClassName = ClassUtil.getShortClassName(targetDoClass);
        doAlias = DaoGenUtil.getDoAlias(targetDoClass);
        daoSimpleClassName = StringUtil.uppercaseFirstLetter(StringUtil.getLastBefore(doAlias, DomainType.DTO)) + "DAO";

        MagicClass magicClass = MagicClass.wrap(targetDoClass);
        Property idProperty = magicClass.getProperty(idName);
        if (idProperty == null) {
            throw new RuntimeException("the id " + idName + " from " + targetDoClass + " not exist!");
        }
        Property gmtModifiedProperty = magicClass.getProperty(ColumnNameContants.gmtModifiedName);
        if (gmtModifiedProperty == null) {
            throw new RuntimeException("the gmtModified: " + ColumnNameContants.gmtModifiedName + " from " + targetDoClass + " not exist!");
        }

        Property gmtCreateProperty = magicClass.getProperty(ColumnNameContants.gmtCreateName);
        if (gmtCreateProperty == null) {
            throw new RuntimeException("the gmtCreate: " + ColumnNameContants.gmtCreateName + " from " + targetDoClass + " not exist!");
        }

        idClass = idProperty.getPropertyClass().getTargetClass();
        idType = ClassUtil.getShortClassName(idClass);
        idTypeWrapper = ClassUtil.getShortClassName(ClassUtil.getWrapperClass(idClass));

        doPackage = targetDoClass.getPackage().getName();
        doFullClassName = targetDoClass.getName();
        baseDalPackage = StringUtil.getLastBefore(doPackage, ".dao.dto");

        daoPackage = baseDalPackage + ".dao";
        daoFullClassName = daoPackage + "." + daoSimpleClassName;
        String actionContext = genDaoInfo.getActionContext();
        if (StringUtil.isNotBlank(actionContext)) {
            voPackage = baseDalPackage + ".entity" + "." + actionContext;
            queryPackage = baseDalPackage + ".entity" + "." + actionContext;
        } else {
            voPackage = baseDalPackage + ".entity";
            queryPackage = baseDalPackage + ".entity";
        }
        voSimpleClassName = StringUtil.getLastBefore(doSimpleClassName, DomainType.DTO) + DomainType.VO;
        querySimpleClassName = StringUtil.getLastBefore(doSimpleClassName, DomainType.DTO) + DomainType.Query;
        voFullClassName = voPackage + "." + voSimpleClassName;
        queryFullClassName = queryPackage + "." + querySimpleClassName;
        ibatisPackage = daoPackage + ".ibatis";
        ibatisSimpleClassName = daoSimpleClassName + implSuffix;
        ibatisFullClassName = ibatisPackage + "." + ibatisSimpleClassName;

        testDaoSimpleClassName = daoSimpleClassName + "Tests";
        testDaoPackage = daoPackage;
        testDaoFullClassName = testDaoPackage + "." + testDaoSimpleClassName;

    }

    public String getBaseDalPackage() {
        return baseDalPackage;
    }

    public String getDaoFullClassName() {
        return daoFullClassName;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public String getDaoSimpleClassName() {
        return daoSimpleClassName;
    }

    public String getDoAlias() {
        return doAlias;
    }

    public String getDoFullClassName() {
        return doFullClassName;
    }

    public String getDoPackage() {
        return doPackage;
    }

    public String getDoSimpleClassName() {
        return doSimpleClassName;
    }

    public String getIbatisPackage() {
        return ibatisPackage;
    }

    public String getIbatisSimpleClassName() {
        return ibatisSimpleClassName;
    }

    public String getIbatisFullClassName() {
        return ibatisFullClassName;
    }

    public String getIdName() {
        return idName;
    }

    public String getIdType() {
        return idType;
    }

    public Class<?> getTargetDoClass() {
        return targetDoClass;
    }

    public Class<?> getIdClass() {
        return idClass;
    }

    public String getIdTypeWrapper() {
        return idTypeWrapper;
    }

    public String getTestDaoFullClassName() {
        return testDaoFullClassName;
    }

    public String getTestDaoPackage() {
        return testDaoPackage;
    }

    public String getTestDaoSimpleClassName() {
        return testDaoSimpleClassName;
    }

    public String getImplSuffix() {
        return implSuffix;
    }

    public void setImplSuffix(String implSuffix) {
        this.implSuffix = implSuffix;
    }

    public String getVoPackage() {
        return voPackage;
    }

    public void setVoPackage(String voPackage) {
        this.voPackage = voPackage;
    }

    public String getVoFullClassName() {
        return voFullClassName;
    }

    public void setVoFullClassName(String voFullClassName) {
        this.voFullClassName = voFullClassName;
    }

    public String getQueryPackage() {
        return queryPackage;
    }

    public void setQueryPackage(String queryPackage) {
        this.queryPackage = queryPackage;
    }

    public String getQueryFullClassName() {
        return queryFullClassName;
    }

    public void setQueryFullClassName(String queryFullClassName) {
        this.queryFullClassName = queryFullClassName;
    }

    public String getVoSimpleClassName() {
        return voSimpleClassName;
    }

    public void setVoSimpleClassName(String voSimpleClassName) {
        this.voSimpleClassName = voSimpleClassName;
    }

    public String getQuerySimpleClassName() {
        return querySimpleClassName;
    }

    public void setQuerySimpleClassName(String querySimpleClassName) {
        this.querySimpleClassName = querySimpleClassName;
    }
}
