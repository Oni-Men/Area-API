package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wms.area.manager.AreaDeleteManager;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaDeleteCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(args.length < 1){
			return false;
		}
		String areaName ="";
		String trim = WelcomMessageSendPlugin.bindString(areaName, args);
		
		AreaDeleteManager.areaDeleter(trim, (Player) sender);
		return true;
	}

}
