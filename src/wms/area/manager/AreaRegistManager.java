package wms.area.manager;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import wms.area.NormalArea;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaRegistManager {
	public static void areaRegister(NormalArea area, Player p){
		
		String areaName = area.getAreaName();
		String areaSubTitle = area.getAreaSubTitle();
		List<Location> locs = area.getLocations();
		
		List<Object> areaList = new ArrayList<>();
		
		areaList.add(areaName);
		areaList.add(areaSubTitle);
		areaList.add(locs);
		
		WelcomMessageSendPlugin.config.set(areaName, areaList);
		WelcomMessageSendPlugin.plugin.saveConfig();
		
		p.sendMessage(ChatColor.GREEN+area.getAreaName()+"を登録しました。");
	}
}
