package onim.en.area_api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import onim.en.area_api.area.AreaModel;

public class PlayerEnterAreaEvent extends PlayerEvent {

  private static final HandlerList handlers = new HandlerList();

  private final AreaModel area;

  public PlayerEnterAreaEvent(Player who, AreaModel area) {
    super(who);
    this.area = area;
  }

  @Override
  public String getEventName() {
    return "PlayerEnterAreaEvent";
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public AreaModel getAreaEntered() {
    return this.area;
  }
}
