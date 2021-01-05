package onim.en.area_api.area;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface AreaModel {

  public String getAreaId();

  public void setAreaName(String areaName);

  public String getAreaName();

  public void setAreaMessage(String areaMessage);

  public String getAreaMessage();

  public ChatColor[] getDecorators();

  public void setDecorators(ChatColor[] decorators);

  public String getDecoratedName();

  public String getDecoratedMessage();

  public AreaType getType();

  boolean isPlayerInside(Player player);

  Set<UUID> getPlayersInsideArea();

  public void update(Collection<? extends Player> collection);
}
