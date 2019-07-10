package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.wizard.gui.component.BlankIcon;

public class NoRenderListSetupPanel extends AbstractSetupPanel {
	private AbstractSetupPanel subPanel = null;


	public NoRenderListSetupPanel(String stepId, AbstractSetupPanel panel) {
		this.subPanel = panel;
		setLayout(null);
	}

	public void afterActions() {
		this.subPanel.afterActions();
	}

	public void beforeNext() {
		this.subPanel.beforeNext();
	}

	public void beforePrevious() {
		this.subPanel.beforePrevious();
	}

	public void beforeShow() {
		this.subPanel.beforeShow();
	}

	public boolean checkInput() {
		return this.subPanel.checkInput();
	}

	public void initialize(String[] parameters) {
		this.subPanel.initialize(parameters);
		this.subPanel.setBounds(new Rectangle(90, 0, 540, 392));
		add(this.subPanel);
	
	}

	public void setContext(IContext c) {
		super.setContext(c);
		this.subPanel.setContext(c);
	}
}