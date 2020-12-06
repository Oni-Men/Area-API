package onim.en.area_api.area.model;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

import onim.en.area_api.area.AreaType;
import onim.en.area_api.util.math.Vec2;

public class CircleArea extends AbstractArea {

  @Expose
  public Vec2 origin;
  @Expose
  public int radius;

  public CircleArea(String areaId, String areaName, Location origin, int radius) {
    super(AreaType.CIRCLE, areaId, areaName);
    this.worldUniqueId = origin.getWorld().getUID();
    this.origin = new Vec2(origin.getBlockX(), origin.getBlockZ());
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
      double distance = this.origin.distance(x, z);
      if (distance <= radius) {
        this.addIfAbsent(player);
      } else {
        this.removeIfPresent(player);
      }
    }
  }
}
