package onim.en.area_api.area.model;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaType;

public class PolygonArea extends AbstractArea {

  public List<Location> vertexList;

  public PolygonArea(String areaId, String areaName, List<Location> vertexList) {
    super(AreaType.POLYGON, areaId, areaName);
    this.vertexList = vertexList;
  }

  @Override
  public void update(Collection<? extends Player> collection) {

  }

  private void windingNumber() {

    for (int i = 0; i < vertexList.size(); i++) {
      Location loc = vertexList.get(i);
    }

  }
}
