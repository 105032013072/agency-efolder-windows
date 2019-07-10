package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.util.DBConnectionUtil;

public class InstallMysql implements IAction{
	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {

		String mysqlHome=new File(params.get("MYSQL_ZIP_PATH").toString()).getPath();
		String dir=mysqlHome.substring(0, mysqlHome.indexOf(":"));
		String dbname=(String) context.getValue("DB_NAME");
		String pw=context.getStringValue("DB_PASSWORD");
		String batFile=new File(InstallerFileManager.getInstallerHome()+File.separator+"installer"+File.separator+"mysql_install.bat").getPath();
		
		/* String mysqlHome="E:/mysql_install/mysql-5.7.19-winx64";
			String dir="E";
			String dbname="test";
			String batFile=new File("D:/test/mysql_install/mysql_install.bat").getPath();*/
		
		try {
			runbat(batFile,dir,mysqlHome,dbname,pw);
			 //测试是否安装成功
	        getConnection(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InstallException(e);
		}
		
	}

	

	private void runbat(String batFile,String... argStrings) throws Exception {

		String cmd = "cmd.exe /c start  /b  " + batFile + " ";
		//String cmd = "cmd.exe /c   " + batFile + " ";
	    if (argStrings != null && argStrings.length > 0) {
	        for (String string : argStrings) {
	            cmd += string + " ";
	        }
	    }
		System.out.println(cmd);//dbname 没有
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
	        System.out.println(line);
	    }
	    
	    int i = ps.exitValue(); 
	    if (i == 0) {
	        System.out.println("执行完成.");
	    } else {
	        System.out.println("执行失败.");
			logger.info("install mysql failed");
	        throw new Exception("install mysql failed");
	    }
	    ps.destroy();
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}
	private Connection getConnection(IContext context) {
		Connection conn = null;
		String user = context.getStringValue("DB_USERNAME");
		String password = context.getStringValue("DB_PASSWORD");
		String url = context.getStringValue("DB_URL");
		String driver = context.getStringValue("DB_DRIVER");
		String userJdbcJars = context.getStringValue("DB_JDBC_LIBS");
		try {
			conn = DBConnectionUtil.getConnection(userJdbcJars, driver, url, user, password);
		} catch (Exception e) {
			throw new InstallException("faild to get DB connection, install mysql failed"+e);
		}
		return conn;
	}

}
