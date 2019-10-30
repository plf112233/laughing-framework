package com.laughing.help.tools.ibatis;

import com.laughing.help.tools.gen.common.*;
import com.laughing.help.tools.gen.dao.DaoGenUtil;
import com.laughing.lang.utils.StringUtil;
import com.laughing.lang.utils.SystemUtil;
import com.laughing.lang.utils.page.DomainType;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

/**
 * @author lifei.pan
 * @ClassName: IbatisAutoGenDAO
 * @Description:生成DAO成的魔法工具
 * @email plfnet@163.com
 * @date 2016年10月21日 上午9:19:59
 */
public class MybatisAutoGenDAO extends BaseAutoGen {

    private String sqlmapRootPath = "src/main/resources/sqlmap";

    private String daoSuffix = "Ibatis";

    private String prefix;

    public MybatisAutoGenDAO(String prefix) {
        this.prefix = prefix;
    }

    private String getBeanId(String beanClassName) {
        String className = StringUtil.getLastAfter(beanClassName, ".");
        if (className.endsWith(daoSuffix)) {
            return StringUtil.lowercaseFirstLetter(className.substring(0, className.length() - daoSuffix.length()));
        }
        return StringUtil.lowercaseFirstLetter(className);
    }

    private String getBeanClassName(Class<?> clazz) {
        String alias = DaoGenUtil.getDoAlias(clazz);
        String namespace = StringUtil.uppercaseFirstLetter(alias) + "DAO";
        String doPackage = clazz.getPackage().getName();
        String baseDalPackage = StringUtil.getLastBefore(doPackage, ".domain");

        String daoPackage = baseDalPackage + ".dao";
        String daoClassName = namespace;
        String ibatisDaoPackage = daoPackage + ".ibatis";
        String ibatisDaoClassName = daoClassName + daoSuffix;
        String ibatisDaoFullClassName = ibatisDaoPackage + "." + ibatisDaoClassName;
        return ibatisDaoFullClassName;
    }

    private void genTests(MybatisSourceGenerator sourceGenerator, Class<?> clazz, File baseFile, FileWriter fileWriter)
            throws IOException {
        log("generating dao tests");
        StringWriter stringWriter = new StringWriter();
        GenMetaInfo genMetaInfo = sourceGenerator.genDaoTests(clazz, stringWriter);
        String name = genMetaInfo.getTestDaoFullClassName();
        String content = stringWriter.toString();
        File javaTestSrcPath = new File(baseFile, JAVA_TEST_SRC);
        genJavaSrc(name, content, javaTestSrcPath, fileWriter);
        log("generate dao tests finish");
    }

    private void printSqlScripts(MybatisSourceGenerator sourceGenerator, Class<?> clazz, GenDaoInfo genDaoInfo) {
        System.out.println("==============create table sql script================");
        System.out.println();
        System.out.println();
        sourceGenerator.genCreateTableSql(clazz, genDaoInfo, new OutputStreamWriter(System.out), true);
        System.out.println();
        System.out.println();
        System.out.flush();
        System.out.println("=====================================================");
    }

    private void addSqlmapToRootFile(File baseFile, String sqlmapFileName) throws IOException {
        File rootFile = new File(baseFile, sqlmapRootPath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        String nodeStr = "<sqlMap resource=\"ibatis/" + sqlmapFileName + "\" />";
        if (checkExist(rootFile, nodeStr)) {
            return;
        }
        nodeStr = "\t" + nodeStr + SystemUtil.LINE_SEPARATOR;
        addNodeToPath(rootFile, "(</sqlMapConfig>)", nodeStr);
    }

    private void genSqlmap(MybatisSourceGenerator sourceGenerator, Class<?> clazz, File baseFile, FileWriter fileWriter, GenDaoInfo genDaoInfo)
            throws IOException {
        log("generating sqlmap");
        File sqlmapDir = new File(baseFile, sqlmapRootPath);
        StringWriter sqlmapStringWriter = new StringWriter();
        GenMetaInfo genMetaInfo = sourceGenerator.genSqlMap(clazz, sqlmapStringWriter, genDaoInfo);
        String sqlmapFileName =  StringUtil.getLastBefore(genMetaInfo.getDoAlias(), DomainType.DTO) + "-sqlmap.xml";
        if (!sqlmapDir.exists()) {
            sqlmapDir.mkdirs();
        }
        File sqlmapFile = new File(sqlmapDir, sqlmapFileName);
        fileWriter.writeToFile(sqlmapStringWriter.toString(), sqlmapFile);
        log("generate sqlmap finish");
    }

    private void genDAO(MybatisSourceGenerator sourceGenerator, Class<?> clazz, File baseFile, FileWriter fileWriter,
                        File javaMainSrcPath, GenDaoInfo genDaoInfo) {
        log("generating dao interface");
        StringWriter stringWriter = new StringWriter();
        GenMetaInfo genMetaInfo = sourceGenerator.genDAO(clazz, stringWriter, genDaoInfo);
        String name = genMetaInfo.getDaoFullClassName();
        String content = stringWriter.toString();
        genJavaSrc(name, content, javaMainSrcPath, fileWriter);
        log("generate dao interface finish");
    }
    private void genImpl(Class<?> clazz, String idName, FileWriter fileWriter, GenDaoInfo genDaoInfo) {
        File baseFile = getProjectBasePath(clazz);
        MybatisSourceGenerator sourceGenerator = new MybatisSourceGenerator();
        sourceGenerator.setIdName(idName);
        sourceGenerator.setTablePrefix(prefix);

        File javaMainSrcPath = new File(baseFile, JAVA_MAIN_SRC);

        try {
                genSqlmap(sourceGenerator, clazz, baseFile, fileWriter, genDaoInfo);
                genDAO(sourceGenerator, clazz, baseFile, fileWriter, javaMainSrcPath, genDaoInfo);
                genTests(sourceGenerator, clazz, baseFile, fileWriter);
                printSqlScripts(sourceGenerator, clazz, genDaoInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void gen(Class<?> clazz, GenDaoInfo genDaoInfo) {
        gen(clazz, "id", genDaoInfo);
    }

    public void forceGen(Class<?> clazz, GenDaoInfo genDaoInfo) {
        forceGen(clazz, "id", genDaoInfo);
    }

    public void gen(Class<?> clazz, String idName, GenDaoInfo genDaoInfo) {
        genImpl(clazz, idName, genWriter(), genDaoInfo);
    }

    public void forceGen(Class<?> clazz, String idName, GenDaoInfo genDaoInfo) {
        genImpl(clazz, idName, forceGenWriter(), genDaoInfo);
    }

    public void setDaoSuffix(String daoSuffix) {
        this.daoSuffix = daoSuffix;
    }

    public void setSqlmapRootPath(String sqlmapRootPath) {
        this.sqlmapRootPath = sqlmapRootPath;
    }
}
