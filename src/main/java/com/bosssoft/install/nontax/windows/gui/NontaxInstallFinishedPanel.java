package com.bosssoft.install.nontax.windows.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.runtime.InstallRuntime;
import com.bosssoft.platform.installer.core.util.ExpressionParser;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.core.util.ShortCutUtil;
import com.bosssoft.platform.installer.io.FileUtils;
import com.bosssoft.platform.installer.io.operation.exception.OperationException;
import com.bosssoft.platform.installer.wizard.gui.InstallFinishedPanel;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;

public class NontaxInstallFinishedPanel extends AbstractSetupPanel{
	private JCheckBox chkshortcut=new JCheckBox();
	
	Logger logger = Logger.getLogger(getClass());
	private StepTitleLabel line = new StepTitleLabel();
	private JTextArea taIntroduction1 = new JTextArea();
	private JLabel lblRun = new JLabel();
	private JTextArea taRunValue = new JTextArea();
	private JTextArea taIntroduction2 = new JTextArea();
	
	public NontaxInstallFinishedPanel(){
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() {
		setLayout(null);
		setOpaque(false);
		this.line.setText(I18nUtil.getString("STEP.INSTALLCOMPLETE"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.taIntroduction1.setOpaque(false);
		this.taIntroduction1.setText("Loading introduction......");
		this.taIntroduction1.setRows(1);
		this.taIntroduction1.setLineWrap(true);
		this.taIntroduction1.setWrapStyleWord(true);
		this.taIntroduction1.setBounds(new Rectangle(37, 43, 375, 41));
		this.taIntroduction1.setEditable(false);

		this.lblRun.setPreferredSize(new Dimension(50, 16));
		this.lblRun.setBounds(new Rectangle(37, 80, 370, 16));

		this.taRunValue.setLineWrap(true);
		this.taRunValue.setOpaque(false);
		this.taRunValue.setBounds(new Rectangle(37, 99, 370, 41));
		this.taRunValue.setEditable(false);
		this.taIntroduction2.setOpaque(false);
		this.taIntroduction2.setLineWrap(true);
		this.taIntroduction2.setWrapStyleWord(true);
		this.taIntroduction2.setBounds(new Rectangle(37, 137, 370, 125));
		this.taIntroduction2.setEditable(false);
		setOpaque(false);
		add(this.line, null);
		add(this.taIntroduction1, null);
		add(this.lblRun, null);
		add(this.taRunValue, null);
		add(this.taIntroduction2, null);
		
		
		/*this.chkshortcut.setText("创建桌面快捷键");
		this.chkshortcut.setOpaque(false);
		this.chkshortcut.setBounds(new Rectangle(37, 270, 375, 25));
		this.chkshortcut.setSelected(true);
		add(this.chkshortcut,null);*/
	}
	
	public void beforeShow() {	
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("run",false);
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonText("next", I18nUtil.getString("BUTTON_START"));
		controlPane.setButtonVisible("help", false);
		//controlPane.setButtonVisible("next", false);
		controlPane.setButtonVisible("previous", false);
		//controlPane.setDefaultButton("run");
		
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("window") >= 0) {
			this.taRunValue.setText(getContext().getStringValue("STARTMENU_PATH").replace('/', '\\') + getContext().getStringValue("SHORTCUT_GROUP").replace('/', '\\'));
			this.lblRun.setText(I18nUtil.getString("FINISH.LABEL.RUN"));
			this.lblRun.setVisible(true);
			this.taRunValue.setVisible(true);
		} 

		this.taIntroduction1.setText(getFinishLabel());

		String addtionInfo = treatLicenseInfo();

		this.taIntroduction2.setText(addtionInfo);
		
	}
	
	
	
	private String getFinishLabel() {
		String introduction = I18nUtil.getString("FINISH.LABEL");
		String productName = I18nUtil.getString("PRODUCT." + getContext().getStringValue("EDITION").toUpperCase());

		return introduction;
	}
	
	public void afterActions(){
		//桌面快捷键
	    String icoDir=ExpressionParser.parseString("${INSTALL_DIR}")+File.separator+"image";
		String desktop=FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		String ico=ico=icoDir+File.separator+"efolder.ico";
		ShortCutUtil.createUrlShortcut(I18nUtil.getString("SHORTCUT.DIR"),ExpressionParser.parseString(getContext().getStringValue("URL")) , ico, desktop);
	}

	@Override
	public void initialize(String[] paramArrayOfString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void beforePrevious() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNext() {
		// TODO Auto-generated method stub
		
	}
	
	private String treatLicenseInfo() {
		return null;
	}
}
