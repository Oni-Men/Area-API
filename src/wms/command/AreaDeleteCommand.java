package wms.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wms.area.manager.AreaDeleteManager;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaDeleteCommand  extends AbstractWmsCommand implements WmsCommandInterface{

	public AreaDeleteCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(args.length == 0){
			sender.sendMessage(ChatColor.RED+"値を入力してください。");
			return;
		}
		String areaName ="";
		String trim = WelcomMessageSendPlugin.bindString(areaName, args);
		
		AreaDeleteManager.areaDeleter(trim, (Player) sender);
	}

}
