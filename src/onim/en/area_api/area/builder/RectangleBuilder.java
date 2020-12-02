package onim.en.area_api.area.builder;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.util.AreaUtils;

public class RectangleBuilder extends AreaBuilder<RectangleArea> {

  private static final List<String> INSTRUCTIONS = Lists.newArrayList();
  static {
    INSTRUCTIONS.add("Right click to change first position.");
    INSTRUCTIONS.add("Left click to change second position.");
    INSTRUCTIONS.add("Left click same block again to create area!");
  }

  private Location pos1, pos2;

  public RectangleBuilder(Player player) {
    super(player);
  }

  @Override
  public RectangleArea create() {
    Preconditions.checkState(pos1 != null && pos2 != null, "Either pos1 or pos2 is null.");

    String id = AreaUtils.createAreaId(16);
    RectangleArea area = new RectangleArea(id, areaName, pos1, pos2);
    area.setAreaMessage(areaMessage);
    return area;
  }

  @Override
  public void onLeftClick(Block block) {
    if (this.pos1 != null && this.pos2 != null && this.pos1.equals(block.getLocation())) {
      this.createAndRegister();
    } else {
      this.pos1 = block.getLocation();
    }
  }

  @Override
  public void onRightClick(Block block) {
    this.pos2 = block.getLocation();
  }

  @Override
  public List<String> getInstructions() {
    return INSTRUCTIONS;
  }
}
