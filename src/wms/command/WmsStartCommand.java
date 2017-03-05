package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import wms.plugin.AreaCheckController;
import wms.plugin.WelcomMessageSendPlugin;

public class WmsStartCommand extends AbstractWmsCommand implements WmsCommandInterface{

	public WmsStartCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(WelcomMessageSendPlugin.state == true){
			
			sender.sendMessage(ChatColor.GREEN+"WMSはすでに稼働しています。");
			
		}else if(WelcomMessageSendPlugin.state == false){
			
			sender.sendMessage(ChatColor.GREEN+"WMSを始動します。");
			
			WelcomMessageSendPlugin.state = true;
			
			AreaCheckController.areaCheckController();
			
		}
	}

}
