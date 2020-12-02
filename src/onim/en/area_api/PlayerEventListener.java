package onim.en.area_api;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.builder.AreaBuilder;
import onim.en.area_api.area.builder.AreaBuilderManager;
import onim.en.area_api.area.model.AbstractArea;
import onim.en.area_api.event.PlayerEnterAreaEvent;
import onim.en.area_api.event.PlayerLeaveAreaEvent;

public class PlayerEventListener implements Listener {

  @EventHandler
  public void onEnterArea(PlayerEnterAreaEvent event) {
    Player player = event.getPlayer();
    AreaModel area = event.getAreaEntered();
    player.sendTitle(area.getDecoratedName(), area.getDecoratedMessage(), 10, 60, 20);
  }

  @EventHandler
  public void onLeaveArea(PlayerLeaveAreaEvent event) {
    Player player = event.getPlayer();
    player.sendMessage(ChatColor.GREEN + "You are in wilderness now.");
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    AreaBuilderManager.unregister(player.getUniqueId());
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();
    if (block == null)
      return;

    AreaBuilder<? extends AbstractArea> builder = AreaBuilderManager.getBuilderByPlayer(event.getPlayer());

    if (builder == null)
      return;

    event.setCancelled(true);

    switch (event.getAction()) {
    case LEFT_CLICK_BLOCK:
      builder.onLeftClick(block);
      break;
    case RIGHT_CLICK_BLOCK:
      builder.onRightClick(block);
      break;
    default:
      break;
    }
  }
}
