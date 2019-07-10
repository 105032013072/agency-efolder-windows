package com.bosssoft.install.nontax.windows.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.ReflectUtil;
import com.bosssoft.platform.installer.wizard.action.CheckDataSourceExistAction;
import com.bosssoft.platform.installer.wizard.action.InitDB;
import com.bosssoft.platform.installer.wizard.cfg.ProductInstallConfigs;
import com.bosssoft.platform.installer.wizard.cfg.Server;
import com.bosssoft.platform.installer.wizard.gui.AbstractDBEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.component.MultiLabel;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;
import com.bosssoft.platform.installer.wizard.gui.component.XFileChooser;
import com.bosssoft.platform.installer.wizard.gui.validate.ValidatorHelper;
import com.bosssoft.platform.installer.wizard.util.DBConnectionUtil;

public class ConfigBusinessPanel extends AbstractSetupPanel implements ActionListener {
	
	
	Logger logger = Logger.getLogger(getClass());
	private StepTitleLabel line = new StepTitleLabel();

	private JLabel lblAgencyCode = new JLabel();
	private JTextField tfdAgencyCode = new JTextField();
	
	private JTextArea taIntroduction = new JTextArea();
	
	private JLabel lblAgencyName = new JLabel();
	private JTextField tfdAgencyName = new JTextField();
	
	
	private JLabel lblRegionCode = new JLabel();
	private JTextField tfdRegionCode = new JTextField();
	
	
	private JLabel lblAppid = new JLabel();
	private JTextField tfdAppid = new JTextField();
	
	private JLabel lblKey = new JLabel();
	private JTextField tfdKey = new JTextField();
	
	private JLabel lblInterface = new JLabel();
	private JTextField tfdInterface = new JTextField();
	
	private JLabel lblInitBusiness = new JLabel();
	protected JCheckBox chkInitBusiness = new JCheckBox();

	
	public ConfigBusinessPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//加载支持的数据库列表，添加默认选中的数据库的编辑面板
	void jbInit() throws Exception {
		setOpaque(false);
		setLayout(null);
		
		this.line.setText(I18nUtil.getString("STEP.BUSINESS"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));
		add(this.line, null);
		
		this.taIntroduction.setOpaque(false);
		this.taIntroduction.setText("请填入财政给的授权信息，并开始进行系统初始化");
		this.taIntroduction.setRows(1);
		this.taIntroduction.setLineWrap(true);
		this.taIntroduction.setWrapStyleWord(true);
		this.taIntroduction.setBounds(new Rectangle(37, 33, 375, 41));
		this.taIntroduction.setEditable(false);
		add(this.taIntroduction, null);
		
		this.lblAgencyCode.setText(I18nUtil.getString("BUSINESS.AGENCYCODE")+":");
		//this.lblAgencyCode.setBounds(new Rectangle(30, 38, 100, 16));
		this.lblAgencyCode.setBounds(new Rectangle(30, 60, 100, 16));
		//this.tfdAgencyCode.setBounds(new Rectangle(162, 35, 237, 21));
		this.tfdAgencyCode.setBounds(new Rectangle(60, 85, 237, 21));
		add(this.lblAgencyCode, null);
		add(this.tfdAgencyCode, null);
		
		
		this.lblAgencyName.setText(I18nUtil.getString("BUSINESS.AGENCYNAME")+":");
		//this.lblAgencyName.setBounds(new Rectangle(30, 61, 64, 16));
		//this.tfdAgencyName.setBounds(new Rectangle(162, 60, 237, 21));
		this.lblAgencyName.setBounds(new Rectangle(30, 113, 64, 16));
		this.tfdAgencyName.setBounds(new Rectangle(60, 138, 237, 21));
		add(this.lblAgencyName, null);
		add(this.tfdAgencyName, null);
		
		
		this.lblRegionCode.setText(I18nUtil.getString("BUSINESS.REGIONCODE")+":");
		/*this.lblRegionCode.setBounds(new Rectangle(30, 84, 64, 16));
		this.tfdRegionCode.setBounds(new Rectangle(60, 85, 237, 21));*/
		this.lblRegionCode.setBounds(new Rectangle(30, 166, 64, 16));
		this.tfdRegionCode.setBounds(new Rectangle(60, 191, 237, 21));
		add(this.lblRegionCode, null);
		add(this.tfdRegionCode, null);
		
		this.lblAppid.setText(I18nUtil.getString("BUSINESS.APPID")+":");
		/*this.lblAppid.setBounds(new Rectangle(30, 108, 64, 16));
		this.tfdAppid.setBounds(new Rectangle(60, 110, 237, 21));*/
		this.lblAppid.setBounds(new Rectangle(30, 220, 64, 16));
		this.tfdAppid.setBounds(new Rectangle(60, 245, 237, 21));
		add(this.lblAppid, null);
		add(this.tfdAppid, null);
		
		this.lblKey.setText(I18nUtil.getString("BUSINESS.KEY")+":");
		/*this.lblKey.setBounds(new Rectangle(30, 134, 64, 16));
		this.tfdKey.setBounds(new Rectangle(60, 135, 237, 21));*/
		this.lblKey.setBounds(new Rectangle(30, 275, 64, 16));
		this.tfdKey.setBounds(new Rectangle(60, 300, 237, 21));
		add(this.lblKey, null);
		add(this.tfdKey, null);
		
		this.lblInterface.setText(I18nUtil.getString("BUSINESS.INTERFACE")+":");
		/*this.lblInterface.setBounds(new Rectangle(30, 158, 100, 16));
		this.tfdInterface.setBounds(new Rectangle(60, 160, 237, 21));*/
		this.lblInterface.setBounds(new Rectangle(30, 330, 100, 16));
		this.tfdInterface.setBounds(new Rectangle(60, 355, 237, 21));
		add(this.lblInterface, null);
		add(this.tfdInterface, null);
		
		/*this.lblInitBusiness.setText(I18nUtil.getString("BUSINESS.INIT")+":");
		this.lblInitBusiness.setBounds(new Rectangle(30, 181, 120, 16));
		this.chkInitBusiness.setBounds(new Rectangle(60,185 , 237, 21));
		this.lblInitBusiness.setBounds(new Rectangle(30, 273, 120, 16));
		this.chkInitBusiness.setBounds(new Rectangle(60, 245, 237, 21));*/
		//add(this.lblInitBusiness, null);
		//add(this.chkInitBusiness, null);
		//refreshSubPanel();
		
		
		
	}

