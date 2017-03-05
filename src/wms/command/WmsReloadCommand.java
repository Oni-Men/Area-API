package wms.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import wms.AreaLoader;
import wms.plugin.AreaCheckController;
import wms.plugin.WelcomMessageSendPlugin;

public class WmsReloadCommand extends AbstractWmsCommand implements WmsCommandInterface{

	public WmsReloadCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		WelcomMessageSendPlugin.state = false;
		
		sender.sendMessage(ChatColor.GREEN+"3秒後にWMSを再開しまし。");
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				AreaLoader.areaLoader();
				
				WelcomMessageSendPlugin.state = true;
				
				AreaCheckController.areaCheckController();
				
			}
		}.runTaskLater(WelcomMessageSendPlugin.plugin, 20*3);
		
	}

}
