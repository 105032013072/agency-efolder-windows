package com.bosssoft.install.nontax.windows.action;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bosssoft.platform.installer.core.IContext;
import com.bosssoft.platform.installer.core.InstallException;
import com.bosssoft.platform.installer.core.action.IAction;
import com.bosssoft.platform.installer.core.util.StringUtil;

public class GetIp implements IAction{
	transient Logger logger = Logger.getLogger(getClass());
	
	public void execute(IContext context, Map params) throws InstallException {
        
		String ipaddress=getLocalIp();
	    if(StringUtil.isNullOrBlank(ipaddress)) ipaddress="127.0.0.1";
	    context.setValue("IP", ipaddress);
	}

	private String getLocalIp() {
		String ipaddress=null;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
	
			while(netInterfaces.hasMoreElements()){
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> netAddresses = ni.getInetAddresses();
				while(netAddresses.hasMoreElements()){
					InetAddress ip =  netAddresses.nextElement();
					if ((ip != null) && (!ip.isLoopbackAddress()) && (ip.getHostAddress().indexOf(":") == -1)) {
						String address = ip.getHostAddress();
						return address;
					}
				}
			}
		} catch (Exception localException) {
			logger.error(localException);
		}
		return ipaddress;
		
	}

	public void rollback(IContext context, Map params) throws InstallException {
		
		
	}
	
	

}
