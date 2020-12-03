package onim.en.area_api.area.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;

import onim.en.area_api.area.AreaType;
import onim.en.area_api.util.math.Vec2;

public class PolygonArea extends AbstractArea {

  @Expose
  public List<Vec2> vertexList;

  public PolygonArea(String areaId, String areaName, List<Location> vertexList) {
    super(AreaType.POLYGON, areaId, areaName);

    Preconditions.checkArgument(vertexList.size() != 0);

    UUID uid = vertexList.get(0).getWorld().getUID();
    Preconditions.checkArgument(vertexList.stream().allMatch(loc -> loc.getWorld().getUID().equals(uid)));
    this.worldUniqueId = uid;
    this.vertexList = vertexList.stream()
        .map(l -> new Vec2(l.getX(), l.getZ()))
        .collect(Collectors.toList());
  }

  @Override
  public void update(Collection<? extends Player> onlinePlayers) {
    for (Player player : onlinePlayers) {
      if (this.isPlayerInsidePolygon(player)) {
        this.addIfAbsent(player);
      } else {
        this.removeIfPresent(player);
      }
    }
  }

  /**
   * Thank you! https://www.nttpc.co.jp/technology/number_algorithm.html
   *
   * @param player
   * @return
   */
  private boolean isPlayerInsidePolygon(Player player) {
    Vec2 point = Vec2.fromPlayer(player);
    int windingNumber = 0;
    for (int i = 0, len = vertexList.size() - 1; i < len; i++) {
      Vec2 current = vertexList.get(i);
      Vec2 next = vertexList.get(i + 1);
      if (current.y <= point.y && next.y > point.y) {
        double vt = (point.y - current.y) / (next.y - current.y);
        if (point.x < (current.x + (vt * (next.x - current.x)))) {
          windingNumber++;
        }
      } else if (current.y > point.y && next.y <= point.y) {
        double vt = (point.y - current.y) / (next.y - current.y);
        if (point.x < (current.x + (vt * (next.x - current.x)))) {
          windingNumber--;
        }
      }
    }

    return windingNumber != 0;
  }
}
