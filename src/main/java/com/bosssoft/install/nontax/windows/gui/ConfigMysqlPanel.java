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

public class ConfigMysqlPanel extends AbstractSetupPanel implements ActionListener {
	Logger logger = Logger.getLogger(getClass());
	private StepTitleLabel line = new StepTitleLabel();

	private JLabel lblDB = new JLabel();
	private JComboBox cbxDb = new JComboBox();

	protected JTextField tfdUrl = new JTextField();
	protected JLabel lblIP = new JLabel();
	protected JLabel lblUrl = new JLabel();
	protected JLabel lblPort = new JLabel();
	protected JCheckBox chkInitDB = new JCheckBox();
	protected MultiLabel lblTabSpace = new MultiLabel(I18nUtil.getString("DBCONFIG.MSG.TABSPACE"), 4, 2, 3);
	protected JTextField tfdSID = new JTextField();
	protected JLabel lbPassword = new JLabel();
	protected JLabel lblUser = new JLabel();
	protected JLabel lblSID = new JLabel();
	protected JCheckBox chkInstall = new JCheckBox();

	protected JTextField tfdPort = new JTextField();

	
	protected JButton btnDBTest = new JButton();

	protected JTextField tfdIP = new JTextField();

	protected JTextField tfdInformixName = new JTextField();

	protected JPasswordField tfdPassword = new JPasswordField();
	protected JTextField tfdUser = new JTextField();

	private JTextArea attention = new JTextArea();
	
	
	
	protected TxtDocumentListener documentListener = new TxtDocumentListener();
	private   EditorActionListener actionListener = new EditorActionListener();

