package wms;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import wms.command.AreaRegistCommand;


public class PlayerEventListener implements Listener{
	
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

}
