package com.laughing.help.tools.gen;

import com.laughing.help.tools.gen.common.GenDaoInfo;
import com.laughing.help.tools.ibatis.IbatisAutoGenDAO;
import com.laughing.help.tools.ibatis.MybatisAutoGenDAO;
import com.laughing.help.tools.thead.GenContext;

/**
 * @ClassName: AutoGen
 * @Description:
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2016年10月20日 下午5:24:41
 *
 */
public class AutoGen {

	private IbatisAutoGenDAO ibatisAutoGenDAO;

	private MybatisAutoGenDAO mybatisAutoGenDAO;

	private AutoGenEntity autoGenEntity;

	private AutoGenView autoGenView;

	public AutoGen(String prefix) {
		ibatisAutoGenDAO = new IbatisAutoGenDAO(prefix);
		mybatisAutoGenDAO = new MybatisAutoGenDAO(prefix);
		autoGenView = new AutoGenView(prefix);
		autoGenEntity = new AutoGenEntity(prefix);
	}

	public void gen(Class<?> clazz, String idName, String actionContext, GenDaoInfo genDaoInfo) {
		try {
			genDaoInfo.setActionContext(actionContext);
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.gen(clazz, idName, genDaoInfo);
			} else {
				ibatisAutoGenDAO.gen(clazz, idName, genDaoInfo);
			}
			autoGenEntity.gen(clazz, idName, actionContext);
			autoGenView.gen(clazz, idName, actionContext);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void forceGen(Class<?> clazz, String idName, String actionContext, GenDaoInfo genDaoInfo) {
		try {
			genDaoInfo.setActionContext(actionContext);
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.forceGen(clazz, idName, genDaoInfo);
			} else {
				ibatisAutoGenDAO.forceGen(clazz, idName, genDaoInfo);
			}
			autoGenEntity.forceGen(clazz, idName,actionContext);
			autoGenView.forceGen(clazz, idName, actionContext);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void gen(Class<?> clazz, String actionContext, GenDaoInfo genDaoInfo) {
		try {
			genDaoInfo.setActionContext(actionContext);
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.gen(clazz, genDaoInfo);
			} else {
				ibatisAutoGenDAO.gen(clazz, genDaoInfo);
			}
			autoGenView.gen(clazz, actionContext);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void forceGen(Class<?> clazz, String actionContext, GenDaoInfo genDaoInfo) {
		try {
			genDaoInfo.setActionContext(actionContext);
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.forceGen(clazz, genDaoInfo);
			} else {
				ibatisAutoGenDAO.forceGen(clazz, genDaoInfo);
			}
			autoGenEntity.forceGen(clazz, actionContext);
			autoGenView.forceGen(clazz, actionContext);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void genDAOAndForm(Class<?> clazz, String idName, GenDaoInfo genDaoInfo) {
		try {
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.gen(clazz, idName, genDaoInfo);
			} else {
				ibatisAutoGenDAO.gen(clazz, idName, genDaoInfo);
			}
			autoGenView.genForm(clazz, idName, false);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void forceGenDAOAndForm(Class<?> clazz, String idName, GenDaoInfo genDaoInfo) {
		try {
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.forceGen(clazz, idName, genDaoInfo);
			} else {
				ibatisAutoGenDAO.forceGen(clazz, idName, genDaoInfo);
			}
			autoGenView.genForm(clazz, idName, true);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void genDAOAndForm(Class<?> clazz, GenDaoInfo genDaoInfo) {
		try {
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.gen(clazz, genDaoInfo);
			} else {
				ibatisAutoGenDAO.gen(clazz, genDaoInfo);
			}
			autoGenView.genForm(clazz, "id", false);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

	public void forceGenDAOAndForm(Class<?> clazz, GenDaoInfo genDaoInfo) {
		try {
			GenContext.getReqLogContext().setGenDaoInfo(genDaoInfo);
			if (GenDaoInfo.DATA_FRAMEWORK_NAME_MYBATIS.equals(genDaoInfo.getFrameWorkName())) {
				mybatisAutoGenDAO.forceGen(clazz, genDaoInfo);
			} else {
				ibatisAutoGenDAO.forceGen(clazz, genDaoInfo);
			}
			autoGenView.genForm(clazz, "id", true);
		} finally {
			GenContext.removeReqLogContext();
		}
	}

}