	public ConfigMysqlPanel() {
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
		this.line.setText(I18nUtil.getString("STEP.DBCONFIG"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.lblDB.setText(I18nUtil.getString("DBCONFIG.LABEL.DBTYPE"));
		this.lblDB.setBounds(new Rectangle(30, 38, 100, 16));

		this.cbxDb.setBounds(new Rectangle(162, 35, 140, 20));

		loadSupportedDBSvr();
		this.cbxDb.addActionListener(this);

		add(this.line, null);
		add(this.lblDB, null);
		add(this.cbxDb, null);
		
		
		this.btnDBTest.setText(I18nUtil.getString("DBCONFIG.BUTTON.CONTEST"));
		this.btnDBTest.setMnemonic('T');
		this.btnDBTest.setMargin(new Insets(2, 2, 2, 2));
		this.btnDBTest.setOpaque(false);
		this.btnDBTest.setEnabled(false);
	

		this.tfdUser.setBounds(new Rectangle(162, 148, 237, 21));
		this.tfdPassword.setBounds(new Rectangle(162, 178, 237, 21));
		this.tfdUser.setText("root");
		this.tfdUser.setEnabled(false);
		this.tfdPassword.setEditable(false);
		this.tfdIP.setBounds(new Rectangle(162, 60, 237, 21));
		this.tfdIP.setText("127.0.0.1");
		this.tfdIP.setEnabled(false);
		
		this.btnDBTest.setBounds(new Rectangle(285, 240, 120, 21));
		
		this.tfdPort.setText("3306");
		this.tfdPort.setEnabled(false);
		this.tfdPort.setBounds(new Rectangle(162, 89, 54, 21));
		
		this.lblSID.setBounds(new Rectangle(30, 119, 94, 16));
		this.lblUser.setBounds(new Rectangle(30, 148, 96, 16));
		this.lbPassword.setBounds(new Rectangle(30, 180, 97, 16));
		this.tfdSID.setBounds(new Rectangle(162, 119, 237, 21));
        this.tfdSID.setText("bosssoftdb");
		
		this.chkInitDB.setBounds(new Rectangle(30, 235, 200, 25));
		this.lblTabSpace.setBounds(new Rectangle(0, 251, 380, 80));
		this.lblTabSpace.setForeground(Color.red);
		this.lblTabSpace.setVerticalAlignment(1);
		this.lblTabSpace.setVisible(false);

		this.lblPort.setBounds(new Rectangle(30, 90, 70, 16));
		this.lblUrl.setBounds(new Rectangle(30, 208, 101, 16));
		this.lblIP.setBounds(new Rectangle(30, 61, 64, 16));
		this.tfdUrl.setBounds(new Rectangle(162, 207, 237, 21)); 
		
		this.lblSID.setText(I18nUtil.getString("DBCONFIG.LABEL.DBNAME"));
		this.lblUser.setText(I18nUtil.getString("DBCONFIG.LABEL.USER"));
		this.lbPassword.setText(I18nUtil.getString("DBCONFIG.LABEL.PASSWORD"));
		this.chkInitDB.setText(I18nUtil.getString("DBCONFIG.LABEL.INITDB"));
		this.chkInitDB.setOpaque(false);
		this.chkInitDB.setMargin(new Insets(0, 0, 0, 0));
		this.chkInitDB.setSelected(true);
		this.lblPort.setText(I18nUtil.getString("DBCONFIG.LABEL.PORT"));
		this.lblUrl.setText(I18nUtil.getString("DBCONFIG.LABEL.URL"));
		this.lblIP.setText(I18nUtil.getString("DBCONFIG.LABEL.IP"));
	
		this.tfdUrl.setEditable(false);
		this.tfdSID.setEnabled(false);
		
		this.chkInstall.setText("安装Mysql");
		this.chkInstall.setBounds(new Rectangle(310, 38, 100, 16));
		this.chkInstall.setOpaque(false);
		this.chkInstall.setMargin(new Insets(0, 0, 0, 0));
		this.chkInstall.setSelected(true);
		
		this.attention.setBounds(new Rectangle(27, 270, 382, 95));
		this.attention.setAlignmentY(0.0F);
		this.attention.setBackground(Color.WHITE);
		this.attention.setOpaque(false);
		this.attention.setEditable(false);
		this.attention.setLineWrap(true);
		this.attention.setWrapStyleWord(true);
		this.attention.setBorder(new TitledBorder(I18nUtil.getString("MYSQL.LABEL.ATTENTION")));
		//this.attention.setFont(new Font("宋体",Font.BOLD, 12));
		
		add(this.lblPort, null);
		add(this.tfdSID, null);
		add(this.tfdPort, null);
		add(this.tfdUrl, null);
		
		add(this.chkInitDB, null);
		add(this.lblTabSpace, null);
		add(this.tfdPassword, null);
		add(this.btnDBTest, null);
	
		add(this.lbPassword, null);
		add(this.lblUser, null);
		add(this.lblUrl, null);
		add(this.tfdUser, null);
		
		add(this.lblSID, null);
		add(this.tfdIP, null);
		add(this.lblIP, null);
		add(this.chkInstall,null);

		add(this.attention,null);
		
		this.tfdUser.getDocument().addDocumentListener(this.documentListener);
		this.tfdPassword.getDocument().addDocumentListener(this.documentListener);
		this.tfdIP.getDocument().addDocumentListener(this.documentListener);
		this.tfdPort.getDocument().addDocumentListener(this.documentListener);
		this.tfdSID.getDocument().addDocumentListener(this.documentListener);

		this.btnDBTest.addActionListener(this.actionListener);
		
		this.chkInitDB.addActionListener(this.actionListener);

		this.chkInstall.addActionListener(this.actionListener);
		
		this.tfdUrl.setText(getDBUrl());
		//refreshSubPanel();
	}

	public void afterShow() {
	}

	//将数据库配置信息保存到context中
	public void beforeNext() {
	
		
			Properties properties = getProperties();
			getContext().putAll(properties);
			Server dbServer = (Server) this.cbxDb.getSelectedItem();

			getContext().setValue("DB_TYPE", dbServer.getName());
			getContext().setValue("DB_VERSION", dbServer.getVersion());

			getContext().setValue("DB_DS_JNDI_NAME", "datasource");
		
			//记录日志
		   logger.info("config DB: "+properties);
	}

	private Properties getProperties() {
		Properties p = new Properties();

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

		
		return p;
	}

	public void beforePrevious() {
	}

	public void beforeShow() {
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonVisible("help", false);
		
		attention.setText(I18nUtil.getString("MYSQL.CONTENT.ATTENTION"));
	}

	public boolean checkInput() {
		String app_server_name = this.context.getStringValue("APP_SERVER_NAME");
		if(btnDBTest.isEnabled()){
			if (("WebLogic".indexOf(app_server_name) != -1) || ("WebSphere6.0".indexOf(app_server_name) != -1)) {
				String result = this.testDBConnection();
				if (result != null) {
					showError(result);
					return false;
				}
			}
		}
		
		String message = check();
		if ((message != null) && (this.chkInitDB.isSelected())) {
			showError(message);
			return false;
		}

		if (this.chkInitDB.isSelected()&&!chkInstall.isSelected()) {
			String result = testDBConnection();
			if (result != null) {
				showError(result);
				return false;
			}

		}

		if (message != null) {
			message = I18nUtil.getString("DBCONFIG.MSG.CONNECTERROR") + message + I18nUtil.getString("DBCONFIG.MSG.CONTINUE");
			int result = MainFrameController.showConfirmDialog(message, I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
			return result == 0;
		}
		if(btnDBTest.isEnabled()){
			String test_conn_result = testDBConnection();
			if (test_conn_result != null) {
				String msg = I18nUtil.getString("DBCONFIG.MSG.CONNECTERROR") + I18nUtil.getString("DBCONFIG.MSG.CONTINUE");
				int dialog_result = MainFrameController.showConfirmDialog(msg, I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
				return dialog_result == 0;
			}
		}
		
		
		//确认数据库是否进行初始化
		if(chkInitDB.isSelected()){
			int dialog_result=MainFrameController.showConfirmDialog(I18nUtil.getString("INITDB.SURE"), I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
		    return dialog_result==0;
		}

		return true;
	}

	public void initialize(String[] parameters) {
	}

	private void loadSupportedDBSvr() {
		List list = ProductInstallConfigs.getSupportedDBSvrs();
		int i = 0;
		for (int j = list.size(); i < j; i++)
			this.cbxDb.addItem(list.get(i));
	}

	public void afterActions() {
	}

	public void actionPerformed(ActionEvent ae) {
	
	}
	
	
	
	private void refreshDBUrl() {
		this.tfdUrl.setText(" " + getDBUrl());
	}
	
	private String getDBUrl() {
		String strURL = "jdbc:mysql://" + this.tfdIP.getText() + ":" + this.tfdPort.getText() + "/" + this.tfdSID.getText();
		return strURL;
	}

	protected class TxtDocumentListener implements DocumentListener {
		protected TxtDocumentListener() {
		}

		public void insertUpdate(DocumentEvent evt) {
			ConfigMysqlPanel.this.refreshDBUrl();
		}

		public void removeUpdate(DocumentEvent evt) {
			ConfigMysqlPanel.this.refreshDBUrl();
		}

		public void changedUpdate(DocumentEvent evt) {
			ConfigMysqlPanel.this.refreshDBUrl();
		}
	}
	
	class EditorActionListener implements ActionListener {
		EditorActionListener() {
		}

		public void actionPerformed(ActionEvent ae) {
			Object source = ae.getSource();
		    if (chkInitDB == source) {
				ConfigMysqlPanel.this.resetTabSpaceText();
			}else if(chkInstall==source){
				ConfigMysqlPanel.this.refreshDBInfo();
				
			}else if (btnDBTest == source) {
				String message = ConfigMysqlPanel.this.testDBConnection();
				if (message != null)
					MainFrameController.showMessageDialog(message, I18nUtil.getString("DIALOG.TITLE.ERROR"), 0);
				else
					MainFrameController.showMessageDialog(I18nUtil.getString("DBCONFIG.MESSAGE.CONNECTIONOK"), I18nUtil.getString("DIALOG.TITLE.INFO"), 1);
			}
		}
	}

	
	public String testDBConnection() {
		
		String result = check();

		if (result != null) {
			return result;
		}

		int rtn = validateConn();

		if (rtn == 100)
			return null;
		if (rtn == -1)
			return I18nUtil.getString("DBCONFIG.MESSAGE.CONNECTIONFAIL");
		if (rtn == 0) {
			return I18nUtil.getString("DBCONFIG.MESSAGE.CANTCREATETBL");
		}
		return I18nUtil.getString("DBCONFIG.MESSAGE.CONNECTIONFAIL");
	}

	private int validateConn() {
		String jdbcUrl = this.tfdUrl.getText().trim();
		String user = this.tfdUser.getText();
		String password = new String(this.tfdPassword.getPassword());
		String jdbcDriverClass = null;
		String driverFiles = null;
		jdbcDriverClass ="com.mysql.jdbc.Driver";
		int rtn = DBConnectionUtil.validateDBConfig(driverFiles, jdbcDriverClass, jdbcUrl, user, password);
		return rtn;
	}

	private String check() {
		if ((StringUtils.isEmpty(this.tfdIP.getText()))
				|| (!ValidatorHelper.isPatternValid(this.tfdIP.getText(), "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$"))) {
			return I18nUtil.getString("DBCONFIG.MSG.IPNULL");
		}

		if (this.tfdUrl.getText().trim().length() == 0) {
			return I18nUtil.getString("DBCONFIG.MSG.URLNULL");
		}

		if (this.tfdUser.getText().trim().length() == 0) {
			return I18nUtil.getString("DBCONFIG.MSG.USERNULL");
		}

		String port = this.tfdPort.getText();
		if ((StringUtils.isEmpty(port)) || (StringUtils.isBlank(port))) {
			return I18nUtil.getString("CHOOSEIP.PORT.EMPTY");
		}
		if ((!ValidatorHelper.isInteger(port)) || ((Integer.valueOf(port).intValue() < 1) && (Integer.valueOf(port).intValue() > 65535))) {
			return I18nUtil.getString("CHOOSEIP.PORT.INVALID");
		}

		if (this.tfdSID.getText().trim().length() == 0) {
			return I18nUtil.getString("DBCONFIG.MSG.USERNAMENULL");
		}
		try {
			if ((Long.parseLong(this.tfdPort.getText().trim()) <= 0L) || (Long.parseLong(this.tfdPort.getText().trim()) > 65535L))
				return I18nUtil.getString("DBCONFIG.MSG.PORTERROR");
		} catch (Exception e) {
			return I18nUtil.getString("DBCONFIG.MSG.PORTVALUEERROR");
		}

		return null;
	}

	public void refreshDBInfo() {
		this.btnDBTest.setEnabled(!this.chkInstall.isSelected());
		if(this.chkInstall.isSelected()){
			this.tfdSID.setEnabled(false);
			this.tfdIP.setEnabled(false);
			this.tfdIP.setText("127.0.0.1");
			this.tfdPort.setEnabled(false);
			this.tfdPort.setText("3306");
			this.tfdUser.setEnabled(false);
			this.tfdUser.setText("root");
			this.tfdPassword.setEditable(false);
			this.tfdPassword.setText("");
		}else{
			this.tfdIP.setEnabled(true);
			this.tfdPort.setEnabled(true);
			this.tfdUser.setEnabled(true);
			this.tfdPassword.setEditable(true);
			this.tfdSID.setEnabled(true);
		}
	}


	public void resetTabSpaceText() {
		String db_type = getClass().getSimpleName().toLowerCase();

		this.lblTabSpace.setVisible(false);
		
	}

	
}
