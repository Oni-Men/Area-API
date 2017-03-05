package wms.command;

import wms.plugin.WelcomMessageSendPlugin;

public class CommandRegister {
	
	public static void registCommand(){
		WelcomMessageSendPlugin.plugin.getCommand("wms").setExecutor(new WmsCommandManager());
		
		WmsCommandManager.wmsCommands.put("info", new AreaInfoCommand("info"));
		WmsCommandManager.wmsCommands.put("regist", new AreaRegistCommand("regist"));
		WmsCommandManager.wmsCommands.put("delete", new AreaDeleteCommand("delete"));
		WmsCommandManager.wmsCommands.put("start", new WmsStartCommand("start"));
		WmsCommandManager.wmsCommands.put("stop", new WmsStopCommand("stop"));
		WmsCommandManager.wmsCommands.put("reload", new WmsReloadCommand("reload"));
		WmsCommandManager.wmsCommands.put("gui", new WmsGuiCommand("gui"));
	}
}
