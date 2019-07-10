package com.bosssoft.install.nontax.windows.gui;

import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.option.ResourceDef;
import com.bosssoft.platform.installer.core.option.ResourceDefHelper;
import com.bosssoft.platform.installer.core.util.ExpressionParser;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.IntroductionPanel;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;
import com.bosssoft.platform.installer.wizard.util.ServerUtil;
import com.bosssoft.platform.license.api.Constant;
import com.bosssoft.platform.license.api.License;
import com.bosssoft.platform.license.api.LicenseContext;
import com.bosssoft.platform.license.generator.SignGenerator;
import com.bosssoft.platform.license.impl.LicenseParser;
import com.bosssoft.platform.license.impl.LicenseUtil;

public class InstallTypepanel extends AbstractSetupPanel{
	private StepTitleLabel line = new StepTitleLabel();

	private JTextArea introduction = new JTextArea();
	
	private JRadioButton rbtnDefault = new JRadioButton();
	private JTextArea defaultexplain = new JTextArea();
	
	
	private JRadioButton rbtnCustomize = new JRadioButton();
	private JTextArea customizeexplain = new JTextArea();
	
	private ButtonGroup buttonGroup = new ButtonGroup();

	public InstallTypepanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	void jbInit() throws Exception {
		setLayout(null);

		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.line.setText(I18nUtil.getString("STEP.INSTALLTYPE"));

		this.introduction.setOpaque(false);
		this.introduction.setText(I18nUtil.getString("INSTALLTYPE.INTRODUCTION"));
		this.introduction.setLineWrap(true);
		this.introduction.setRows(1);
		this.introduction.setEditable(false);
		this.introduction.setWrapStyleWord(true);
		this.introduction.setBounds(new Rectangle(80, 43, 375, 184));
		
		this.rbtnDefault.setText(I18nUtil.getString("INSTALLTYPE.DEFAULT"));
		this.rbtnDefault.setFont(new Font("宋体",Font.BOLD, 12));
		this.rbtnDefault.setOpaque(false);
		this.rbtnDefault.setBounds(new Rectangle(80, 70, 372, 25));
		this.rbtnDefault.setSelected(true);
		
		this.defaultexplain.setText(I18nUtil.getString("INSTALLTYPE.DEFAULT.EXPLAIN"));
		this.defaultexplain.setOpaque(false);
		this.defaultexplain.setBounds(new Rectangle(80, 105, 372, 80));
		
		
		this.rbtnCustomize.setBounds(new Rectangle(80, 243, 372, 30));
		this.rbtnCustomize.setFont(new Font("宋体",Font.BOLD, 12));
		this.rbtnCustomize.setText(I18nUtil.getString("INSTALLTYPE.CUSTOMIZE"));
		this.rbtnCustomize.setOpaque(false);
		
		this.customizeexplain.setText(I18nUtil.getString("INSTALLTYPE.CUSTOMIZE.EXPLAIN"));
		this.customizeexplain.setOpaque(false);
		this.customizeexplain.setBounds(new Rectangle(82, 285, 372, 30));
		
		
		this.buttonGroup.add(this.rbtnDefault);
		this.buttonGroup.add(this.rbtnCustomize);

		add(this.line, null);
		add(this.rbtnCustomize,null);
		add(this.rbtnDefault,null);
		add(this.defaultexplain,null);
		add(this.customizeexplain,null);
		setOpaque(false);

		add(this.introduction, null);
	}
	
	public void afterShow() {
		
	}

	public void beforeNext() {
	  //记录安装模式
	  if(this.rbtnCustomize.isSelected()){
		  getContext().setValue("IS_DEFAULT_INSTALL", "false");//自定义安装
	  }else{
		  getContext().setValue("IS_DEFAULT_INSTALL", "true");//默认安装
	  }
	}
	

	public void beforePrevious() {
	}

	public void beforeShow() {
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("finish", false);
		controlPane.setButtonVisible("help", false);
		
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
	}
}
