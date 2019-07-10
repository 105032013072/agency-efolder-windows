package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.AbstractDBEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.db.OracleEditorPanel;

public class FrontOracleEditorPanel extends AbstractDBEditorPanel {
	Logger logger = Logger.getLogger(getClass());
	public FrontOracleEditorPanel() {
		initUI();
	}

	public String getDBUrl() {
		String strURL = "jdbc:oracle:thin:@" + this.tfdIP.getText() + ":" + this.tfdPort.getText() + "/" + this.tfdSID.getText();

		return strURL;
	}

	public void initUI() {
		this.lblSID.setText("SID:");
		this.tfdPort.setText("1521");
		Properties propers=new Properties();
		String dafaultConfig= InstallerFileManager.getConfigDir()+ File.separator+"default_install.properties";
		FileInputStream inStream= null;
		try{
			inStream=new FileInputStream(dafaultConfig);
			propers.load(inStream);
			this.tfdSID.setText(propers.getProperty("DB_NAME"));//数据库名
		}catch(Exception e){
			logger.error(e);
		}

		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new OracleEditorPanel(), "Center");
		frame.setVisible(true);
	}

	public Properties getProperties() {
		Properties p = super.getProperties();
		p.put("DB_TYPE", "oracle");
		p.put("DB_IS_INSTALL", false);
        p.put("DB_SCHEMA", tfdUser.getText().trim().toUpperCase());
		return p;
	}

	public String getJDBCDriverClassName() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	public boolean checkInput() {
		boolean validate = super.checkInput();
		if (!validate) {
			return false;
		}
		return true;
	}
}
