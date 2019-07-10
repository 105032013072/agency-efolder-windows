package com.bosssoft.install.nontax.windows.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.IntroductionPanel;
import com.bosssoft.platform.license.api.Constant;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseContext;
import com.bosssoft.platform.license.generator.SignGenerator;
import com.bosssoft.platform.license.impl.LicenseParser;
import com.bosssoft.platform.license.impl.LicenseUtil;

public class IntroductionLicensepanel extends IntroductionPanel{
    
	public void beforeNext(){
		
		List<String> licenseApps=new ArrayList<String>();
		String licenseFilePath=InstallerFileManager.getInstallerHome()+File.separator+"installer"+File.separator+"bosssoft-license.xml";
		if(!new File(licenseFilePath).exists()) showMessage(I18nUtil.getString("LICENSE.NOT.EXIST"));
	
		//licnse 文件解析
		Constant.init();
		LicenseContext context = LicenseContext.getInstance();
		context.setLicenseFilePath(licenseFilePath);
		Map<String, License> results = LicenseParser.getInstance().getLicensees();
		
		//license 签名验证
	  for (Map.Entry<String, License> entry : results.entrySet()) {
		String appName=entry.getKey();
		License license=entry.getValue();
		
		
		if(!verifySign(license)) showMessage(appName+I18nUtil.getString("LICENSE.ILLEGAL"));
		licenseApps.add(appName);
	  }
		
	  getContext().setValue("licenseApps", licenseApps);
	}
	
	private boolean verifySign(License license) {
		
		String src =license.toString();
		String dest =license.getSign();
		return LicenseUtil.verify(src, dest);
	}

	private void showMessage(String msg){
		JPanel jpanel = new JPanel();
		JTextArea textArea = new JTextArea(4, 40);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(msg);
		textArea.setEditable(false);
		jpanel.add(new JScrollPane(textArea));
	    Object[] options ={ "确定"}; 
	    int result=JOptionPane.showOptionDialog(null, jpanel, "标题",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	    if(result==0||result==-1) System.exit(-1);
	}
}