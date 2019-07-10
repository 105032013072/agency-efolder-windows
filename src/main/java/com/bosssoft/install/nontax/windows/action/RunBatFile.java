package com.bosssoft.install.nontax.windows.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.message.MessageManager;

public class RunBatFile implements IAction{

	public void execute(IContext context, Map params) throws InstallException {
		String runFile=context.getStringValue("runFile");
		//String runFile="F:/Bosssoft/Platform/Nontax/test.bat";
		StringBuffer stgrBuffer=new StringBuffer();
		try {
	          //执行命令  
			 Process  p = Runtime.getRuntime().exec("cmd.exe  /C   start   /b  "+runFile);
	         
	          //取得命令结果的输出流
	          InputStream fis=p.getInputStream();
	          //用一个读输出流类去读
	          InputStreamReader isr=new InputStreamReader(p.getInputStream(), Charset.forName("GBK"));
	          //用缓冲器读行
	          BufferedReader br=new BufferedReader(isr);
	          String line=null;
	          //直到读完为止
	          while((line=br.readLine())!=null) {
	              stgrBuffer.append(line).append(System.lineSeparator());
	              MessageManager.syncSendMessage(stgrBuffer.toString());
	              if(line.equals("start success")) break;
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		// TODO Auto-generated method stub
		
	}
   
}
