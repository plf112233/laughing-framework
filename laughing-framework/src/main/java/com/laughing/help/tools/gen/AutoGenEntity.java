package com.laughing.help.tools.gen;

import com.laughing.help.tools.gen.common.BaseAutoGen;
import com.laughing.help.tools.gen.common.FileWriter;
import com.laughing.help.tools.gen.common.SourceGenerator;
import com.laughing.lang.utils.StringUtil;

import java.io.File;
import java.io.StringWriter;

/**
 * @author lifei.pan
 * @ClassName: AutoGenEntity
 * @Description: 字段创建实体类
 * @email plfnet@163.com
 * @date 2016年10月20日 下午5:32:52
 */
public class AutoGenEntity extends BaseAutoGen {

    private String prefix;
    private String idName = "id";

    public AutoGenEntity(String prefix) {
        this.prefix = prefix;
    }

    private void genImpl(Class<?> clazz, String idName, String actionContext, boolean force) {
        try {
            File baseFile = getProjectBasePath(clazz);

            actionContext = StringUtil.trimToEmpty(actionContext);
            SourceGenerator sourceGenerator = new SourceGenerator();
            sourceGenerator.setIdName(idName);
            sourceGenerator.setTablePrefix(prefix);

            File javaMainSrcPath = new File(baseFile, JAVA_MAIN_SRC);
            genJavaVO(sourceGenerator, clazz, actionContext, getFileWriter(force), javaMainSrcPath);
            genJavaQuery(sourceGenerator, clazz, actionContext, getFileWriter(force), javaMainSrcPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void gen(Class<?> clazz, String idName, String actionContext) {
        genImpl(clazz, idName, actionContext, false);
    }

    public void gen(Class<?> clazz, String actionContext) {
        gen(clazz, "id", actionContext);
    }

    public void forceGen(Class<?> clazz, String idName, String actionContext) {
        genImpl(clazz, idName, actionContext, true);
    }

    public void forceGen(Class<?> clazz, String actionContext) {
        forceGen(clazz, "id", actionContext);
    }

    private void genJavaVO(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext,
                           FileWriter fileWriter, File javaMainSrcPath) {
        StringWriter stringWriter = new StringWriter();
        String aoFullname = sourceGenerator.genJavaVO(clazz, actionContext, stringWriter);
        String content = stringWriter.toString();
        this.genJavaSrc(aoFullname, content, javaMainSrcPath, fileWriter);
    }

    private void genJavaQuery(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext,
                              FileWriter fileWriter, File javaMainSrcPath) {
        StringWriter stringWriter = new StringWriter();
        String aoFullname = sourceGenerator.genJavaQuery(clazz, actionContext, stringWriter);
        String content = stringWriter.toString();
        this.genJavaSrc(aoFullname, content, javaMainSrcPath, fileWriter);
    }
}
