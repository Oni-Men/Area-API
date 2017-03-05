package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import wms.plugin.WelcomMessageSendPlugin;

public class WmsStopCommand extends AbstractWmsCommand implements WmsCommandInterface{

	public WmsStopCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(WelcomMessageSendPlugin.state == false){
			
			sender.sendMessage(ChatColor.GREEN+"WMSはすでに停止しています。");
			
		}else if(WelcomMessageSendPlugin.state == true){
			
			sender.sendMessage(ChatColor.GREEN+"WMSを停止します。");
			
			WelcomMessageSendPlugin.state = false;
			
		}
	}

}
