package onim.en.area_api.area.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.model.PolygonArea;

public class PolygonBuilder extends AreaBuilder<PolygonArea> {

  private static final List<String> INSTRUCTIONS = Lists.newArrayList();
  static {
    INSTRUCTIONS.add("Left click to add vertex.");
    INSTRUCTIONS.add("Left click again to remove vertex");
    INSTRUCTIONS.add("Right click to create polygon area!");
  }

  private List<Location> vertexList;

  public PolygonBuilder(Player player) {
    super(player);
  }

  public PolygonBuilder(Player player, AreaModel area) {
    super(player, area);
  }

  @Override
  public void onLeftClick(Block block) {
    if (vertexList == null)
      vertexList = new ArrayList<>();

    Location location = block.getLocation();
    if (!vertexList.remove(location)) {
      vertexList.add(location);
    }
  }

  @Override
  public void onRightClick(Block block) {
    if (vertexList == null)
      vertexList = new ArrayList<>();

    this.createAndRegister();
  }

  @Override
  public List<String> getInstructions() {
    return INSTRUCTIONS;
  }

  @Override
  public PolygonArea create() {
    Preconditions.checkNotNull(vertexList);
    Preconditions.checkState(vertexList.size() >= 3);

    PolygonArea area = new PolygonArea(areaId, areaName, vertexList);
    area.setAreaMessage(areaMessage);
    return area;
  }

  @Override
  public Map<Location, BlockData> getCurrentGuide(Player player) {
    Map<Location, BlockData> blocks = new HashMap<>();
    BlockData data = Bukkit.createBlockData(Material.LIGHT_BLUE_STAINED_GLASS);
    for (Location loc : vertexList) {
      blocks.put(loc, data);
    }
    return blocks;
  }

}
