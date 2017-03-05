package wms.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ManagementGui {
	
	public static String manage_gui = ChatColor.GREEN+"Management GUI";
	
	public static Inventory managementGui = Bukkit.createInventory(null, 9*3,manage_gui);
	
	public static ItemStack areainfo_item = new ItemStack(Material.BOOK);
	static{
		ItemMeta areainfo_item_meta = areainfo_item.getItemMeta();
		areainfo_item_meta.setDisplayName(ChatColor.GREEN+"Areas Information");
		areainfo_item.setItemMeta(areainfo_item_meta);
	}
	public static ItemStack wms_enable = new ItemStack(Material.WOOL);
	static{
		ItemMeta wms_enable_meta = wms_enable.getItemMeta();
		wms_enable_meta.setDisplayName(ChatColor.GREEN+"WMS START");
		wms_enable.setItemMeta(wms_enable_meta);
		wms_enable.setDurability((short) 5);
		
	}
	public static ItemStack wms_disable = new ItemStack(Material.WOOL);
	static{
		ItemMeta wms_disable_meta = wms_disable.getItemMeta();
		wms_disable_meta.setDisplayName(ChatColor.GREEN+"WMS STOP");
		wms_disable.setItemMeta(wms_disable_meta);
		wms_disable.setDurability((short) 8);
		
	}
	public static ItemStack wms_reload = new ItemStack(Material.PISTON_BASE);
	static{
		ItemMeta wms_reload_meta = wms_reload.getItemMeta();
		wms_reload_meta.setDisplayName(ChatColor.GREEN+"WMS RELOAD");
		wms_reload.setItemMeta(wms_reload_meta);
		
	}
	

	public static void manageGui(Player p){
		 
		managementGui.setItem(10, areainfo_item);
		managementGui.setItem(12, wms_enable);
		managementGui.setItem(14, wms_disable);
		managementGui.setItem(16, wms_reload);
		
		p.openInventory(managementGui);
	}
	
	public static void invClick(ItemStack item, HumanEntity p){
		
	}
	
	
}
