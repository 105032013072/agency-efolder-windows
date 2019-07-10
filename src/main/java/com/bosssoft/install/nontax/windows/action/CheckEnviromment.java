package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Map;
import java.util.Set;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.ResourceDef;
import com.bosssoft.platform.installer.core.option.ResourceDefHelper;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.wizard.util.ServerUtil;

public class CheckEnviromment implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
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
			    	try {
						showMessage(entry.getKey(),pids);
					} catch (Exception e) {
						throw new InstallException(e);
					}
			    }
			}
		}
		
		resourceMap.remove(appName);
	}

	private void showMessage(String serverName, Set<Integer> pids) throws Exception{
		Boolean flag=true;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String read=null;
		System.out.println(serverName+I18nUtil.getString("CONFIG.RUNEVN.PORTCONFLICT"));
		while(flag){
			read=br.readLine();
			if("Y".equalsIgnoreCase(read)){
				ServerUtil.killWithPid4Win(pids);
				flag=false;
			}else if("N".equalsIgnoreCase(read)){
				flag=false;
				System.exit(0);
			}else{
				System.out.println(I18nUtil.getString("CONFIG.RUNEVN.PORTCONFLICT.ILLAGALINPUT"));
			}
		}
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		// TODO Auto-generated method stub
		
	}

}
