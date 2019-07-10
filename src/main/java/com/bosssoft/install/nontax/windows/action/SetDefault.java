package com.bosssoft.install.nontax.windows.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.CompDef;
import com.bosssoft.platform.installer.core.option.ComponentsDefHelper;
import com.bosssoft.platform.installer.core.option.ModuleDef;
import com.bosssoft.platform.installer.core.option.ResourceDef;
import com.bosssoft.platform.installer.core.option.ResourceDefHelper;
import com.bosssoft.platform.installer.core.util.ExpressionParser;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.util.ServerUtil;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseManager;

public class SetDefault implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
		try{
			//读取default_install.properties中的默认值
			loaddefault(context);
			
			//加载resource.xml(运行环境配置)
			loadresource(context);
			
			//加载配置的安装组件
			loadComponents(context);
			
			//环境检查
			environmentCheck(context);
		}catch(Exception e){
			e.printStackTrace();
			throw new InstallException(e);
		}
		
		
	}

	private void environmentCheck(IContext context) {
		Map<String,ResourceDef>	resourceMap=ResourceDefHelper.getResourceMap();
		//应用服务器
		String appName=context.getStringValue("APP_SERVER_NAME");
		Integer port=Integer.valueOf(context.getStringValue("APP_SERVER_PORT"));
		ResourceDef newdef=new ResourceDef();
		newdef.setName(appName);
		newdef.addPort(port);
		resourceMap.put(appName, newdef);
		
	
		for (Map.Entry<String, ResourceDef> entry: resourceMap.entrySet()) {
			ResourceDef def=entry.getValue();
			if(def.getPorts().size()!=0){
				//检测是否有进程占用端口
			    Set<Integer> pids=ServerUtil.searchProcess4Win(def.getPorts());
			    if(pids.size()!=0){
			    	int dialog_result=MainFrameController.showConfirmDialog(entry.getKey()+I18nUtil.getString("CONFIG.RUNEVN.PORTCONFLICT"), I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
			    	if(dialog_result==0){
			    		ServerUtil.killWithPid4Win(pids);
			    		context.setValue("IS_KILL_PID", true);
			    	}else{
			    		context.setValue("IS_KILL_PID", false);
			    		break;
			    	}
			    }else{
			    	context.setValue("IS_KILL_PID", true);
			    }
			}
		}
		
		resourceMap.remove(appName);
	}

	private void loadComponents(IContext context) {
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

	private void loadresource(IContext context) throws Exception {
		
		StringBuffer installStr=new StringBuffer();
		Map<String,ResourceDef> resourceMap=new HashMap<String, ResourceDef>();
		resourceMap=ResourceDefHelper.getResourceMap();
		for (Entry<String, ResourceDef> entry : resourceMap.entrySet()) {
			ResourceDef def=entry.getValue();
			def.setIsInstall(true);
			Map<String,String> params=def.getParams();
			Iterator paramit=params.entrySet().iterator();
			while(paramit.hasNext()){
				Map.Entry<String,String> param=(Entry) paramit.next();
				String value=param.getValue().replace("[home]", def.getHome());
				param.setValue(value);
				context.setValue(param.getKey(), value);
			}
			context.setValue(def.getName()+"_home",def.getHome());
			installStr.append(def.getName()).append(",");
		}
		
		context.setValue("RESOURCE_MAP", resourceMap);
		context.setValue("INSATLL_SERVERS", installStr);
	}

	private void loaddefault(IContext context) {
		String dafaultConfig=InstallerFileManager.getConfigDir()+File.separator+"default_install.properties";
		Properties propers=new Properties();
		FileInputStream inStream=null;
		try {
			inStream=new FileInputStream(dafaultConfig);
			propers.load(inStream);
			Enumeration keys=propers.keys();
			String key=null;
			while(keys.hasMoreElements()){
				key=keys.nextElement().toString();
				context.setValue(key, ExpressionParser.parseString(propers.getProperty(key)));
			}
			//设置bosssofthome
			String bosssofHome = System.getProperty("BOSSSOFT_HOME");
			if (StringUtils.isNotBlank(bosssofHome)) {
				context.setValue("BOSSSOFT_HOME", bosssofHome);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new InstallException(e);
		}
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}

}
