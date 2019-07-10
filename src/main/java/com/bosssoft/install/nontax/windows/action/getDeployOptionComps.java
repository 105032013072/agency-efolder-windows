package com.bosssoft.install.nontax.windows.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.ComponentsDefHelper;
import com.bosssoft.platform.installer.core.option.ModuleDef;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseManager;

public class getDeployOptionComps implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
		List<String> licenseApps=(List<String>) context.getValue("licenseApps");
		List<ModuleDef>	optionsCompList = ComponentsDefHelper.getOptionCompsDef();
		List<ModuleDef> installCompList=new ArrayList<ModuleDef>();
		StringBuffer stringBuffer=new StringBuffer();

		for (ModuleDef moduleDef : optionsCompList) {
			if(licenseApps.contains(moduleDef.getNameKey())){
				installCompList.add(moduleDef);
				stringBuffer.append(moduleDef.getNameKey()+",");
			} 
			
		}
		context.setValue("MODULE_OPTIONS", installCompList);
		context.setValue("MODULE_OPTIONS_NAMES", stringBuffer.toString());
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}

}
