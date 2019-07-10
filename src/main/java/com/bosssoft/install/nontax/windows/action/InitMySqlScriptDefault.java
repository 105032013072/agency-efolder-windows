package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

public class InitMySqlScriptDefault implements IAction{
	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
		//调用flyway接口初始化脚本
		InitDBSqlScriptFlyWay initDBSqlScriptFlyWay=new InitDBSqlScriptFlyWay();
		initDBSqlScriptFlyWay.execute(context, params);
		
	}

	

	public void rollback(IContext context, Map params) throws InstallException {

	}



}
