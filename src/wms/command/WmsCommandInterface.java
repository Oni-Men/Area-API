package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface WmsCommandInterface {
	
	String cmdName();
	
	void onCmd(CommandSender sender, Command cmd, String s, String[] args);
	
}
