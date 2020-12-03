package onim.en.area_api.area.model;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;

import onim.en.area_api.area.AreaType;

public class RectangleArea extends AbstractArea {

  @Expose
  public int top, left, width, height;


  public RectangleArea(String areaId, String areaName, Location loc1, Location loc2) {
    super(AreaType.RECTANGLE, areaId, areaName);

    int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
    int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
    int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
    int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

    Preconditions.checkArgument(loc1.getWorld().equals(loc2.getWorld()), "Cannot define area across world");

    World world = loc1.getWorld();
    this.worldUniqueId = world.getUID();
    this.left = minX;
    this.top = minZ;
    this.width = maxX - minX;
    this.height = maxZ - minZ;

  }

  @Override
  public boolean isPlayerInside(Player player) {
    return this.getPlayersInsideArea().contains(player.getUniqueId());
  }

  @Override
  public void update(Collection<? extends Player> onlinePlayers) {
    for (Player player : onlinePlayers) {
      Location location = player.getLocation();
      if (!location.getWorld().getUID().equals(this.worldUniqueId)) {
        this.removeIfPresent(player);
        continue;
      }

      if (!this.isInside(location.getBlockX(), location.getBlockZ())) {
        this.removeIfPresent(player);
        continue;
      }

      this.addIfAbsent(player);
    }
  }

  private boolean isInside(int x, int z) {
    return (this.left <= x && this.left + this.width >= x) && (this.top <= z && this.top + this.height >= z);
  }
}
