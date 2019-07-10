package com.bosssoft.install.nontax.windows.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bosssoft.platform.installer.core.cfg.Step;
import com.bosssoft.platform.installer.core.gui.AbstractControlPanel;
import com.bosssoft.platform.installer.core.gui.AbstractSetupPanel;
import com.bosssoft.platform.installer.core.gui.IRenderer;
import com.bosssoft.platform.installer.core.util.I18nUtil;
import com.bosssoft.platform.installer.core.util.InstallerFileManager;
import com.bosssoft.platform.installer.core.util.ReflectUtil;
import com.bosssoft.platform.installer.wizard.gui.StepListSetupPanel;
import com.bosssoft.platform.installer.wizard.gui.StepListRenderer.StepItem;

public class NontaxStepListRenderer implements IRenderer{
	
	public AbstractControlPanel getControlPanel(Step step) {
		if (step.getControlPanelClassName() == null)
			return null;
		String clazzName = step.getControlPanelClassName();
		Object instance = ReflectUtil.newInstanceBy(clazzName);
		AbstractControlPanel panel = null;
		if (instance == null) {
			return null;
		}
		panel = (AbstractControlPanel) instance;

		return panel;
	}

	public AbstractSetupPanel getSetupPanel(Step step) {
		AbstractSetupPanel panel = null;
		if (step.getSetupPanelClassName() == null)
			return null;
		String clazzName = step.getSetupPanelClassName();
		Object instance = ReflectUtil.newInstanceBy(clazzName);

		if (instance == null) {
			return null;
		}
		panel = (AbstractSetupPanel) instance;
		AbstractSetupPanel spanel = new NoRenderListSetupPanel(step.getID(), panel);
		
		return spanel;
	}
}
