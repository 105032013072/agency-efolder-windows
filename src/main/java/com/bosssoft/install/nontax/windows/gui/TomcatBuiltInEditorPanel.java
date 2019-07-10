package com.bosssoft.install.nontax.windows.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.wizard.gui.AbstractASEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.component.XFileChooser;

/**
 * 内置应用服务器配置面板
 * @author huangxw
 *
 */
public class TomcatBuiltInEditorPanel  extends AbstractASEditorPanel implements ActionListener{

	Logger logger = Logger.getLogger(getClass());
	
	private String port="8080";

	/*private JLabel lblTCHome = new JLabel();
	
	private JLabel lblTCPort=new JLabel();
	
	private JTextField txPort = new JTextField();

	protected XFileChooser tcHomeChooser = new XFileChooser();
	
	private JCheckBox chkInstall = new JCheckBox();*/
	
	
	public TomcatBuiltInEditorPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void jbInit() {
		setLayout(null);
		setOpaque(false);
		/*this.lblTCHome.setText("Tomcat Home:");
		//this.lblTCHome.setBounds(new Rectangle(0, 5, 90, 16));
		this.lblTCHome.setBounds(new Rectangle(0, 32, 90, 16));
		
		this.tcHomeChooser.setBounds(new Rectangle(117, 30, 253, 21));
		this.tcHomeChooser.setButtonText(I18nUtil.getString("BUTTON.BROWSE2"));
		
		
		this.lblTCPort.setText("Tomcat Port:");
		this.lblTCPort.setBounds(new Rectangle(0, 59, 90, 16));
		this.txPort.setBounds(new Rectangle(117, 56, 215, 22));
		
		this.chkInstall.setText("选择安装内置应用服务器");
		this.chkInstall.setBounds(new Rectangle(0, 5, 200, 16));
		this.chkInstall.setOpaque(false);
		this.chkInstall.setSelected(true);
		
		
		
		
		add(this.lblTCHome, null);
		add(this.tcHomeChooser, null);
		add(this.txPort,null);
		add(this.lblTCPort,null);
		add(this.chkInstall,null);
		
		this.txPort.setText("8080");
		this.txPort.setVisible(false);
		this.tcHomeChooser.setVisible(false);
		this.lblTCHome.setVisible(false);
		this.lblTCPort.setVisible(false);
		*/
	}

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("AS_TOMCAT_VERSION", getParameter());
		p.put("IS_APP_SERVER_INSTALL", true);
		p.put("APP_SERVER_PORT", this.port);
		return p;
	}

	public boolean checkInput() {
		// TODO Auto-generated method stub
		return true;
	}

	public void actionPerformed(ActionEvent arg0) {
		
		
	}
	
	public String getAppSvrPort(){
		return this.port;
	}
    
}
