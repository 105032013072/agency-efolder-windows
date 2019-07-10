package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.option.RegisterDef;
import com.bosssoft.platform.installer.core.option.RegisterDefHelper;

public class CreateStopFile implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
		String batFile=params.get("targetDir").toString()+"/"+"stop.bat";
        File f=new File(batFile);
        BufferedWriter out=null;
        try {
        	//如果文件存在就删除，重建
    		if(f.exists()) f.delete();
    		f.createNewFile();
    		
        	out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));
        	out.write("@echo off"+System.lineSeparator());
        	out.write("path=%SystemRoot%/system32;%SystemRoot%;%SystemRoot%;%path%"+System.lineSeparator());
        	//停止的服务（按启动顺序的逆序停止服务）
        	List<RegisterDef> list=RegisterDefHelper.getRegisterList(context);
        	for(int i=list.size()-1;i>=0;i--){
        		RegisterDef registerDef=list.get(i);
        		String workPath=registerDef.getWorkDir();
			   	StringBuffer content=new StringBuffer();
			   	content.append("cd "+workPath+System.getProperty("line.separator"));
			   	content.append(workPath.substring(0, 2)+System.getProperty("line.separator"));//截取工作磁盘名
			   	content.append(registerDef.getStopCmd()+System.getProperty("line.separator"));
			   	out.write(content.toString());
        	}
        	//数据库停止
        	if("true".equalsIgnoreCase(context.getStringValue("DB_IS_INSTALL"))){
        		out.write("net stop mysql"+System.lineSeparator());
        	}
        	
        	out.write("echo stop success"+System.lineSeparator());
        	out.write("pause");
		    
        	
        } catch (Exception e) {
			throw new InstallException("Faild to create stop File",e);
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					throw new InstallException(e);
				}
			}
		}
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}
   
}
