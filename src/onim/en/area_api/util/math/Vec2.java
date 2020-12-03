package onim.en.area_api.util.math;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

public class Vec2 {

  @Expose
  public double x;
  @Expose
  public double y;

  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vec2(int x, int y) {
    this((double) x, (double) y);
  }

  public static Vec2 fromPlayer(Player player) {
    Location location = player.getLocation();
    return new Vec2(location.getX(), location.getZ());
  }
}
