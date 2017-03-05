package wms;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import wms.command.AreaRegistCommand;
import wms.gui.AreasInfoGui;
import wms.gui.ManagementGui;


public class PlayerEventListener implements Listener {
	
	@EventHandler
	public void areaSelect(PlayerInteractEvent e){
		Player player = e.getPlayer();
		
		if(!(player.getGameMode() == GameMode.CREATIVE)){
			return;
		}
		if(!e.getPlayer().getItemInHand().equals(AreaRegistCommand.areaSelector)) {
			return;
		}
		if(e.getClickedBlock() == null){
			return;
		}
		
		e.setCancelled(true);
		
		Location location = e.getClickedBlock().getLocation();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			AreaRegistCommand.locMap2.put(player, location);
			player.sendMessage(ChatColor.GREEN+"2つ目の場所を指定しました。");
			return;
		}
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			AreaRegistCommand.locMap1.put(player, location);
			player.sendMessage(ChatColor.GREEN+"1つ目の場所を指定しました。");
			return;
			
		}
	}
	@EventHandler
	public void dropAreaSelector(PlayerDropItemEvent e){
		Item itemDrop = e.getItemDrop();
		if(itemDrop.getItemStack().equals(AreaRegistCommand.areaSelector)){
			itemDrop.remove();
		}
	}
	@EventHandler
	public void clickInventory(InventoryClickEvent e){
		
		ItemStack currentItem = e.getCurrentItem();
		HumanEntity whoClicked = e.getWhoClicked();
		
		if(e.getClickedInventory().equals(ManagementGui.managementGui)){
			
			ManagementGui.invClick(currentItem, whoClicked);
			
			e.setCancelled(true);
			return;
		}
		if(e.getInventory().getName().equals(AreasInfoGui.area_info)){
			
			AreasInfoGui.clickAreaInfo(currentItem, whoClicked);
			
			e.setCancelled(true);
			return;
		}
	}

}
