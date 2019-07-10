package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;


import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.initdb.SqlScriptRunner;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.action.InitDBSqlScript;
import com.bosssoft.platform.installer.wizard.action.InitDBSqlScriptFlyWay;
import com.bosssoft.platform.installer.wizard.util.DBConnectionUtil;
import com.bosssoft.platform.installer.wizard.util.OrderedProperties;

/**
 * 调用外部化工具进行初始化
 * @author huangxw
 *
 */
public class InitMySqlScriptExternal implements IAction{
	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
		//通过sql方式执行脚本
		/*File parentDir=new File(InstallerFileManager.getInstallerHome()+File.separator+"resource"+File.separator+"db"+File.separator+"script");
		File[] files=parentDir.listFiles();
		StringBuffer strbuffer=new StringBuffer("");
		for (File f : files) {
			strbuffer.append(f.getPath()).append(",");
		}
		context.setValue("DB_INIT_SQLSCRIPT", strbuffer.toString());
		InitDBSqlScript initDBSqlScript=new InitDBSqlScript();
		initDBSqlScript.execute(context, params);*/
		
		//调用fly工具
		String configFile=params.get("configFile").toString();
		String runFile=new File(params.get("runFile").toString()).getPath();
		String configKeys[]=params.get("configKey").toString().split(",");
		String contextKeys[]=params.get("contextKey").toString().split(",");
		
		try{
			//修改配置
			OrderedProperties props=new OrderedProperties();
			File f = new File(configFile);   
	        InputStream in = new FileInputStream(f);   
			props.load(in);
			in.close();
			
			for(int i=0;i<configKeys.length;i++){
				String key=configKeys[i];
				String value=context.getStringValue(contextKeys[i]);
				props.setProperty(key, value);
			}
			 OutputStream out = new FileOutputStream(f);
			 props.store(out, null);
			 out.close();
			 
			 //调用flyway的执行脚本
			 runbat(runFile);
			 
		}catch(Exception e){
			logger.error(e);
			throw new InstallException(e);
		}
		
		
	}

	private void runbat(String runFile) throws Exception {
		//String cmd = "cmd.exe /c start  /b  " + runFile + " ";
		String cmd = "cmd.exe /c  start  " + runFile + " ";
	    
	    Process ps = Runtime.getRuntime().exec(cmd);
	     //取得命令结果的输出流
	    InputStream fis=ps.getInputStream();
	    //用一个读输出流类去读
	    InputStreamReader isr=new InputStreamReader(ps.getInputStream(),Charset.forName("GBK"));
	    //用缓冲器读行
	    BufferedReader br=new BufferedReader(isr);
	    String line=null;
	    //直到读完为止
	    while((line=br.readLine())!=null) {
	       logger.info(line);
	    }
	    
	    int i = ps.exitValue(); 
	    if (i == 0) {
	        logger.info("脚本初始化成功");
	    } else {
	    	 logger.info("脚本初始化失败");
	        throw new Exception("init dbScript faild");
	    }
	    
	    br.close();
	    isr.close();
	    fis.close();
	    ps.destroy();
		
	}

	public void rollback(IContext context, Map params) throws InstallException {

	}



}
