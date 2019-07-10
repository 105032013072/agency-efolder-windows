package com.bosssoft.install.nontax.windows.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.util.ExpressionParser;
/**
 *设置应用服务器的相关路径
 * @author Windows
 *
 */
public class SetAppsvrPath implements IAction{
	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
		String appsvrType = context.getStringValue("APP_SERVER_TYPE");
		if(appsvrType.toLowerCase().indexOf("tomcat")!=-1){
			
			String serverdir=ExpressionParser.parseString("${AS_TOMCAT_HOME}/bin/");
			context.setValue("appSvr_path", serverdir+"startup.bat");
			logger.info(context.getValue("appSvr_path"));
			
			context.setValue("appSvr_deploy_path", ExpressionParser.parseString("${AS_TOMCAT_HOME}/webapps"));

		}else if(appsvrType.toLowerCase().indexOf("jboss")!=-1){
			
		}else if(appsvrType.toLowerCase().indexOf("weblogic")!=-1){
			String serverdir=ExpressionParser.parseString("${AS_WL_DOMAIN_HOME}/");
			context.setValue("appSvr_path", serverdir+"startWebLogic.cmd");
			
			context.setValue("appSvr_deploy_path", ExpressionParser.parseString("${AS_WL_DOMAIN_HOME}/autodeploy"));
		} 
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
	}

}
