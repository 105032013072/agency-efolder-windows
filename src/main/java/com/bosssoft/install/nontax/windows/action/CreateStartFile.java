 package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bosssoft.platform.installer.core.util.I18nUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.RegisterDef;
import com.bosssoft.platform.installer.core.option.RegisterDefHelper;

import javax.swing.filechooser.FileSystemView;

 public class CreateStartFile implements IAction{
    
	transient Logger logger=Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
		String environments=params.get("environments").toString();
		String batFile=params.get("targetDir").toString();
		try {
			createBatFile(batFile,environments,context);
		} catch (IOException e) {
			throw new InstallException(e);
		}
		
	}


	private void createBatFile(String createFilePath, String environments, IContext context) throws IOException {
		File f=new File(createFilePath+"/"+"start.bat");
		BufferedWriter out=null;
        try {
        	//如果文件存在就删除，重建
    		if(f.exists()) f.delete();
    		f.createNewFile();
    		
        	out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));
        	out.write("@echo off"+System.lineSeparator());
			out.write("chcp 65001"+System.lineSeparator());

        	//设置环境变量
        	String[] es=environments.split(",");
        	for (String en : es) {
        		String content="set "+en+"="+context.getStringValue(en)+System.getProperty("line.separator");
        		out.write(content);
    		}
        	
        	//设置启动的服务
        	/*String[] ss=servers.split(",");
		    for (String server : ss) {
		    	String serverpath=context.getStringValue(server);
		    	
		    	String folderpath=serverpath.substring(0,serverpath.lastIndexOf("/"));
		    	String serverName=serverpath.substring(serverpath.lastIndexOf("/")+1,serverpath.length());
		    	
				//String content="cd "+folderpath+System.getProperty("line.separator")+"start "+serverName+System.getProperty("line.separator");
				StringBuffer content=new StringBuffer();
				content.append("cd "+folderpath+System.getProperty("line.separator"));
				content.append(folderpath.substring(0, 2)+System.getProperty("line.separator"));//截取工作磁盘名
				content.append("start "+serverName+System.getProperty("line.separator"));
				out.write(content.toString());
			}*/
        	
        	//若mysql为内置安装，配置为启动项
        	if("true".equals(context.getStringValue("DB_IS_INSTALL"))){
        		out.write("net start efolderMySQL"+System.lineSeparator());
        	}
        	
        
        	//设置启动的服务
        	List<RegisterDef> list=RegisterDefHelper.getRegisterList(context);
        	for (RegisterDef registerDef : list) {
			   	String workPath=registerDef.getWorkDir();
			   	StringBuffer content=new StringBuffer();
			   	content.append("cd "+workPath+System.getProperty("line.separator"));
			   	content.append(workPath.substring(0, 2)+System.getProperty("line.separator"));//截取工作磁盘名
			   	content.append(registerDef.getStartCmd()+System.getProperty("line.separator"));
			   	out.write(content.toString());
			}
        	if(!"Tomcat".equalsIgnoreCase(context.getStringValue("APP_SERVER_NAME"))){
        		String appSvrPath=context.getStringValue("appSvr_path");
        		String folderpath=appSvrPath.substring(0,appSvrPath.lastIndexOf("/"));
		    	String serverName=appSvrPath.substring(appSvrPath.lastIndexOf("/")+1,appSvrPath.length());
		    	StringBuffer content=new StringBuffer();
				content.append(appSvrPath+System.getProperty("line.separator"));
				content.append("cd "+folderpath+System.getProperty("line.separator"));
				content.append(folderpath.substring(0, 2)+System.getProperty("line.separator"));//截取工作磁盘名
				content.append("start "+serverName+System.getProperty("line.separator"));
				out.write(content.toString());
        	}

			//设置快捷方式
			//
			String shortCutPath= FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
					+File.separator+ "财政电子票夹_v1.0.url";
			StringBuffer content=new StringBuffer();
			Integer port=Integer.valueOf(context.getStringValue("APP_SERVER_PORT"));
			String dir = context.getValue("INSTALL_DIR").toString();
			content.append("set file="+shortCutPath+System.lineSeparator());
			content.append("echo [InternetShortcut] >>%file%"+System.lineSeparator());
			content.append("echo URL=\"http://localhost:"+port+"/efolder-web/index/login.do\" >>%file%"+System.lineSeparator());
			content.append("echo IconIndex=0 >>%file%"+System.lineSeparator());
			content.append("echo IconFile="+dir+File.separator+"image"+File.separator+"efolder.ico >>%file%"+System.lineSeparator());
			out.write(new String(content.toString().getBytes(),"UTF-8"));

        	//设置延时，然后访问URL
			content=new StringBuffer();
        	content.append("echo server is starting......"+System.lineSeparator());
        	content.append("ping 127.0.0.1 -n 10 >nul "+System.lineSeparator());
        	out.write(content.toString());



        	out.write("echo start success"+System.lineSeparator());

        	out.write("pause");
        	
		    
        	
        } catch (Exception e) {
			throw new InstallException("Faild to create start File",e);
		} finally{
			if(out!=null) out.close();
		}
		
	}
	public void rollback(IContext context, Map params) throws InstallException {
		// TODO Auto-generated method stub
		
	}

}
