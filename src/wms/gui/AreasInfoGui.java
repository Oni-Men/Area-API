package wms.gui;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import wms.area.manager.AreaInformationManager;
import wms.plugin.WelcomMessageSendPlugin;

public class AreasInfoGui {
	
	public static HashMap<HumanEntity,Integer> pageMap = new HashMap<>();
	
	public static String area_info = ChatColor.GREEN+"Areas Information";
	
	public static Inventory areasInfo = Bukkit.createInventory(null, 9*4,area_info);
	
	public static ItemStack next_pages = new ItemStack(Material.PAPER);
	static{
		ItemMeta pages_meta = next_pages.getItemMeta();
		pages_meta.setDisplayName(ChatColor.GREEN+"次のページ");
		next_pages.setItemMeta(pages_meta);
	}
	public static ItemStack back_pages = new ItemStack(Material.PAPER);
	static{
		ItemMeta pages_meta2 = back_pages.getItemMeta();
		pages_meta2.setDisplayName(ChatColor.GREEN+"前ののページ");
		back_pages.setItemMeta(pages_meta2);
	}
	
	public static void loadAreaInfos(){
		List<List<?>> divided = WelcomMessageSendPlugin.listDivider(AreaInformationManager.getAreaList(), 9);
		for(Object d:divided.get(0)){
			((NormalArea)d)
		}
	}
	public static void clickAreaInfo(ItemStack item, HumanEntity p){
		
	}
	
}
