package wms.command;

import wms.plugin.WelcomMessageSendPlugin;

public class CommandRegister {
	
	public static void registCommand(){
		WelcomMessageSendPlugin.plugin.getCommand("arearegist").setExecutor(new AreaRegistCommand());
		WelcomMessageSendPlugin.plugin.getCommand("areadelete").setExecutor(new AreaDeleteCommand());
		WelcomMessageSendPlugin.plugin.getCommand("areainfo").setExecutor(new AreaInfoCommand());
	}
}
