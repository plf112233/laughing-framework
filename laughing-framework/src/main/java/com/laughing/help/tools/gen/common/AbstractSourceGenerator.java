package com.laughing.help.tools.gen.common;

import com.laughing.help.biz.annations.Domain;
import com.laughing.help.tools.gen.dao.*;
import com.laughing.lang.LaughingException;
import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.Property;
import com.laughing.lang.utils.ClassUtil;
import com.laughing.lang.utils.MapUtil;
import com.laughing.lang.utils.StringUtil;
import com.laughing.lang.utils.SystemUtil;
import com.laughing.lang.utils.page.DomainType;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.*;

public abstract class AbstractSourceGenerator {

    private TemplateSourceGenator templateSourceGenator = new TemplateSourceGenator();

    private static final Map<Class<?>, String> typeDefaults = new HashMap<Class<?>, String>();
    private static final Map<Class<?>, String> type2sql = new HashMap<Class<?>, String>();

    static {
        typeDefaults.put(Boolean.TYPE, "false");
        typeDefaults.put(Boolean.class, "false");
        typeDefaults.put(Short.TYPE, "(short)1");
        typeDefaults.put(Short.class, "(short)1");
        typeDefaults.put(Integer.TYPE, "2");
        typeDefaults.put(Integer.class, "2");
        typeDefaults.put(Long.TYPE, "3L");
        typeDefaults.put(Long.class, "3L");
        typeDefaults.put(Float.TYPE, "1.1");
        typeDefaults.put(Float.class, "1.1");
        typeDefaults.put(Double.TYPE, "1.2");
        typeDefaults.put(Double.class, "1.2");
        typeDefaults.put(String.class, "\"a\"");
        typeDefaults.put(Date.class, "new java.util.Date()");
        typeDefaults.put(java.sql.Date.class, "new java.sql.Date(new java.util.Date().getTime())");
        typeDefaults.put(Timestamp.class, "new java.sql.Timestamp(new Date().getTime())");

        type2sql.put(Integer.TYPE, "int");
        type2sql.put(Integer.class, "int");
        type2sql.put(Long.TYPE, "bigint");
        type2sql.put(Long.class, "bigint");
        type2sql.put(String.class, "varchar(?)");
    }

    private MappingPolicy mappingPolicy = new DefaultMappingPolicy();

    private ResultRender resultRender = new DefaultResultRender();

    private String idName = "id";

    private boolean logicDeleted = true;

    private String encoding = "utf-8";

    private String createTableTemplateName = "create-table-template-mysql.vm";

    private String genIbatisDaoTemplateName = "gen-ibatis-dao.vm";

    private String genTestsTemplateName = "gen-tests.vm";

    private String genFormTemplateName = "gen-form-template.vm";

    private String genFormTemplateNameCn = "gen-form-template-cn.vm";

    private String genJavaActionTemplateName = "gen-java-action.vm";

    private String genJavaAOTemplateName = "gen-java-ao.vm";

    private String genJavaAOImplTemplateName = "gen-java-ao-impl.vm";

    private boolean withName = false;

    protected abstract String getSqlMapTemplateName();

    protected abstract String getGenDaoTemplateName();

    public String setTablePrefix(String prefix) {
        String ret = mappingPolicy.getTablePrefix();
        mappingPolicy.setTablePrefix(prefix);
        return ret;
    }

    public GenMetaInfo genSqlMap(Class<?> clazz, Writer out, GenDaoInfo genDaoInfo) {
        GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);

