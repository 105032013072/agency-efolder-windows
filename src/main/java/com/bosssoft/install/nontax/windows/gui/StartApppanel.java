package com.bosssoft.install.nontax.windows.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bosssoft.platform.common.utils.StringUtils;
import com.bosssoft.platform.installer.core.MainFrameController;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.message.IProgressReceiver;
import com.bosssoft.platform.installer.core.message.MessageManager;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.wizard.gui.InstallingPanel;
import com.bosssoft.platform.installer.wizard.gui.IntroductionPanel;
import com.bosssoft.platform.installer.wizard.gui.component.StepTitleLabel;

public class StartApppanel extends AbstractSetupPanel implements IProgressReceiver{

	private StepTitleLabel line = new StepTitleLabel();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel setupPane = new JPanel();

	private JTextArea runLabel = new JTextArea();
	
	private JLabel lblstarting = new JLabel();
	
	private JEditorPane txtRun = new JEditorPane();

	private JScrollPane jScrollPane1 = new JScrollPane();
	
	public StartApppanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void jbInit() {
		MessageManager.registe(this);
		setLayout(this.borderLayout1);
		setOpaque(false);

		this.setupPane.setLayout(null);
		this.line.setText(I18nUtil.getString("STEP.STARTAPP"));
		this.line.setBounds(new Rectangle(26, 5, 581, 27));

		this.lblstarting.setText(I18nUtil.getString("START.LABEL.STARTING"));
		this.lblstarting.setBounds(new Rectangle(37, 35, 200, 16));
		
		this.jScrollPane1.getViewport().add(this.txtRun, null);
		this.runLabel.setOpaque(false);
		this.runLabel.setEditable(false);
		this.runLabel.setBounds(new Rectangle(37, 35, 373, 33));
		this.jScrollPane1.setBounds(new Rectangle(36, 68, 410, 260));
		this.jScrollPane1.setOpaque(false);
		this.txtRun.setEditable(false);
		this.txtRun.setOpaque(false);
		
		add(this.setupPane, "Center");
		this.setupPane.setOpaque(false);
		this.setupPane.add(this.line, null);
		this.setupPane.add(this.runLabel, null);
		this.setupPane.add(this.jScrollPane1, null);
	    this.setupPane.add(this.lblstarting,null);
		
	}
	
	@Override
	public void initialize(String[] paramArrayOfString) {
		
		
	}

	@Override
	public void beforeShow() {
		
		AbstractControlPanel controlPane = MainFrameController.getControlPanel();
		controlPane.setButtonVisible("help", false);
		controlPane.setButtonVisible("next", false);
		controlPane.setButtonVisible("cancel", false);
		controlPane.setButtonVisible("previous", false);
		
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

	@Override
	public void afterActions() {
		// TODO Auto-generated method stub
		
	}
	
	
	public class ProgressBarAutoRefresh extends Thread {
		public ProgressBarAutoRefresh() {
		}

		public void run() {
			System.out.println("123");
		}
	}


	public void messageChanged(String message) {
	    if(StringUtils.isNotNullAndBlank(message)){
	    	this.txtRun.setText(message);
			this.txtRun.repaint();
			/*if(message.contains("start success")){
				AbstractControlPanel controlPane = MainFrameController.getControlPanel();
				controlPane.setButtonEnabled("finish", true);
				controlPane.repaint();
			}*/
	    }
	}

	public void beginWork(String message, int count) {
		// TODO Auto-generated method stub
		
	}

	public void worked(String message, int count) {
		// TODO Auto-generated method stub
		
	}

	public void worked(int count) {
		// TODO Auto-generated method stub
		
	}

}
