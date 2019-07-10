package com.bosssoft.install.nontax.windows.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.CompDef;
import com.bosssoft.platform.installer.core.option.ComponentsDefHelper;
import com.bosssoft.platform.installer.core.option.ModuleDef;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseManager;

public class InitComponents implements IAction{
	transient Logger logger = Logger.getLogger(getClass());

	public void execute(IContext context, Map params) throws InstallException {
		List<ModuleDef> optionsList = ComponentsDefHelper.getOptionCompsDef();
		List<CompDef> installList=new ArrayList<CompDef>();
		if ((optionsList == null) || (optionsList.size() == 0)) {
			return;
		}
		
		StringBuffer installNamekeys=new StringBuffer();
		for (ModuleDef moduleDef : optionsList) {
			 License license=LicenseManager.getLicense(moduleDef.getNameKey());
	           if(license!=null) {
	        	   installList.add(moduleDef);
	        	   installNamekeys.append(moduleDef.getNameKey()+",");
	           }
		}
		context.setValue("MODULE_OPTIONS", installList);
		context.setValue("MODULE_OPTIONS_NAMES", installNamekeys.toString());
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		// TODO Auto-generated method stub
		
	}
}
