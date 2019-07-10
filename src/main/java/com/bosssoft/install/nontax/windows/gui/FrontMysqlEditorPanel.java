package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import javax.swing.JFrame;

import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.AbstractDBEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.db.DB2EditorPanel;

public class FrontMysqlEditorPanel extends AbstractDBEditorPanel {
	public static final String DB_TYPE_MYSQL = "mysql";
	Logger logger = Logger.getLogger(getClass());

	public FrontMysqlEditorPanel() {
		Properties propers=new Properties();
		String dafaultConfig= InstallerFileManager.getConfigDir()+ File.separator+"default_install.properties";
		FileInputStream inStream= null;
		this.tfdPort.setText("3306");
		try{
			inStream=new FileInputStream(dafaultConfig);
			propers.load(inStream);
			this.tfdSID.setText(propers.getProperty("DB_NAME"));//数据库名
		}catch(Exception e){
			logger.error(e);
		}

		this.tfdSID.setText(propers.getProperty("DB_NAME"));//数据库名
		initUI();
	}

	public String getDBUrl() {
		String strURL = "jdbc:mysql://" + this.tfdIP.getText() + ":" + this.tfdPort.getText() + "/" + this.tfdSID.getText();
		//strURL=strURL+"prepStmtCacheSize=517&cachePrepStmts=true&autoReconnect=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
		return strURL;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new DB2EditorPanel(), "Center");
		frame.setVisible(true);
	}

	public Properties getProperties() {
		Properties p = super.getProperties();
		p.put("DB_TYPE", "mysql");
		p.put("DB_IS_INSTALL",false);
		return p;
	}

	public String getJDBCDriverClassName() {
		return "com.mysql.jdbc.Driver";
	}

	public boolean checkInput() {
		boolean validate = super.checkInput();
		if (!validate) {
			return false;
		}
		return true;
	}

	public void initUI() {
	
	}
}
