package wms.area.manager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import wms.plugin.WelcomMessageSendPlugin;

public class AreaDeleteManager {
	
	public static void areaDeleter(String trim, Player p){
		
		if(WelcomMessageSendPlugin.config.contains(trim)){
		
			WelcomMessageSendPlugin.config.set(trim, null);
			WelcomMessageSendPlugin.plugin.saveConfig();
		
			p.sendMessage(ChatColor.DARK_PURPLE+trim+"を削除しました。");
			
			WelcomMessageSendPlugin.plugin.saveConfig();
			
		}else{
			p.sendMessage(ChatColor.DARK_PURPLE+trim+"は存在しません。");
		}

	}
	
}
