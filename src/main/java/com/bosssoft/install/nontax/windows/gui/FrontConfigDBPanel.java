package com.bosssoft.install.nontax.windows.gui;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.wizard.gui.ConfigDBPanel;

public class FrontConfigDBPanel extends ConfigDBPanel{
   
	private JScrollPane jScrollPane ;
	
	public FrontConfigDBPanel(){
		super();
		jScrollPane= new JScrollPane();
		this.jScrollPane.getViewport().add(explain, null);
		this.jScrollPane.setBounds(new Rectangle(27, 265, 382, 110));
		this.jScrollPane.setOpaque(false);
		
		
		JScrollBar jsVB = jScrollPane.getVerticalScrollBar();
		add(this.jScrollPane,null);
	}
	
	public void beforeShow(){
		super.beforeShow();
		this.explain.setCaretPosition(0);
	}
}
