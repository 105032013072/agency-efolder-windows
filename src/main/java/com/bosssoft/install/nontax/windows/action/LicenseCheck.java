package com.bosssoft.install.nontax.windows.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.license.api.Constant;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseContext;
import com.bosssoft.platform.license.impl.LicenseParser;
import com.bosssoft.platform.license.impl.LicenseUtil;

public class LicenseCheck implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
		
		List<String> licenseApps=new ArrayList<String>();
		
		String licenseFilePath=InstallerFileManager.getInstallerHome()+File.separator+"installer"+File.separator+"bosssoft-license.xml";
		if(!new File(licenseFilePath).exists()) showMessage(I18nUtil.getString("LICENSE.NOT.EXIST"));
		
		//licnse 文件解析
		Constant.init();
		LicenseContext lcontext = LicenseContext.getInstance();
		lcontext.setLicenseFilePath(licenseFilePath);
		Map<String, License> results = LicenseParser.getInstance().getLicensees();
				
		//license 签名验证
		for (Map.Entry<String, License> entry : results.entrySet()) {
			String appName=entry.getKey();
			License license=entry.getValue();
				
			if(!verifySign(license)) showMessage(appName+I18nUtil.getString("LICENSE.ILLEGAL"));
			licenseApps.add(appName);
		}
				
		context.setValue("licenseApps", licenseApps);
	}

	private boolean verifySign(License license) {
		String src =license.toString();
		String dest =license.getSign();
		return LicenseUtil.verify(src, dest);
	}

	private void showMessage(String string) {
		System.out.println(string);
		System.exit(-1);
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}

}
