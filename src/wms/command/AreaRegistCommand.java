package wms.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import wms.area.NormalArea;
import wms.area.manager.AreaRegistManager;
import wms.plugin.WelcomMessageSendPlugin;


public class AreaRegistCommand implements CommandExecutor{

	public static HashMap<Player, Location> locMap1 = new HashMap<>();
	public static HashMap<Player, Location> locMap2 = new HashMap<>();
	public static ItemStack areaSelector = new ItemStack(Material.QUARTZ);
	static{
		ItemMeta itemMeta = areaSelector.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN+"エリアセレクター");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.LIGHT_PURPLE+"ブロックを右クリックすると1つ目の場所を指定、");
		lore.add(ChatColor.LIGHT_PURPLE+"ブロックを左クリックすると2つ目の場所を指定できます。");
		lore.add(ChatColor.LIGHT_PURPLE+"/arearegist [Area Name]で指定範囲を登録できます。");
		itemMeta.setLore(lore);
		itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
		areaSelector.setItemMeta(itemMeta);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(!(sender instanceof Player)){
			return false;
		}
		if(args.length == 0 && !((Player)sender).getInventory().contains(areaSelector)){
			((Player)sender).getInventory().addItem(areaSelector);
			return true;
		}
		String areaName = "";
		String bindString = WelcomMessageSendPlugin.bindString(areaName, args);
		String[] split = bindString.split("&s");
		
		if(!locMap1.containsKey(sender)){
			sender.sendMessage(ChatColor.RED+"1つ目の場所が指定されていません。");
			return false;
		}
		if(!locMap2.containsKey(sender)){
			sender.sendMessage(ChatColor.RED+"2つ目の場所が指定されていません。");
			return false;
		}
		
		if(split.length != 2){sender.sendMessage(ChatColor.RED+"指定した値は不正です。");return false;}
		if(split[0] == null || split[0].isEmpty()){sender.sendMessage(ChatColor.RED+"エリアの名前が不正です。");return false;}
		if(split[1] == null || split[1].isEmpty()){sender.sendMessage(ChatColor.RED+"サブタイトルが不正です。");return false;}
			
		NormalArea area = new NormalArea(split[0], split[1], locMap1.get(sender),  locMap2.get(sender));
		
		AreaRegistManager.areaRegister(area, (Player) sender);
		
		((Player)sender).getInventory().remove(areaSelector);
		
		return true;
	}

}
