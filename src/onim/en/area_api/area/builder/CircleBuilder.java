package onim.en.area_api.area.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import net.minecraft.server.v1_16_R3.MathHelper;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.model.CircleArea;

public class CircleBuilder extends AreaBuilder<CircleArea> {

  private Location origin, radius;

  private static final List<String> INSTRUCTIONS = Lists.newArrayList();
  static {
    INSTRUCTIONS.add("Left click to set the origin.");
    INSTRUCTIONS.add("Right click to change radius.");
    INSTRUCTIONS.add("Right click again to create area!");
  }

  public CircleBuilder(Player player) {
    super(player);
  }

  public CircleBuilder(Player player, AreaModel area) {
    super(player, area);
  }

  @Override
  public void onLeftClick(Block block) {
    this.origin = block.getLocation();
  }

  @Override
  public void onRightClick(Block block) {
    if (block.getLocation().equals(this.origin))
      return;
    if (this.origin != null && this.radius != null && this.radius.equals(block.getLocation())) {
      this.createAndRegister();
    } else {
      this.radius = block.getLocation();
    }
  }

  @Override
  public List<String> getInstructions() {
    return INSTRUCTIONS;
  }

  @Override
  public CircleArea create() {
    double r = Math.hypot(origin.getX() - radius.getX(), origin.getZ() - radius.getZ());
    CircleArea area = new CircleArea(areaId, areaName, origin, (int) r);
    area.setAreaMessage(areaMessage);
    return area;
  }

  @Override
  public Map<Location, BlockData> getCurrentGuide(Player player) {
    if (origin == null)
      return null;

    World world = player.getWorld();
    Map<Location, BlockData> blocks = new HashMap<>();
    int y = origin.getBlockY();

    blocks.put(origin, Bukkit.createBlockData(Material.LIME_STAINED_GLASS));

    if (radius == null)
      return blocks;

    double r = Math.hypot(origin.getBlockX() - radius.getBlockX(), origin.getBlockZ() - radius.getBlockZ());
    double length = 2 * r * Math.PI / 4; //The arc length of a quarter.
    int size = (int) length / 4;

    for (int i = 0; i < size; i++) {
      int cos = (int) (r * MathHelper.cos((float) (Math.PI / 2 / size * i)));
      int sin = (int) (r * MathHelper.sin((float) (Math.PI / 2 / size * i)));

      Location loc1 = new Location(world, origin.getBlockX() + cos, y, origin.getBlockZ() + sin);
      Location loc2 = new Location(world, origin.getBlockX() - cos, y, origin.getBlockZ() + sin);
      Location loc3 = new Location(world, origin.getBlockX() + cos, y, origin.getBlockZ() - sin);
      Location loc4 = new Location(world, origin.getBlockX() - cos, y, origin.getBlockZ() - sin);

      BlockData data = Bukkit.createBlockData(Material.LIGHT_BLUE_STAINED_GLASS);
      blocks.put(loc1, data);
      blocks.put(loc2, data);
      blocks.put(loc3, data);
      blocks.put(loc4, data);
    }

    blocks.put(radius, Bukkit.createBlockData(Material.ORANGE_STAINED_GLASS));

    return blocks;
  }

}
