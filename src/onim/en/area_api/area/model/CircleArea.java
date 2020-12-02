package onim.en.area_api.area.model;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaType;

public class CircleArea extends AbstractArea {

  public UUID worldUniqueId;
  public int originX, originZ;
  public int radius;

  public CircleArea(String areaId, String areaName, Location origin, int radius) {
    super(AreaType.CIRCLE, areaId, areaName);
    this.worldUniqueId = origin.getWorld().getUID();
    this.originX = origin.getBlockX();
    this.originZ = origin.getBlockZ();
    this.radius = radius;
  }

  @Override
  public void update(Collection<? extends Player> onlinePlayers) {
    for (Player player : onlinePlayers) {
      Location location = player.getLocation();
      if (!location.getWorld().getUID().equals(this.worldUniqueId)) {
        this.removeIfPresent(player);
        continue;
      }

      double x = location.getX();
      double z = location.getZ();
      double distance = Math.sqrt(Math.pow(this.originX - x, 2) + Math.pow(this.originZ - z, 2));
      if (distance <= radius) {
        this.addIfAbsent(player);
      } else {
        this.removeIfPresent(player);
      }
    }
  }
}
