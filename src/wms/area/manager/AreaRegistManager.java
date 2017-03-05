package wms.area.manager;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import wms.area.NormalArea;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaRegistManager {
	public static void areaRegister(NormalArea area, Player p){
		
		String areaName = area.getAreaName();
		String areaSubTitle = area.getAreaSubTitle();
		HashSet<Chunk> chunks = area.getChunks();
		
		List<Object> areaList = new ArrayList<>();
		
		areaList.add(areaName);
		areaList.add(areaSubTitle);
		areaList.add(chunks);
		
		WelcomMessageSendPlugin.config.set(areaName, areaList);
		WelcomMessageSendPlugin.plugin.saveConfig();
		
		p.sendMessage(ChatColor.GREEN+area.getAreaName()+"を登録しました。");
	}
}
