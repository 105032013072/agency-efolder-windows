package com.bosssoft.install.nontax.windows.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.wizard.action.InitDBSqlScript;

public class EfolderInitDBSqlScript extends InitDBSqlScript{

	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
		try{
			super.execute(context, params);
		}catch(Exception e){
			logger.error(e);
		}
		
	}

}
