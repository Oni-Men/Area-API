package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wms.gui.ManagementGui;

public class WmsGuiCommand extends AbstractWmsCommand implements WmsCommandInterface{

	public WmsGuiCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(!(sender instanceof Player)){
			return;
		}
		Player player = (Player) sender;
		ManagementGui.manageGui(player);
	}

}