	public void afterShow() {
	}

	//将数据库配置信息保存到context中
	public void beforeNext() {

		Properties properties = new Properties();
		properties.put("BUSINESS_AGENCYCODE", gbEncoding(this.tfdAgencyCode.getText().trim()));
		properties.put("BUSINESS_AGENCYNAME", gbEncoding(this.tfdAgencyName.getText().trim()));
		properties.put("BUSINESS_REGIONCODE", gbEncoding(this.tfdRegionCode.getText().trim()));
		
		properties.put("BUSINESS_APPID", gbEncoding(this.tfdAppid.getText().trim()));
		properties.put("BUSINESS_KEY", gbEncoding(this.tfdKey.getText().trim()));
		properties.put("BUSINESS_INTERFACE", gbEncoding(this.tfdInterface.getText().trim()));
		//properties.put("BUSINESS_INIT", this.chkInitBusiness.isSelected());
		
		getContext().putAll(properties);
		// 记录日志
		logger.info("config businessInfo: " + properties);
	}

	private Properties getProperties() {
		/*Properties p = new Properties();

		p.put("DB_IP", this.tfdIP.getText().trim());
		p.put("DB_NAME", this.tfdSID.getText());
		p.put("DB_USERNAME", this.tfdUser.getText());
		p.put("DB_PASSWORD", new String(this.tfdPassword.getPassword()));
		p.put("DB_SERVER_PORT", this.tfdPort.getText().trim());
		p.put("DB_URL", this.tfdUrl.getText().trim());
		p.put("DB_IS_INIT", Boolean.toString(this.chkInitDB.isSelected()));
		p.put("DB_IS_DEFAULT_JAR", "true");
        p.put("DB_IS_INSTALL", Boolean.toString(this.chkInstall.isSelected()));
		p.put("DB_DRIVER", "com.mysql.jdbc.Driver");
		p.put("DB_JDBC_LIBS", "");
*/
		
		//return p;
		return null;
	}

	public void beforePrevious() {
	}

	public void beforeShow() {
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("run",true);
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonVisible("help", false);
		controlPane.setButtonVisible("next", false);
		controlPane.setButtonVisible("cancel", false);
		controlPane.setButtonVisible("previous", false);
		//controlPane.setDefaultButton("run");
		
	}

	public boolean checkInput() {

		return true;
	}

	public void initialize(String[] parameters) {
	}

	

	public void afterActions() {
	}

	public void actionPerformed(ActionEvent ae) {
	
	}

	public void resetTabSpaceText() {
		
	}

	 public static String gbEncoding(String gbString) {   
	        /*char[] utfBytes = gbString.toCharArray();  
	        String unicodeBytes = "";   
	        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
	            String hexB = Integer.toHexString(utfBytes[byteIndex]);   
	              if (hexB.length() <= 2) {   
	                  hexB = "00" + hexB;   
	             }   
	             unicodeBytes = unicodeBytes + "\\u" + hexB;   
	        }   
	        
	        return unicodeBytes; */ 
		 
		 final UnicodeEscaper ue = UnicodeEscaper.above('Z');
	      return ue.translate(gbString);
	  }
	
}
