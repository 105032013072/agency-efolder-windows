package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

import com.bosssoft.install.nontax.windows.util.InstallUtil;
import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.ModuleDef;
import com.bosssoft.platform.installer.core.option.RegisterDef;
import com.bosssoft.platform.installer.core.option.RegisterDefHelper;

public class CreateUninstallFile implements IAction{
	
	transient Logger logger=Logger.getLogger(getClass());

	public void execute(IContext context, Map params) throws InstallException {
			String createFile=params.get("targetDir").toString()+File.separator+"uninstall.bat";
			String sourceFile=params.get("sourceFILE").toString();
	        try {
				createBatFile(sourceFile,createFile,context);
			} catch (IOException e) {
				throw new InstallException(e);
			}
	}
	
	private void createBatFile(String sourceFile,String createFile, IContext context) throws IOException {
		File f=new File(createFile);
		StringBuffer strbuffer=new StringBuffer();
		BufferedWriter out=null;
		try {
			String delop=InstallUtil.readFile(sourceFile);
			
        	//如果文件存在就删除，重建
    		if(f.exists()) f.delete();
    		f.createNewFile();
    		
        	out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"GBk"));
            strbuffer.append("@echo off"+System.lineSeparator());
            strbuffer.append("path=%SystemRoot%/system32;%SystemRoot%;%SystemRoot%;%path%"+System.lineSeparator());
        	//移除服务
        	strbuffer.append("echo 正在移除应用......"+System.lineSeparator());
        	List<RegisterDef> list=RegisterDefHelper.getRegisterList(context);
        	for (RegisterDef registerDef : list) {
			   	String workPath=registerDef.getWorkDir();
			   	strbuffer.append("cd "+workPath+System.lineSeparator());
			   	strbuffer.append(workPath.substring(0, 2)+System.lineSeparator());//截取工作磁盘名
				strbuffer.append(registerDef.getStopCmd()+System.lineSeparator());
			   	strbuffer.append(registerDef.getRemoveCmd()+System.lineSeparator());
			}

        	//移除数据库服务
        	//if("true".equalsIgnoreCase(context.getStringValue("DB_IS_INSTALL"))){
        	//	strbuffer.append("sc delete mysql"+System.lineSeparator());
        	//}
        	
        	//删除桌面快捷键
        	String shortCutPath=FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
        			            +File.separator+"efolder_v1.0.url";
            strbuffer.append("del "+shortCutPath+System.lineSeparator());

        	
        	 
        	//删除安装目录下的文件
        	String installPath=context.getStringValue("INSTALL_DIR");
        	strbuffer.append("echo 正在删除文件......"+System.lineSeparator());
        	
        	/*strbuffer.append("set path="+installPath+System.getProperty("line.separator"));
        	strbuffer.append(delop+System.getProperty("line.separator"));*/

			File file = new File(installPath);
			File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中

        	String appsvrType = context.getStringValue("APP_SERVER_TYPE");
        	List<ModuleDef> optionsCompList=(List<ModuleDef>) context.getValue("MODULE_OPTIONS");
        	for (ModuleDef moduleDef : optionsCompList) {
        		//删除应用服务器下组件程序
        		appSvrDir(appsvrType,moduleDef,strbuffer,context);
				//删除boss_home下的文件
        		//bossHomeDir(moduleDef,strbuffer,context);
			}
			for (File item : fileList) {
        		if(item.getName().equals("mysql")){
        			continue;
				}
				if(item.isDirectory()) {
					strbuffer.append("rd /s /q " + item + System.lineSeparator());
				}
				else if(item.isFile()){
					strbuffer.append("del /f /s /q " + item + System.lineSeparator());
				}
			}

			//strbuffer.append("rd /s /q "+installPath+System.lineSeparator());
        	strbuffer.append("echo 卸载完成"+System.lineSeparator());
        	strbuffer.append("pause");
        	out.write(strbuffer.toString());
        	
        } catch (Exception e) {
        	throw new InstallException("Faild to create uninstall File",e);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		
	}



	private void appSvrDir(String appsvrType, ModuleDef moduleDef, StringBuffer strbuffer, IContext context) {
		String delpath=null;
		if(appsvrType.toLowerCase().indexOf("tomcat")!=-1){
            delpath=context.getStringValue("AS_TOMCAT_HOME")+File.separator+"webapps"+File.separator+moduleDef.getNameKey();
            
		}else if(appsvrType.toLowerCase().indexOf("weblogic")!=-1){
			//删除autodeploy下的工程文件
			delpath=context.getStringValue("AS_WL_DOMAIN_HOME")+File.separator+"autodeploy"+File.separator+moduleDef.getNameKey();
		}
        if(!new File(delpath).exists()) return;
        
			strbuffer.append("rd /s /q "+delpath.replaceAll("/", "\\\\")+System.getProperty("line.separator"));
	}

	private void bossHomeDir(ModuleDef moduleDef, StringBuffer strBuffer, IContext context) {
		String delPath=context.getStringValue("BOSSSOFT_HOME")+"/"+moduleDef.getNameKey();
		if(!new File(delPath).exists()) return;
		strBuffer.append("rd /s /q "+delPath.replaceAll("/", "\\\\")+System.getProperty("line.separator"));
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}
    
	
}