        Map<String, Object> context = MapUtil.newHashMap();
        String alias = DaoGenUtil.getDoAlias(clazz);
        String mappingName = alias + "ResultMap";
        String tableName = null;
        if (StringUtil.isNotBlank(genDaoInfo.getTableName())) {
            tableName = genDaoInfo.getTableName();
        } else {
            tableName = mappingPolicy.getTablePrefix() + StringUtil.camelToUnderLineString(alias);
        }
        String idType = genMetaInfo.getIdType();
        context.put("encoding", encoding);
        context.put("namespace", genMetaInfo.getDaoSimpleClassName());
        context.put("domainName", genMetaInfo.getDoFullClassName());
        context.put("daoFullClassName", genMetaInfo.getDaoFullClassName());
        context.put("simpleName", genMetaInfo.getDoSimpleClassName());
        context.put("paramName", genMetaInfo.getDoAlias());
        context.put("aliasDomainName", genMetaInfo.getDoAlias());
        context.put("mappingName", mappingName);
        context.put("voPackage", genMetaInfo.getVoPackage());
        context.put("queryPackage", genMetaInfo.getQueryPackage());
        context.put("voSimpleClassName", genMetaInfo.getVoSimpleClassName());
        context.put("querySimpleClassName", genMetaInfo.getQuerySimpleClassName());
        context.put("voFullClassName", genMetaInfo.getVoFullClassName());
        context.put("queryFullClassName", genMetaInfo.getQueryFullClassName());
        Set<String> mappingFilters = new HashSet<String>();
        mappingFilters.add("class");
        List<IbatisResult> mappingColumns = getResult(clazz, mappingFilters, OptionEnum.READ_AND_WRITE);
        List<IbatisResult> dbQUeryColumns = getDbQUeryResult(clazz, mappingFilters, OptionEnum.READ_AND_WRITE);
        context.put("mappingColumns", mappingColumns);
        context.put("dbQueryColumns", dbQUeryColumns);
        String fullSQLColumns = genFullSQLColumns(clazz);
        context.put("fullSQLColumns", fullSQLColumns);
        String genInsertNoId = genInsertNoId(clazz, genDaoInfo);
        String genInsertBatchNoId = genInsertBatchNoId(clazz,genDaoInfo);
        String genUpdateNoId = genUpdateNoId(clazz, genDaoInfo);
        context.put("insertSqlNoId", genInsertNoId);
        context.put("insertBatchNoId", genInsertBatchNoId);
        context.put("genUpdateNoId", genUpdateNoId);
        context.put("idType", idType);
        context.put("tableName", tableName);
        context.put("logicDeleted", genDaoInfo.isLogicDeleted());
        context.put("idNameColumn", StringUtil.camelToUnderLineString(idName));
        context.put("idNameProperty", StringUtil.fixedCharToCamel(idName, "_-"));
        context.put("gmtModifiedName", StringUtil.camelToUnderLineString(ColumnNameContants.gmtModifiedName));
        context.put("gmtCreateName", ColumnNameContants.gmtCreateName);
        genFromTemplate(context, out, getSqlMapTemplateName());
        DaoGenUtil.writeLine(out);
        return genMetaInfo;
    }

    public GenMetaInfo genDAO(Class<?> clazz, Writer out, GenDaoInfo genDaoInfo) {
        GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);

        Map<String, Object> context = MapUtil.newHashMap();

        context.put("thisPackage", genMetaInfo.getDaoPackage());
        context.put("doFullClassName", genMetaInfo.getDoFullClassName());

        context.put("className", genMetaInfo.getDaoSimpleClassName());
        context.put("voPackage", genMetaInfo.getVoPackage());
        context.put("queryPackage", genMetaInfo.getQueryPackage());
        context.put("voSimpleClassName", genMetaInfo.getVoSimpleClassName());
        context.put("querySimpleClassName", genMetaInfo.getQuerySimpleClassName());
        context.put("voFullClassName", genMetaInfo.getVoFullClassName());
        context.put("queryFullClassName", genMetaInfo.getQueryFullClassName());
        context.put("namespace", genMetaInfo.getDaoSimpleClassName());
        context.put("idType", genMetaInfo.getIdType());
        context.put("idTypeWrapper", genMetaInfo.getIdTypeWrapper());
        context.put("domainName", genMetaInfo.getDoSimpleClassName());
        context.put("paramName", genMetaInfo.getDoAlias());
        genFromTemplate(context, out, getGenDaoTemplateName());
        DaoGenUtil.writeLine(out);

        return genMetaInfo;
    }

    private String getBaseTestPackage(String doPackage) {
        return StringUtil.getLastBefore(doPackage, ".biz.");
    }

    private String propertySetter(Class<?> clazz, String doObjectName) {
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        filters.add(idName);
        List<IbatisResult> columnsResult = getResult(clazz, filters, OptionEnum.READ_AND_WRITE);
        StringBuilder sb = new StringBuilder();
        for (IbatisResult ibatisResult : columnsResult) {
            String name = ibatisResult.getProperty();
            String value = getTestValueForType(ibatisResult.getType());
            if (value == null) {
                continue;
            }
            sb.append("\t\t");
            sb.append(doObjectName);
            String setterName = ".set" + StringUtil.uppercaseFirstLetter(name);
            sb.append(setterName);
            sb.append("(");
            sb.append(value);
            sb.append(");");
            sb.append(SystemUtil.LINE_SEPARATOR);
        }
        String ret = sb.toString();
        if (ret.endsWith(SystemUtil.LINE_SEPARATOR)) {
            ret = ret.substring(0, ret.length() - SystemUtil.LINE_SEPARATOR.length());
        }
        return ret;
    }

    public String getActionContext(String actionContext) {
        if (StringUtil.isEmpty(actionContext)) {
            return "";
        }
        return actionContext + "/";
    }

    private String getTestValueForType(Class<?> type) {
        String value = AbstractSourceGenerator.typeDefaults.get(type);
        return value;
    }

    public String genJavaAOImpl(Class<?> domainClass, String serviceContext, Writer out) {
        Map<String, Object> context = MapUtil.newHashMap();
        String alias = DaoGenUtil.getDoAlias(domainClass);
        String domainUpper = StringUtil.uppercaseFirstLetter(alias);
        String aoClassName = domainUpper + "Service";

        GenMetaInfo genMetaInfo = new GenMetaInfo(domainClass, idName);
        String baseServicePackage = genMetaInfo.getBaseBizPackage() + ".service";
        String targetActionPackage;
        String actionContextPackage;
        serviceContext = normalizeContext(serviceContext);
        if (!StringUtil.isEmpty(serviceContext)) {
            actionContextPackage = serviceContext.replace('/', '.');
            targetActionPackage = baseServicePackage + "." + actionContextPackage;
        } else {
            targetActionPackage = baseServicePackage;
            actionContextPackage = "";
        }
        String aoFullName = targetActionPackage + "." + aoClassName;

        String domainName = ClassUtil.getShortClassName(domainClass);
        String domainParam = StringUtil.lowercaseFirstLetter(domainName);

        String thisPackage = targetActionPackage + ".impl";
        String thisClassName = aoClassName + "Impl";
        String aoImplFullClassName = thisPackage + "." + thisClassName;
        String idType = genMetaInfo.getIdType();

        String daoClassName = genMetaInfo.getDaoSimpleClassName();

        String daoBeanName = StringUtil.lowercaseFirstLetter(daoClassName);
        String daoParamName = daoBeanName;
        String domainInDb = alias + "Indb";
        String idGetter = "get" + StringUtil.uppercaseFirstLetter(idName);

        MagicClass magicClass = MagicClass.wrap(domainClass);
        Map<String, Property> propertyMap = magicClass.getProperties();
        List<DomainField> fields = new ArrayList<DomainField>();
        for (Map.Entry<String, Property> entry : propertyMap.entrySet()) {
            Property property = entry.getValue();
            String name = entry.getKey();
            if (!property.isWritable() || !property.isReadable()) {
                continue;
            }
            if (name.equals(idName) || name.equals(ColumnNameContants.gmtModifiedName)
                    || name.equals(ColumnNameContants.gmtCreateName)) {
                continue;
            }

            fields.add(new DomainField(name, property.getPropertyClass().getTargetClass()));
        }

        context.put("fields", fields);
        context.put("daoFullClassName", genMetaInfo.getDaoFullClassName());
        context.put("domainInDb", domainInDb);
        context.put("idGetter", idGetter);
        context.put("aliasAllUpper", alias.toUpperCase());
        context.put("daoParamName", daoParamName);
        context.put("daoBeanName", daoBeanName);
        context.put("daoClassName", daoClassName);
        context.put("idType", idType);
        context.put("alias", alias);
        context.put("domainFullName", domainClass.getName());
        context.put("aoClassName", aoClassName);
        context.put("thisPackage", thisPackage);
        context.put("thisClassName", thisClassName);
        context.put("aoFullName", aoFullName);
        context.put("domainParam", domainParam);
        context.put("domainName", domainName);
        context.put("domainUpper", domainUpper);

        templateSourceGenator.genSource(context, out, genJavaAOImplTemplateName);
        return aoImplFullClassName;
    }

    public String genJavaAO(Class<?> domainClass, String actionContext, Writer out) {
        Map<String, Object> context = MapUtil.newHashMap();
        String domainUpper = StringUtil.uppercaseFirstLetter(DaoGenUtil.getDoAlias(domainClass));
        String aoClassName = domainUpper + "Service";

        GenMetaInfo genMetaInfo = new GenMetaInfo(domainClass, idName);
        String baseActionPackage = genMetaInfo.getBaseBizPackage() + ".service";
        String targetActionPackage;
        String actionContextPackage;
        actionContext = normalizeContext(actionContext);
        if (!StringUtil.isEmpty(actionContext)) {
            actionContextPackage = actionContext.replace('/', '.');
            targetActionPackage = baseActionPackage + "." + actionContextPackage;
        } else {
            targetActionPackage = baseActionPackage;
            actionContextPackage = "";
        }
        String aoFullName = targetActionPackage + "." + aoClassName;
        String domainName = ClassUtil.getShortClassName(domainClass);
        String domainParam = StringUtil.lowercaseFirstLetter(domainName);

        String idType = genMetaInfo.getIdType();
        String idTypeUpper = StringUtil.uppercaseFirstLetter(idType);

        context.put("idTypeUpper", idTypeUpper);
        context.put("idType", idType);
        context.put("domainFullName", domainClass.getName());
        context.put("domainParam", domainParam);
        context.put("domainName", domainName);
        context.put("domainUpper", domainUpper);
        context.put("thisClassName", aoClassName);
        context.put("thisPackage", targetActionPackage);

        templateSourceGenator.genSource(context, out, genJavaAOTemplateName);
        return aoFullName;
    }

    private void renderCreate(Class<?> domainClass, File moduleDir, String actionContext, FileWriter fileWriter) {
        Map<String, Object> context = MapUtil.newHashMap();

        String alias = DaoGenUtil.getDoAlias(domainClass);
        String domainName = ClassUtil.getShortClassName(domainClass);
        String addAction = "${base.getContextPath()}/" + getActionContext(actionContext) + alias + "/add.do";
        String messaageInfo = "if ('${message?if_exists}' != '') {toastr.error('${message?if_exists}');}";
        Set<String> mappingFilters = new HashSet<String>();
        mappingFilters.add("class");
        List<IbatisResult> mappingColumns = getEditResult(domainClass, mappingFilters, OptionEnum.READ_AND_WRITE);
        context.put("mappingColumns", mappingColumns);
        context.put("domainName", domainName);
        context.put("alias", alias);
        context.put("addAction", addAction);
        context.put("messaageInfo", messaageInfo);
        context.put("statusList", StatusEnum.getStatusEnumList());
        String targetName = getCreateName(alias) + ".ftl";

        File createFile = new File(moduleDir, targetName);
        templateSourceGenator.genSource(context, "gen-templates-create-vm.vm", createFile, fileWriter);

    }

    private String getCreateName(String alias) {
        if (withName) {
            return "create" + StringUtil.uppercaseFirstLetter(alias);
        } else {
            return "create";
        }
    }

    private String getEditName(String alias) {
        if (withName) {
            return "edit" + StringUtil.uppercaseFirstLetter(alias);
        } else {
            return "edit";
        }
    }

    private String getListName(String alias) {
        if (withName) {
            return "list" + StringUtil.uppercaseFirstLetter(alias);
        } else {
            return "list";
        }
    }

    private String getDetailName(String alias) {
        if (withName) {
            return alias + "Detail";
        } else {
            return "detail";
        }
    }

    private void renderList(Class<?> domainClass, File moduleDir, String actionContext, FileWriter fileWriter) {
        Map<String, Object> context = MapUtil.newHashMap();

        String alias = DaoGenUtil.getDoAlias(domainClass);
        String domainName = ClassUtil.getShortClassName(domainClass);
        String headInfo = "<#include \"/common/taglib.ftl\"/>";
        String foreachStart = "#foreach($" + alias + " in $" + alias + "s)";

        String end = "#end";
        String createPageAction = "${base.getContextPath()}/" + getActionContext(actionContext) + alias + "/addView.do";
        String deleteDoAction = "${base.getContextPath()}/" + getActionContext(actionContext) + alias + "/del.do";
        String editPageAction = "'${base.getContextPath()}/" + getActionContext(actionContext) + alias
                + "/editView.do?id='+id";
        String tablePageAction = "${base.getContextPath()}/" + getActionContext(actionContext) + alias + "/table.do";
        MagicClass magicClass = MagicClass.wrap(domainClass);
        Map<String, Property> propertyMap = magicClass.getProperties();
        List<DomainField> fields = new ArrayList<DomainField>();
        for (Map.Entry<String, Property> entry : propertyMap.entrySet()) {
            Property property = entry.getValue();
            String name = entry.getKey();
            if (!property.isWritable() || !property.isReadable()) {
                continue;
            }
            if (name.equals(ColumnNameContants.gmtModifiedName) || name.equals(ColumnNameContants.gmtCreateName)) {
                continue;
            }

            fields.add(new DomainField(name, property.getPropertyClass().getTargetClass()));
        }
        Set<String> mappingFilters = new HashSet<String>();
        mappingFilters.add("class");
        List<IbatisResult> mappingColumns = getViewResult(domainClass, mappingFilters, OptionEnum.READ_AND_WRITE);
        context.put("mappingColumns", mappingColumns);
        context.put("domainName", domainName);
        context.put("fields", fields);
        context.put("alias", alias);
        context.put("foreachStart", foreachStart);
        context.put("headInfo", headInfo);
        context.put("end", end);
        context.put("createPageAction", createPageAction);
        context.put("deleteDoAction", deleteDoAction);
        context.put("editPageAction", editPageAction);
        context.put("tablePageAction", tablePageAction);

        String targetName = getListName(alias) + ".ftl";

        File createFile = new File(moduleDir, targetName);
        templateSourceGenator.genSource(context, "gen-templates-list-vm.vm", createFile, fileWriter);
    }

    @SuppressWarnings("unused")
    private void renderDetail(Class<?> domainClass, File moduleDir, String actionContext, FileWriter fileWriter) {
        Map<String, Object> context = MapUtil.newHashMap();

        String alias = DaoGenUtil.getDoAlias(domainClass);

        String listPageAction = "$baseModule.setTarget('" + getActionContext(actionContext) + alias + "/list')";
        String editPageAction = "$baseModule.setTarget('" + getActionContext(actionContext) + alias
                + "/edit').param('id', $" + alias + ".id)";

        MagicClass magicClass = MagicClass.wrap(domainClass);
        Set<String> propertyNames = new TreeSet<String>(magicClass.getReadableProperties().keySet());

        context.put("alias", alias);
        context.put("propertyNames", propertyNames);
        context.put("editPageAction", editPageAction);
        context.put("listPageAction", listPageAction);

        String targetName = getDetailName(alias) + ".vm";

        File createFile = new File(moduleDir, targetName);
        templateSourceGenator.genSource(context, "gen-templates-detail-vm.vm", createFile, fileWriter);
    }

    private void renderEdit(Class<?> domainClass, File moduleDir, String actionContext, FileWriter fileWriter) {
        Map<String, Object> context = MapUtil.newHashMap();
        String alias = DaoGenUtil.getDoAlias(domainClass);
        String domainName = ClassUtil.getShortClassName(domainClass);
        String domainParam = StringUtil.lowercaseFirstLetter(domainName);
        String editAction = "${base.getContextPath()}/" + getActionContext(actionContext) + alias + "/edit.do";
        String messaageInfo = "if ('${message?if_exists}' != '') {toastr.error('${message?if_exists}');}";
        Set<String> mappingFilters = new HashSet<String>();
        mappingFilters.add("class");
        List<IbatisResult> mappingColumns = getEditResult(domainClass, mappingFilters, OptionEnum.READ_AND_WRITE);
        context.put("mappingColumns", mappingColumns);
        context.put("domainName", domainName);
        context.put("alias", alias);
        context.put("editAction", editAction);
        context.put("domainParam", domainParam);
        context.put("messaageInfo", messaageInfo);
        String statusEditInfo = " <#if statusList??><#list statusList as status><#if " + alias + ".status == status.key><option value=\"${(status.key)!''}\" selected>${(status.value)!''}</option><#else><option value=\"${(status.key)!''}\">${(status.value)!''}</option></#if></#list></#if>";
        context.put("statusEditInfo", statusEditInfo);
        String targetName = getEditName(alias) + ".ftl";

        File createFile = new File(moduleDir, targetName);
        templateSourceGenator.genSource(context, "gen-templates-edit-vm.vm", createFile, fileWriter);
    }

    public void genViewTemplates(Class<?> domainClass, File moduleDir, String actionContext, FileWriter fileWriter) {
        renderCreate(domainClass, moduleDir, actionContext, fileWriter);
        renderList(domainClass, moduleDir, actionContext, fileWriter);
        // renderDetail(domainClass, moduleDir, actionContext, fileWriter);
        renderEdit(domainClass, moduleDir, actionContext, fileWriter);
    }

    public void genJavaAction(String className, Class<?> domainClass, String actionContext, Writer out,
                              String actionContextPackage) {
        Map<String, Object> context = MapUtil.newHashMap();
        String thisPackage = StringUtil.getLastBefore(className, ".");
        String thisClassName = StringUtil.getLastAfter(className, ".");
        GenMetaInfo genMetaInfo = new GenMetaInfo(domainClass, idName);
        String alias = DaoGenUtil.getDoAlias(domainClass);
        String domainUpper = StringUtil.uppercaseFirstLetter(alias);
        String aoPackage = genMetaInfo.getBaseBizPackage() + ".service";
        String aoClassName = thisClassName + "Service";
        String aoFullName = null;
        if (StringUtil.isNotBlank(actionContext)) {
            aoFullName = aoPackage + "." + actionContext + "." + aoClassName;
        } else {
            aoFullName = aoPackage + "." + aoClassName;
        }
        String aoBeanName = StringUtil.lowercaseFirstLetter(aoClassName);
        String formName = genMetaInfo.getDoAlias();
        String domainFullName = domainClass.getName();
        String domainName = ClassUtil.getShortClassName(domainClass);
        String domainParam = StringUtil.lowercaseFirstLetter(domainName);
        String baseActionPackage = genMetaInfo.getBaseBizPackage() + ".action";
        String contextPath = actionContextPackage.replace('.', '/');
        String baseActionTarget = getActionContext(contextPath) + alias;

        String idType = genMetaInfo.getIdType();
        String idTypeUpper = StringUtil.uppercaseFirstLetter(idType);
        String idGetter = "get" + StringUtil.uppercaseFirstLetter(idName);

        context.put("idGetter", idGetter);
        context.put("alias", alias);
        context.put("idTypeUpper", idTypeUpper);
        context.put("idType", idType);
        context.put("baseActionTarget", baseActionTarget);
        context.put("baseActionPackage", baseActionPackage);
        context.put("domainUpper", domainUpper);
        context.put("domainParam", domainParam);
        context.put("domainName", domainName);
        context.put("domainFullName", domainFullName);
        context.put("formName", formName);
        context.put("aoFullName", aoFullName);
        context.put("aoClassName", aoClassName);
        context.put("aoBeanName", aoBeanName);
        context.put("aoParamName", aoBeanName);
        context.put("thisPackage", thisPackage);
        context.put("thisClassName", thisClassName + "Action");

        templateSourceGenator.genSource(context, out, genJavaActionTemplateName);
    }

    public void genForm(Class<?> clazz, File formFile, FileWriter fileWriter) {
        GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);
        Map<String, Object> context = MapUtil.newHashMap();

        MagicClass magicClass = MagicClass.wrap(clazz);
        Map<String, Property> propertyMap = magicClass.getProperties();

        List<DomainField> fields = new ArrayList<DomainField>();
        for (Map.Entry<String, Property> entry : propertyMap.entrySet()) {
            Property property = entry.getValue();
            String name = entry.getKey();
            if (!property.isWritable() || !property.isReadable()) {
                continue;
            }
            if (name.equals(idName) || name.equals(ColumnNameContants.gmtModifiedName)
                    || name.equals(ColumnNameContants.gmtCreateName)) {
                continue;
            }

            fields.add(new DomainField(name, property.getPropertyClass().getTargetClass()));
        }

        context.put("name", genMetaInfo.getDoAlias());
        context.put("fields", fields);
        context.put("idName", idName);

        String cnName = cnName(clazz);
        if (StringUtil.isEmpty(cnName)) {
            templateSourceGenator.genSource(context, genFormTemplateName, formFile, fileWriter);
        } else {
            templateSourceGenator.genSource(context, genFormTemplateNameCn, formFile, fileWriter);
        }

    }

    private String cnName(Class<?> clazz) {
        Domain domain = clazz.getAnnotation(Domain.class);
        if (domain == null) {
            return null;
        }
        return domain.cnName();
    }

    public GenMetaInfo genDaoTests(Class<?> clazz, Writer out) {
        GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);
        Map<String, Object> context = MapUtil.newHashMap();

        String doPackage = clazz.getPackage().getName();
        String doClassName = clazz.getSimpleName();

        String daoClassName = genMetaInfo.getDaoSimpleClassName();

        String baseTestClassPackage = getBaseTestPackage(doPackage);
        String baseTestClassName = "BaseTest";
        String baseTestFullName = baseTestClassPackage + "." + baseTestClassName;
        String daoPropertyname = StringUtil.lowercaseFirstLetter(daoClassName);

        String baseDoName = StringUtil.lowercaseFirstLetter(doClassName);
        String doObject_1 = baseDoName;
        String doObject_2 = baseDoName + "_2";
        String doObject_3 = baseDoName + "_3";

        String propertySetter = propertySetter(clazz, doObject_1);

        context.put("propertySetter", propertySetter);
        context.put("doObject_1", doObject_1);
        context.put("doObject_2", doObject_2);
        context.put("doObject_3", doObject_3);
        context.put("daoPropertyname", daoPropertyname);
        context.put("baseTestClassName", baseTestClassName);
        context.put("baseTestFullName", baseTestFullName);
        context.put("thisPackage", genMetaInfo.getTestDaoPackage());
        context.put("thisClassName", genMetaInfo.getTestDaoSimpleClassName());
        context.put("doFullClassName", genMetaInfo.getDoFullClassName());

        context.put("daoClassName", genMetaInfo.getDaoSimpleClassName());

        context.put("idType", genMetaInfo.getIdType());
        context.put("idTypeWrapper", genMetaInfo.getIdTypeWrapper());
        context.put("doName", genMetaInfo.getDoSimpleClassName());

        genFromTemplate(context, out, genTestsTemplateName);
        DaoGenUtil.writeLine(out);

        return genMetaInfo;
    }

    public GenMetaInfo genIbatisDao(Class<?> clazz, Writer out, GenDaoInfo genDaoInfo) {
        String implSuffix = genDaoInfo.getDaoClassSuffix();
        GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);
        if (implSuffix == null) {
            implSuffix = "Ibatis";
            genMetaInfo.setImplSuffix(implSuffix);
        }
        Map<String, Object> context = MapUtil.newHashMap();
        String sqlMapClientName = genDaoInfo.getSqlMapClientName();
        context.put("thisPackage", genMetaInfo.getIbatisPackage());
        context.put("doFullClassName", genMetaInfo.getDoFullClassName());
        context.put("daoFullClassName", genMetaInfo.getDaoFullClassName());
        context.put("daoClassName", genMetaInfo.getDaoSimpleClassName());

        context.put("className", genMetaInfo.getIbatisSimpleClassName());
        context.put("sqlMapClientName", sqlMapClientName);
        context.put("namespace", genMetaInfo.getDaoSimpleClassName());
        context.put("idType", genMetaInfo.getIdType());
        context.put("idTypeWrapper", genMetaInfo.getIdTypeWrapper());
        context.put("domainName", genMetaInfo.getDoSimpleClassName());
        context.put("paramName", genMetaInfo.getDoAlias());

        genFromTemplate(context, out, genIbatisDaoTemplateName);
        DaoGenUtil.writeLine(out);

        return genMetaInfo;
    }

    protected void genFromTemplate(Map<String, Object> context, Writer out, String templateName) {
        templateSourceGenator.genSource(context, out, templateName);
    }

    protected void genFromTemplate(Map<String, Object> context, String templateName, File targetFile,
                                   FileWriter fileWriter) {
        templateSourceGenator.genSource(context, templateName, targetFile, fileWriter);
    }

    public void genCreateTableSql(Class<?> clazz, GenDaoInfo genDaoInfo, Writer out, boolean defaultNotNull) {
        Map<String, Object> context = MapUtil.newHashMap();
        String alias = DaoGenUtil.getDoAlias(clazz);
        String tableName = genDaoInfo.getTableName();
        if (StringUtil.isBlank(tableName)) {
            tableName = mappingPolicy.getTablePrefix() + StringUtil.camelToUnderLineString(StringUtil.getLastBefore(alias, DomainType.DTO));
        }
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        filters.add(idName);
        List<IbatisResult> columnsResult = getResult(clazz, filters, OptionEnum.READ_AND_WRITE);

        context.put("tableName", tableName);
        context.put("idNameColumn", StringUtil.camelToUnderLineString(idName));
        context.put("encoding", encoding);

        MagicClass mc = MagicClass.wrap(clazz);
        Property idProperty = mc.getProperty(idName);

        context.put("idType", type2sql.get(idProperty.getPropertyClass().getTargetClass()));
        context.put("mysqlEncoding", StringUtil.replace(encoding, "-", ""));

        IbatisResult deletedIbatisResult = new IbatisResult();
        deletedIbatisResult.setColumn("deleted");
        deletedIbatisResult.setProperty("deleted");
        deletedIbatisResult.setType(Byte.TYPE);
        deletedIbatisResult.setExtend("default 0");
        columnsResult.add(deletedIbatisResult);

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (IbatisResult result : columnsResult) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
                sb.append("\r\n");
            }
            sb.append("`");
            sb.append(result.getColumn());
            sb.append("`");
            sb.append(" ");
            sb.append(getSqlType(result.getType()));
            sb.append(" ");

            if (!SqlTypes.isDefaultNullable(result.getType())) {
                if (defaultNotNull) {
                    sb.append(" not null? ");
                }
            }

            String extend = result.getExtend();
            if (!StringUtil.isEmpty(extend)) {
                sb.append(" ");
                sb.append(extend);
                sb.append(" ");
            }
        }
        context.put("columns", sb.toString());

        genFromTemplate(context, out, createTableTemplateName);
        try {
            out.write(SystemUtil.LINE_SEPARATOR);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getSqlType(Class<?> type) {
        return SqlTypes.getByClass(type);
    }

    protected String getEntityType(Class<?> type) {
        return EntityTypes.getByClass(type);
    }
    public String genFullSQLColumns(Class<?> clazz) {
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        return genFullSQLColumns(clazz, "", filters, OptionEnum.READ_AND_WRITE);
    }

    public String genInsertNoId(Class<?> clazz, GenDaoInfo genDaoInfo) {
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        filters.add(idName);
        return genInsert(clazz, filters, OptionEnum.READ_AND_WRITE, genDaoInfo);
    }

    public String genInsertBatchNoId(Class<?> clazz, GenDaoInfo genDaoInfo) {
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        filters.add(idName);
        return genInsertBatch(clazz, filters, OptionEnum.READ_AND_WRITE, genDaoInfo);
    }


    public String genUpdateNoId(Class<?> clazz, GenDaoInfo genDaoInfo) {
        Set<String> filters = new HashSet<String>();
        filters.add("class");
        filters.add(idName);
        return genUpdate(clazz, filters, OptionEnum.READ_AND_WRITE, genDaoInfo);
    }

    public String genUpdate(Class<?> clazz, Set<String> filters, OptionEnum resultOptionEnum, GenDaoInfo genDaoInfo) {
        List<IbatisResult> results = getResult(clazz, filters, resultOptionEnum);
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        String tableName = genDaoInfo.getTableName();
        if (StringUtil.isNotBlank(tableName)) {
            sb.append(tableName);
        } else {
            sb.append(mappingPolicy.toTableName(clazz));
        }
        sb.append(" set ");
        boolean first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append("\r\n\t\t\t\t");
            sb.append(result.getColumn());
            sb.append("= ");
            if (propertyName.equals(ColumnNameContants.gmtModifiedName)) {
                sb.append("now()");
            } else {
                sb.append(genPropertyName(propertyName));
            }
        }
        sb.append("\r\n");
        sb.append("\t\t\twhere ");
        sb.append(StringUtil.camelToUnderLineString(idName));
        sb.append("=");
        sb.append(genPropertyName(StringUtil.fixedCharToCamel(idName, "_-")));
        return sb.toString();
    }

    public String genInsert(Class<?> clazz, Set<String> filters, OptionEnum resultOptionEnum, GenDaoInfo genDaoInfo) {
        List<IbatisResult> results = getResult(clazz, filters, resultOptionEnum);
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        String tableName = genDaoInfo.getTableName();
        if (StringUtil.isNotBlank(tableName)) {
            sb.append(tableName);
        } else {
            sb.append(mappingPolicy.toTableName(clazz));
        }
        sb.append("(");
        boolean first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(result.getColumn());
        }
        sb.append(") \r\n\t\t\t\tvalues (");
        first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (propertyName.equals(ColumnNameContants.gmtModifiedName)
                    || propertyName.equals(ColumnNameContants.gmtCreateName)) {
                sb.append("now()");
            } else {
                sb.append(genPropertyName(propertyName));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String genInsertBatch(Class<?> clazz, Set<String> filters, OptionEnum resultOptionEnum, GenDaoInfo genDaoInfo) {
        List<IbatisResult> results = getResult(clazz, filters, resultOptionEnum);
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        String tableName = genDaoInfo.getTableName();
        if (StringUtil.isNotBlank(tableName)) {
            sb.append(tableName);
        } else {
            sb.append(mappingPolicy.toTableName(clazz));
        }
        sb.append("(");
        boolean first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(result.getColumn());
        }
        sb.append(") \r\n\t\t\t\tvalues ");
        sb.append(" \n\t\t\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">");
        sb.append("\n\t\t\t\t (");
        first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (propertyName.equals(ColumnNameContants.gmtModifiedName)
                    || propertyName.equals(ColumnNameContants.gmtCreateName)) {
                sb.append("now()");
            } else {
                sb.append(genPropertyName("item." + propertyName));
            }
        }
        sb.append(")");
        sb.append("\n\t\t\t\t </foreach>");
        return sb.toString();
    }

    protected abstract String genPropertyName(String propertyName);

    public String genFullSQLColumns(Class<?> clazz, String prefix, Set<String> filters, OptionEnum resultOptionEnum) {
        List<IbatisResult> results = getResult(clazz, filters, resultOptionEnum);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (IbatisResult result : results) {
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(prefix);
            sb.append(result.getColumn());
        }
        return sb.toString();
    }

    private void checkPropertyName(String name) {
        String normalizeName = StringUtil.camelToUnderLineString(name).toUpperCase();
        if (Keywords.MYSQL_KEYWORDS.contains(normalizeName)) {
            throw new LaughingException("property '" + name + "' is a keyword of mysql.");
        }
    }



    private String getSqlMapType(IbatisResult result) {
        String sqlmap = getSqlType(result.getType());
        if (StringUtils.isNotBlank(sqlmap)) {
            sqlmap = sqlmap.replace("(", "");
            sqlmap = sqlmap.replace("?", "");
            sqlmap = sqlmap.replace(")", "");
            sqlmap = sqlmap.replaceAll("datetime", "TIMESTAMP");
        }
        return sqlmap.toUpperCase();

    }
    private List<IbatisResult> getResult(Class<?> c, Set<String> filters, OptionEnum resultOptionEnum) {
        MagicClass clazz = MagicClass.wrap(c);
        Map<String, Property> properties = clazz.getProperties();
        List<IbatisResult> ret = new ArrayList<IbatisResult>();
        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            checkPropertyName(entry.getKey());
            if (filters != null && filters.contains(entry.getKey())) {
                continue;
            }
            Property property = entry.getValue();
            if (needRead(resultOptionEnum) && !property.isReadable()) {
                continue;
            }
            if (needWrite(resultOptionEnum) && !property.isWritable()) {
                continue;
            }
            IbatisResult result = mappingPolicy.toResult(property);
            result.setSqlType(getSqlMapType(result));
            result.setEntityType(getEntityType(result.getType()));
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            ret.add(result);
        }
        return ret;
    }
    private List<IbatisResult> getViewResult(Class<?> c, Set<String> filters, OptionEnum resultOptionEnum) {
        MagicClass clazz = MagicClass.wrap(c);
        Map<String, Property> properties = clazz.getProperties();
        List<IbatisResult> ret = new ArrayList<IbatisResult>();
        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            checkPropertyName(entry.getKey());
            if (filters != null && filters.contains(entry.getKey())) {
                continue;
            }
            Property property = entry.getValue();
            if (needRead(resultOptionEnum) && !property.isReadable()) {
                continue;
            }
            if (needWrite(resultOptionEnum) && !property.isWritable()) {
                continue;
            }
            IbatisResult result = mappingPolicy.toViewResult(c, property);
            result.setSqlType(getSqlMapType(result));
            result.setEntityType(getEntityType(result.getType()));
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            String viewAliasName = result.getViewAliasName();
            if (StringUtil.isBlank(viewAliasName)) {
                continue;
            }
            ret.add(result);
        }
        return ret;
    }

    private List<IbatisResult> getDbQUeryResult(Class<?> c, Set<String> filters, OptionEnum resultOptionEnum) {
        MagicClass clazz = MagicClass.wrap(c);
        Map<String, Property> properties = clazz.getProperties();
        List<IbatisResult> ret = new ArrayList<IbatisResult>();
        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            checkPropertyName(entry.getKey());
            if (filters != null && filters.contains(entry.getKey())) {
                continue;
            }
            Property property = entry.getValue();
            if (needRead(resultOptionEnum) && !property.isReadable()) {
                continue;
            }
            if (needWrite(resultOptionEnum) && !property.isWritable()) {
                continue;
            }
            IbatisResult result = mappingPolicy.toDBQueryResult(c, property);
            result.setSqlType(getSqlMapType(result));
            result.setEntityType(getEntityType(result.getType()));
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            String viewAliasName = result.getDbQueryAliasName();
            if (StringUtil.isBlank(viewAliasName)) {
                continue;
            }
            ret.add(result);
        }
        return ret;
    }

    private List<IbatisResult> getEditResult(Class<?> c, Set<String> filters, OptionEnum resultOptionEnum) {
        MagicClass clazz = MagicClass.wrap(c);
        Map<String, Property> properties = clazz.getProperties();
        List<IbatisResult> ret = new ArrayList<IbatisResult>();
        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            checkPropertyName(entry.getKey());
            if (filters != null && filters.contains(entry.getKey())) {
                continue;
            }
            Property property = entry.getValue();
            if (needRead(resultOptionEnum) && !property.isReadable()) {
                continue;
            }
            if (needWrite(resultOptionEnum) && !property.isWritable()) {
                continue;
            }
            IbatisResult result = mappingPolicy.toViewResult(c, property);
            result.setSqlType(getSqlMapType(result));
            result.setEntityType(getEntityType(result.getType()));
            String propertyName = result.getProperty();
            String value = ColumnNameContext.COLUMNNAMEINFOMAP.get(propertyName);
            if (StringUtil.isNotBlank(value)) {
                continue;
            }
            String editAliasName = result.getEditAliasName();
            if (StringUtil.isBlank(editAliasName)) {
                continue;
            }
            ret.add(result);
        }
        return ret;
    }

    public String genMapping(Class<?> clazz, Set<String> filters, OptionEnum resultOptionEnum) {
        List<IbatisResult> results = getResult(clazz, filters, resultOptionEnum);
        StringBuilder sb = new StringBuilder();
        for (IbatisResult result : results) {
            sb.append(resultRender.render(result));
            sb.append("\r\n");
        }
        return sb.toString();
    }

    private String normalizeContext(String actionContext) {
        actionContext = actionContext.replace('\\', '/');
        while (actionContext.startsWith("/")) {
            actionContext = actionContext.substring(1);
        }
        while (actionContext.endsWith("/")) {
            actionContext = actionContext.substring(0, actionContext.length() - 1);
        }
        return actionContext;
    }

    private static boolean needRead(OptionEnum resultOptionEnum) {
        return (resultOptionEnum.getValue() & OptionEnum.READ.getValue()) == OptionEnum.READ.getValue();
    }

    private static boolean needWrite(OptionEnum resultOptionEnum) {
        return (resultOptionEnum.getValue() & OptionEnum.WRITE.getValue()) == OptionEnum.WRITE.getValue();
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public boolean isWithName() {
        return withName;
    }

    public void setWithName(boolean withName) {
        this.withName = withName;
    }
}
