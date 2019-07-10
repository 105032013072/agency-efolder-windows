package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;

import com.bosssoft.platform.installer.wizard.gui.AbstractDBEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.db.OracleEditorPanel;

public class FrontOracleEditorPanel extends AbstractDBEditorPanel {
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
		this.tfdSID.setText("agencydb");
		
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
