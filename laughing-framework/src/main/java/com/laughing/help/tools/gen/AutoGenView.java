package com.laughing.help.tools.gen;

import com.laughing.help.tools.gen.common.BaseAutoGen;
import com.laughing.help.tools.gen.common.FileWriter;
import com.laughing.help.tools.gen.common.GenMetaInfo;
import com.laughing.help.tools.gen.common.SourceGenerator;
import com.laughing.help.tools.gen.dao.DaoGenUtil;
import com.laughing.lang.utils.StringUtil;
import com.laughing.lang.utils.SystemUtil;
import com.laughing.lang.utils.page.DomainType;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @ClassName: AutoGenView
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2016年10月20日 下午5:32:52
 *
 */
public class AutoGenView extends BaseAutoGen {

	private String prefix;

	private String formPath = "src/main/resources/forms";

	private String aoPath = "src/main/resources/beans/biz-ao.xml";

	private String viewTemplatePath = "src/main/webapp/WEB-INF";

	private String formRootPath = "src/main/resources/form.xml";
	private String idName = "id";

	public AutoGenView(String prefix) {
		this.prefix = prefix;
	}

	public void genForm(Class<?> clazz, String idName, boolean force) {
		try {
			File baseFile = getProjectBasePath(clazz);

			SourceGenerator sourceGenerator = new SourceGenerator();
			sourceGenerator.setIdName(idName);
			sourceGenerator.setTablePrefix(prefix);
			genForm(sourceGenerator, clazz, baseFile, getFileWriter(force));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void genImpl(Class<?> clazz, String idName, String actionContext, boolean force) {
		try {
			File baseFile = getProjectBasePath(clazz);

			actionContext = StringUtil.trimToEmpty(actionContext);

			SourceGenerator sourceGenerator = new SourceGenerator();
			sourceGenerator.setIdName(idName);
			sourceGenerator.setTablePrefix(prefix);

			File javaMainSrcPath = new File(baseFile, JAVA_MAIN_SRC);
			File templatesSrcPath = new File(baseFile, viewTemplatePath);

			genJavaAO(sourceGenerator, clazz, actionContext, getFileWriter(force), javaMainSrcPath);
			genJavaAOImpl(sourceGenerator, clazz, actionContext, baseFile, getFileWriter(force), javaMainSrcPath);
			genJavaAction(sourceGenerator, clazz, actionContext, getFileWriter(force), javaMainSrcPath);
			genViewTemplates(sourceGenerator, clazz, actionContext, getFileWriter(force), templatesSrcPath);

			String alias = DaoGenUtil.getDoAlias(clazz);

			log("http://localhost:端口号/项目名称" + sourceGenerator.getActionContext(actionContext) + alias + "/list.do");
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

	private void genJavaAO(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext,
			FileWriter fileWriter, File javaMainSrcPath) {
		StringWriter stringWriter = new StringWriter();
		String aoFullname = sourceGenerator.genJavaAO(clazz, actionContext, stringWriter);
		String content = stringWriter.toString();
		this.genJavaSrc(aoFullname, content, javaMainSrcPath, fileWriter);
	}

	private void genJavaAOImpl(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext, File baseFile,
			FileWriter fileWriter, File javaMainSrcPath) throws IOException {
		StringWriter stringWriter = new StringWriter();
		String aoImplFullname = sourceGenerator.genJavaAOImpl(clazz, actionContext, stringWriter);
		String content = stringWriter.toString();
		this.genJavaSrc(aoImplFullname, content, javaMainSrcPath, fileWriter);

		File daoSpring = new File(baseFile, aoPath);
		if (daoSpring.exists()) {
			String alias = DaoGenUtil.getDoAlias(clazz);
			addSpringBean(daoSpring, aoImplFullname, alias + "Service");
		}

	}

	private void genViewTemplates(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext,
			FileWriter fileWriter, File templatesSrcPath) {
		if (!templatesSrcPath.exists()) {
			templatesSrcPath.mkdirs();
		}
		File pageDir = new File(templatesSrcPath, "view");
		if (!pageDir.exists()) {
			pageDir.mkdirs();
		}
		File actionDir = new File(pageDir, actionContext);
		if (!actionDir.exists()) {
			actionDir.mkdirs();
		}
		String alias = DaoGenUtil.getDoAlias(clazz);
		File moduleDir = new File(actionDir, alias);
		if (!moduleDir.exists()) {
			moduleDir.mkdirs();
		}

		actionContext = normalizeContext(actionContext);
		sourceGenerator.genViewTemplates(clazz, moduleDir, actionContext, fileWriter);
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

	private void genJavaAction(SourceGenerator sourceGenerator, Class<?> clazz, String actionContext,
			FileWriter fileWriter, File javaMainSrcPath) {
		GenMetaInfo genMetaInfo = new GenMetaInfo(clazz, idName);
		String baseActionPackage = genMetaInfo.getBaseBizPackage()+ ".controller";
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
		String actionClassName = StringUtil.uppercaseFirstLetter(DaoGenUtil.getDoAlias(clazz));
		String className = targetActionPackage + "." + actionClassName;
		actionClassName = StringUtil.uppercaseFirstLetter(StringUtil.getLastBefore(actionClassName, DomainType.DTO));
		String fullActionName = targetActionPackage + "." + actionClassName + "Controller";
		StringWriter stringWriter = new StringWriter();
		sourceGenerator.genJavaAction(className, clazz, actionContext, stringWriter, actionContextPackage);
		String content = stringWriter.toString();
		this.genJavaSrc(fullActionName, content, javaMainSrcPath, fileWriter);
	}

	private void genForm(SourceGenerator sourceGenerator, Class<?> clazz, File baseFile, FileWriter fileWriter)
			throws IOException {
		File formDir = new File(baseFile, formPath);
		String alias = DaoGenUtil.getDoAlias(clazz);
		String formFileName = alias + "_autogen.xml";

		if (!formDir.exists()) {
			formDir.mkdirs();
		}
		File formFile = new File(formDir, formFileName);
		sourceGenerator.genForm(clazz, formFile, fileWriter);
		addFormToRoot(baseFile, formFileName);
	}

	private void addFormToRoot(File baseFile, String formName) throws IOException {
		File rootFile = new File(baseFile, formRootPath);
		if (!rootFile.exists()) {
			return;
		}

		String nodeStr = "<resource file=\"forms/" + formName + "\" />";
		if (checkExist(rootFile, nodeStr)) {
			return;
		}
		nodeStr = "\t" + nodeStr + SystemUtil.LINE_SEPARATOR;
		addNodeToPath(rootFile, "(</forms>)", nodeStr);
	}

}
