package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.AbstractDBEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.db.DB2EditorPanel;

public class MysqlBuiltInEditorPanel extends AbstractDBEditorPanel{

	public static final String DB_TYPE_MYSQL = "mysql";
	Logger logger = Logger.getLogger(getClass());
	
	public MysqlBuiltInEditorPanel() {
		this.tfdPort.setText("3306");
		
		
		/*this.tfdSID.setText("bosssoftdb");
		this.tfdUser.setText("root");
		this.tfdPassword.setText("root");*/
		//default_install.properties中获取默认的数据库名、密码、实例
		String dafaultConfig=InstallerFileManager.getConfigDir()+File.separator+"default_install.properties";
		Properties propers=new Properties();
		FileInputStream inStream=null;
		try{
			inStream=new FileInputStream(dafaultConfig);
			propers.load(inStream);
			this.tfdSID.setText(propers.getProperty("DB_NAME"));//数据库名
			this.tfdUser.setText(propers.getProperty("DB_USERNAME"));//数据库用户名
			this.tfdPassword.setText(propers.getProperty("DB_PASSWORD"));//数据库密码
			
		}catch(Exception e){
			logger.error(e);
		}
		
		this.btnDBTest.setEnabled(false);
		this.tfdUser.setEnabled(false);
		this.tfdIP.setEnabled(false);
		this.tfdPort.setEnabled(false);
		this.tfdUrl.setEditable(false);
		this.tfdSID.setEnabled(false);
		this.tfdPassword.setEditable(false);
		
		
	}

	public String getDBUrl() {
		String strURL = "jdbc:mysql://" + this.tfdIP.getText() + ":" + this.tfdPort.getText() + "/" + this.tfdSID.getText();

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
		p.put("DB_IS_INSTALL",true);
		return p;
	}

	public String getJDBCDriverClassName() {
		return "com.mysql.jdbc.Driver";
	}

	public boolean checkInput() {
		//确认数据库是否进行初始化
		if(chkInitDB.isSelected()){
			int dialog_result=MainFrameController.showConfirmDialog(I18nUtil.getString("INITDB.SURE"), I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
		    return dialog_result==0;
	    }
		
		
		return  true;
	}

	public void initUI() {
	}
}
