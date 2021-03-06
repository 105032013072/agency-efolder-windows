package com.bosssoft.install.nontax.windows.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.ReflectUtil;
import com.bosssoft.platform.installer.wizard.cfg.ProductInstallConfigs;
import com.bosssoft.platform.installer.wizard.cfg.Server;
import com.bosssoft.platform.installer.wizard.gui.AbstractASEditorPanel;
import com.bosssoft.platform.installer.wizard.gui.AppSvrPanel;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;
import com.bosssoft.platform.installer.wizard.util.ServerUtil;

public class NontaxAppSvrPanel extends AbstractSetupPanel implements ActionListener {

	Logger logger = Logger.getLogger(getClass());

	protected StepTitleLabel line = new StepTitleLabel();

	protected JTextArea introduction = new JTextArea();

	protected JComboBox cbxAS = new JComboBox();

	protected AbstractASEditorPanel appsvrPanel = null;

	protected JLabel lblAppsvr = new JLabel();

	protected HashMap editorPanelMap = new HashMap();

	public NontaxAppSvrPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 初始化界面（读取支持的服务器列表：appsver-supported文件）
	void jbInit() throws Exception {
		setLayout(null);
		setOpaque(false);

		this.line.setText(I18nUtil.getString("STEP.APPSVR"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.introduction.setOpaque(false);
		this.introduction.setEditable(false);
		this.introduction.setLineWrap(true);
		this.introduction.setWrapStyleWord(true);
		this.introduction.setBounds(new Rectangle(37, 33, 375, 137));

		this.cbxAS.setBounds(new Rectangle(155, 178, 192, 20));
		loadSupportedAppsvr();
		this.cbxAS.addActionListener(this);

		this.lblAppsvr.setText(I18nUtil.getString("APPSVR.LABEL.AS"));
		this.lblAppsvr.setBounds(new Rectangle(37, 180, 110, 16));


		setOpaque(false);
		add(this.line, null);
		add(this.introduction, null);
		add(this.cbxAS, null);
		add(this.lblAppsvr, null);
	}

	private void loadSupportedAppsvr() {
		List list = ProductInstallConfigs.getSupportedAppSvrs();
		for (int i = 0; i < list.size(); i++)
			this.cbxAS.addItem(list.get(i));
	}

	public void afterShow() {
	}

	public void beforeNext() {
		if (this.appsvrPanel != null) {
			Properties properties = this.appsvrPanel.getProperties();
			getContext().putAll(properties);

			logger.info("set app server: " + properties);
		}
		Server s = (Server) this.cbxAS.getSelectedItem();

		getContext().setValue("APP_SERVER_TYPE", s.getType().concat(s.getVersion()));
		getContext().setValue("APP_SERVER_NAME", s.getType());
		getContext().setValue("APP_SERVER_VERSION", s.getVersion());

	     getContext().setValue("IS_CLUSTER", "false");

	}

	public void beforePrevious() {
	}

	public void beforeShow() {
		refreshSubPanel();
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonVisible("help", false);
		String edition = getContext().getStringValue("EDITION");
		if ("DE".equalsIgnoreCase(edition)) {
			this.cbxAS.setEnabled(false);
			this.introduction.setText(I18nUtil.getString("APPSVR.LABEL_DE"));
		} else if ("PE".equalsIgnoreCase(edition)) {
			this.introduction.setText(I18nUtil.getString("APPSVR.LABEL_PE"));
		} else if ("CE".equalsIgnoreCase(edition)) {
			this.introduction.setText(I18nUtil.getString("APPSVR.LABEL_CE"));
		}
	}

	public boolean checkInput() {
		String asType = this.cbxAS.getSelectedItem().toString();
		getContext().setValue("APP_SERVER_TYPE", asType);
		if ((this.appsvrPanel != null) && (!this.appsvrPanel.checkInput())) {
			return false;
		}

		Boolean isInstalled = (Boolean) getContext().getValue("IS_REPEAT_INSTALLED");
		if ((isInstalled != null) && (isInstalled.booleanValue())) {
			String appServerHome = getContext().getStringValue("appServerHome");
			File f1 = new File(this.appsvrPanel.getChooserAppSerHome());
			File f2 = new File(appServerHome);
			if (!f1.getAbsolutePath().equals(f2.getAbsolutePath())) {
				String msg = I18nUtil.getString("APPSVR.HOME.ERROR");
				msg = MessageFormat.format(msg, new Object[] { f2.getAbsolutePath() });
				MainFrameController.showMessageDialog(msg, I18nUtil.getString("DIALOG.TITLE.ERROR"), 0);
				return false;
			}
		}

		return checkEvn();
	}

	public void initialize(String[] parameters) {
	}

	private void clearAppsvrPanel() {
		if (this.appsvrPanel != null)
			remove(this.appsvrPanel);
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == this.cbxAS) {
			refreshSubPanel();
		}

	}

	public void afterActions() {
	}

	private void refreshSubPanel() {
		Server appSvr = (Server) this.cbxAS.getSelectedItem();
		

		clearAppsvrPanel();
		String appSvrName = appSvr.getName();

		if (this.editorPanelMap.containsKey(appSvrName)) {
			this.appsvrPanel = ((AbstractASEditorPanel) this.editorPanelMap.get(appSvrName));
		} else {
			String className = null;
			className = appSvr.getEditorPanel();
			if (className != null) {
				this.appsvrPanel = ((AbstractASEditorPanel) ReflectUtil.newInstanceBy(className));
				this.appsvrPanel.setParameter(appSvr.getVersion());
				this.appsvrPanel.setContext(getContext());
				this.editorPanelMap.put(appSvrName, this.appsvrPanel);
				this.appsvrPanel.setBounds(new Rectangle(37, 206, 373, 350));
			} else {
				this.appsvrPanel = null;
			}
		}
		if (this.appsvrPanel != null) {
			add(this.appsvrPanel, null);
			validate();
			repaint();
		}
	}

	public AbstractASEditorPanel getAppsvrPanel() {
		return appsvrPanel;
	}

	// 应用服务器端口冲突检测
	private boolean checkEvn() {
		String port = getAppsvrPanel().getAppSvrPort();
		List<Integer> ports = new ArrayList<Integer>();
		ports.add(Integer.valueOf(port));
		Set<Integer> pids = ServerUtil.searchProcess4Win(ports);
		if (pids.size() != 0) {

			int dialog_result = MainFrameController.showConfirmDialog(port + I18nUtil.getString("APPSVR.PORT.CONFLICT"),
					I18nUtil.getString("DIALOG.TITLE.WARNING"), 0, 2);
			if (dialog_result == 0) {
				ServerUtil.killWithPid4Win(pids);
			}
			return false;
		}
		return true;
	}
}
