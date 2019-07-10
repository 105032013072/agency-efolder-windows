package com.bosssoft.install.nontax.windows.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.runtime.InstallRuntime;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;

/**
 * 启动完成
 * @author huangxw
 *
 */
public class NontaxRunFinishedPanel extends AbstractSetupPanel{
	Logger logger = Logger.getLogger(getClass());
	private StepTitleLabel line = new StepTitleLabel();
	private JTextArea taIntroduction = new JTextArea();
	private JCheckBox chkReadme;
	private JLabel lblRun = new JLabel();
	private JTextArea taRunValue = new JTextArea();
	private JLabel lblReadme;

	public NontaxRunFinishedPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		setLayout(null);
		setOpaque(false);
		this.line.setText(I18nUtil.getString("STEP.RUNCOMPLETE"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.taIntroduction.setOpaque(false);
		this.taIntroduction.setText(I18nUtil.getString("START.SUCCESS"));
		this.taIntroduction.setRows(1);
		this.taIntroduction.setLineWrap(true);
		this.taIntroduction.setWrapStyleWord(true);
		this.taIntroduction.setBounds(new Rectangle(37, 43, 375, 41));
		this.taIntroduction.setEditable(false);
//		String osName = System.getProperty("os.name").toLowerCase();
//		if (osName.indexOf("window") >= 0) {
//			this.chkReadme = new JCheckBox();
//			this.chkReadme.setText(I18nUtil.getString("FINISH.LABEL.REAME"));
//			this.chkReadme.setOpaque(false);
//			this.chkReadme.setBounds(new Rectangle(37, 300, 375, 25));
//			if (isCluster())
//				this.chkReadme.setSelected(false);
//			else
//				add(this.chkReadme, null);
//		} else {
//			this.lblReadme = new JLabel();
//			this.lblReadme.setText(I18nUtil.getString("FINISH.LABEL.REAME2"));
//			this.lblReadme.setOpaque(false);
//			this.lblReadme.setBounds(new Rectangle(37, 300, 375, 25));
//			add(this.lblReadme, null);
//		}
		
		
		
		this.lblRun.setPreferredSize(new Dimension(50, 16));
		this.lblRun.setBounds(new Rectangle(37, 80, 370, 16));

		this.taRunValue.setLineWrap(true);
		this.taRunValue.setOpaque(false);
		this.taRunValue.setBounds(new Rectangle(37, 99, 370, 41));
		this.taRunValue.setEditable(false);
		setOpaque(false);
		add(this.line, null);
		add(this.taIntroduction, null);
		add(this.lblRun, null);
		add(this.taRunValue, null);
	}

	private boolean isCluster() {
		boolean r = false;
		String isCluster = InstallRuntime.INSTANCE.getContext().getStringValue("IS_CLUSTER");
		r = (isCluster != null) && (isCluster.equalsIgnoreCase("true"));
		return r;
	}
	
	public void afterShow() {
	}

	public void beforeNext() {
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonVisible("help", false);
	}

	public void beforePrevious() {
	}

	public void beforeShow() {
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("finish", true);
		controlPane.setButtonVisible("help", false);
		controlPane.setButtonVisible("next", false);
		controlPane.setButtonVisible("cancel", false);
		controlPane.setButtonVisible("previous", false);
		controlPane.setDefaultButton("finish");
		
//		String osName = System.getProperty("os.name").toLowerCase();
//		if (osName.indexOf("window") >= 0) {
//			this.chkReadme.setVisible(true);
//			this.taRunValue.setText(getContext().getStringValue("STARTMENU_PATH").replace('/', '\\') + getContext().getStringValue("SHORTCUT_GROUP").replace('/', '\\'));
//			this.lblRun.setText(I18nUtil.getString("FINISH.LABEL.RUN"));
//			this.lblRun.setVisible(true);
//			this.taRunValue.setVisible(true);
//		} else if (this.chkReadme != null) {
//			this.chkReadme.setVisible(false);
//		}
	}

	public boolean checkInput() {
		return true;
	}

	public String getNextBranchID() {
		return "";
	}

	public void initialize(String[] parameters) {
	}

	public void afterActions() {
//		if ((this.chkReadme != null) && (!this.chkReadme.isSelected())) {
//			return;
//		}
//		String installDir = getContext().getStringValue("INSTALL_DIR");
//		String readmePath = getContext().getStringValue("BOSSSOFT_HOME");;
//		if (Locale.getDefault().toString().equals("zh_CN"))
//			readmePath = readmePath + "/front-app-info.txt";
//		else {
//			readmePath = readmePath + "/front-app-info.txt";
//		}
//		String[] cmds = { "notepad", readmePath };
//		try {
//			Runtime.getRuntime().exec(cmds, null, null);
//		} catch (IOException localIOException) {
//		}
	}

	private String treatLicenseInfo() {
		return null;
	}
}
